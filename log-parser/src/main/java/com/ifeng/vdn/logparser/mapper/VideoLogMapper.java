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

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since Mar 16, 2015
 */
public class VideoLogMapper extends Mapper<LongWritable, Text, Text, Text> {

	private static final Logger log = LoggerFactory.getLogger(VideoLogMapper.class);

	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		if(value != null) {
			String[] items = value.toString().split("\t");
			if(items.length == 24){
				log.info("Key[{}]    Value>>>>{}", key, value.toString());
				
				if(items[20].endsWith("zhvp1.0.16") || items[20].endsWith("nsvp1.0.18")){
					context.write(new Text("items[20]"), value);
				}
			}
		}
	}
}
