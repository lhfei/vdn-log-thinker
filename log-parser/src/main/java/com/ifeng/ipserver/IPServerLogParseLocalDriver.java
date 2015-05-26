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
package com.ifeng.ipserver;

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

import com.ifeng.vdn.logparser.mapper.VideoLogMapper;
import com.ifeng.vdn.logparser.mapper.VideoLogReducer;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since  May 25, 2015
 */
public class IPServerLogParseLocalDriver extends Configured implements Tool {
	
	private static final Logger log = LoggerFactory.getLogger(IPServerLogParseLocalDriver.class);
	

	@Override
	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(super.getConf());
		job.setJarByClass(getClass());

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setMapperClass(IPServerLogParseMapper.class);
		job.setReducerClass(IPServerLogParseReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		return job.waitForCompletion(true) ? 0 : 1;
	}
	
	public static void main(String[] args) throws Exception {
		String input = "src/test/resources/input/ipserver/mini.txt";
		String output = "src/test/resources/output/ipserver";
		
		if (args == null || args.length != 2) {
			args = new String[] {input, output};
		}
		
		FileUtil.fullyDelete(new File(output));

		int exitCode = ToolRunner.run(new IPServerLogParseLocalDriver(), args);
		System.exit(exitCode);
	}

}
