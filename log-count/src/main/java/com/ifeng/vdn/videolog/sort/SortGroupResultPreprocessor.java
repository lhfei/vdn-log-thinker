/*
 * Copyright 2010-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ifeng.vdn.videolog.sort;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since May 16, 2015
 */
public class SortGroupResultPreprocessor extends Configured implements Tool {
	private static final Logger log = LoggerFactory.getLogger(SortGroupResultPreprocessor.class);
	
	static class SortGroupResultMapper extends Mapper<LongWritable, Text, IntWritable, Text> {

		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, IntWritable, Text>.Context context)
				throws IOException, InterruptedException {
			
			String[] items = value.toString().split("\t");
			
			context.write(new IntWritable(Integer.parseInt(items[1])), new Text(items[0]));

			log.info("key = {}, value = {}", key, value);
		}
		
	}

	@Override
	public int run(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.printf("Usage: %s [generic options] <input> <output>\n",
					getClass().getSimpleName());
			ToolRunner.printGenericCommandUsage(System.err);
			return -1;
		}
		
		Job job = Job.getInstance(getConf());
		job.setMapperClass(SortGroupResultMapper.class);
		job.setNumReduceTasks(0);
		
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
	
		// Sort data by total number:
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		
		
		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		if(args == null || args.length != 2) {
			args = new String[]{"src/test/resources/input/sort/count-result.txt",
			"src/test/resources/output/sort-countresult"};
		}
		
		FileUtil.fullyDelete(new File(args[1]));
		
		int exitCode = ToolRunner.run(new SortGroupResultPreprocessor(), args);
		
		System.exit(exitCode);
		
	}

}
