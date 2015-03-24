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
package com.ifeng.vdn.loggroup.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ifeng.vdn.loggroup.tool.VideologFilter;
import com.ifeng.vdn.loggroup.tool.VideologPair;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since Mar 16, 2015
 */
public class VideoLogGroupMapper extends
		Mapper<LongWritable, Text, Text, Text> {
	
	private static final Logger log = LoggerFactory.getLogger(VideoLogGroupMapper.class);

	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		VideologPair pair = VideologFilter.filerByErrCode(value);
		if(pair.isValid()){
			log.info("Key: {}", pair.getKey());
			context.write(new Text(pair.getKey()), new Text(pair.getValue()));
		}
	}
}
