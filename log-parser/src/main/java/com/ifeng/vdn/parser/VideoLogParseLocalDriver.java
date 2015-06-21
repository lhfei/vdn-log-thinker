/*
 * Copyright 2010-2014 the original author or authors.
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
package com.ifeng.vdn.parser;

import java.io.File;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
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
 * @since  May 25, 2015
 */
public class VideoLogParseLocalDriver extends Configured implements Tool {
	
	private static final Logger log = LoggerFactory.getLogger(VideoLogParseLocalDriver.class);
	

	@Override
	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(getConf());
		
		job.setJarByClass(getClass());

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setMapperClass(VideoLogParseMapper.class);
		job.setReducerClass(VideoLogParseReducer.class);
		job.setCombinerClass(VideoLogParseReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		return job.waitForCompletion(true) ? 0 : 1;
		
	}
	
	public static void main(String[] args) throws Exception {
		
		String input = "/home/lhfei/app_tmp/vdnlog/input/*/*.gz";
		String output = "/home/lhfei/app_tmp/vdnlog/output/*/";
		
		//String input = "/home/lhfei/app_tmp/vdnlog/result_ALL.txt";
		//String output = "/home/lhfei/app_tmp/vdnlog/output/";
		
		if (args == null || args.length != 2) {
			args = new String[] {input, output};
		}
		
		FileUtil.fullyDelete(new File(output));

		int exitCode = ToolRunner.run(new VideoLogParseLocalDriver(), args);
		System.exit(exitCode);
	}

}
