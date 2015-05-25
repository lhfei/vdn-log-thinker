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
package com.ifeng.vdn.logparser.mapper;

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

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since Mar 16, 2015
 */
public class VideoLogDriver extends Configured implements Tool {

	@Override
	public int run(String[] paths) throws Exception {
		Job job = Job.getInstance(super.getConf());
		job.setJarByClass(getClass());

		FileInputFormat.addInputPath(job, new Path(paths[0]));
		FileOutputFormat.setOutputPath(job, new Path(paths[1]));

		job.setMapperClass(VideoLogMapper.class);
		job.setReducerClass(VideoLogReducer.class);
		job.setCombinerClass(VideoLogReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		String input = "src/test/resources/input/videolog/0000.sta";
		String output = "src/test/resources/output/log";
		
		/*String input = "/home/lhfei/app_tmp/2015-03-05.log.gz";
		String output = "src/test/resources/output/log2";*/
		if (args == null || args.length != 2) {
			args = new String[] {input, output};
		}
		
		FileUtil.fullyDelete(new File(output));

		int exitCode = ToolRunner.run(new VideoLogDriver(), args);
		System.exit(exitCode);
	}

}
