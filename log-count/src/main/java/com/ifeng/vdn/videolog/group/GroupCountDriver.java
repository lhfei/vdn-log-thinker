package com.ifeng.vdn.videolog.group;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ifeng.vdn.videolog.VideologFilter;
import com.ifeng.vdn.videolog.VideologPair;

public class GroupCountDriver extends Configured implements Tool {
	
	private static final Logger log = LoggerFactory.getLogger(GroupCountDriver.class);

	@Override
	public int run(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.printf("Usage: %s [generic options] <input> <output>\n",
					getClass().getSimpleName());
			ToolRunner.printGenericCommandUsage(System.err);
			return -1;
		}
		
		Job job = Job.getInstance(getConf());
		job.setMapperClass(VideoLogGroupCountMapper.class);
		job.setReducerClass(VideoLogGroupCountReducer.class);
		
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		
		Iterator<String> iter = VideologFilter.VALID_ERR_CODE.iterator();
		if(job.waitForCompletion(true)){
			Counters counter = job.getCounters();
			PrintWriter pw = new PrintWriter(new File(args[1] +"/result.txt"), "UTF-8");
			
			while(iter.hasNext()){
				String errId = iter.next();
				//context.write(new Text(key), new Text("" +context.getCounter(errId, errId).getValue()));
				pw.write(errId +"\t"+ counter.findCounter(errId, errId).getValue());
				pw.println();
			}
			
			pw.flush();
			pw.close();
			
		}
		
		return job.waitForCompletion(true) ? 0 : 1;
	}
	
	static class VideoLogGroupCountMapper extends Mapper<LongWritable, Text, Text, Text> {

		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			VideologPair pair = VideologFilter.filerByErrCode(value);
			if (pair.isValid()) {
				String errId = pair.getErrId();
				
				if(VideologFilter.contains(errId)){
					context.getCounter(errId, errId).increment(1);
					log.info("Group: {}, Total: {}", errId, context.getCounter(errId, errId).getValue());
					
					context.write(new Text(errId), new Text(""+context.getCounter(errId, errId).getValue()));
				}
			}
		}
		
	}
	
	static class VideoLogGroupCountReducer extends Reducer<Text, Text, Text, Text> {

		@Override
		protected void reduce(Text key, Iterable<Text> values,
				Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			Iterator<String> iter = VideologFilter.VALID_ERR_CODE.iterator();
			while(iter.hasNext()){
				String errId = iter.next();
				context.write(new Text(key), new Text("" +context.getCounter(errId, errId).getValue()));
			}
		}
		
	}
	
	
	public static void main(String[] args) throws Exception {
		if(args == null || args.length != 2){
			args = new String[]{"src/test/resources/input/videolog/0000.txt",
					"src/test/resources/output/groupcount"};
		}
		
		FileUtil.fullyDelete(new File(args[1]));
		
		int exitCode = ToolRunner.run(new GroupCountDriver(), args);
		
		System.exit(exitCode);
	}

}
