<<<<<<< HEAD
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
package com.ifeng.hadoop.thinker;

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
 * @since  Apr 27, 2015
 */
public class LogMapper extends Mapper<LongWritable, Text, Text, Text> {
	
	private static final Logger log = LoggerFactory.getLogger(LogMapper.class);

	/* (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Mapper#map(java.lang.Object, java.lang.Object, org.apache.hadoop.mapreduce.Mapper.Context)
	 */
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		
		try {
			if(value != null && value.toString().trim().length() > 0) {
				String line = value.toString();
				String[] items  = line.split("\\s+");
				if(items.length == 3){
					String name = items[0];
					String swift  =items[1].split(":")[1];
					String origin = items[2].split(":")[1];
					
					long swfitSize = Long.parseLong(swift);
					long originSize = Long.parseLong(origin);
					
					LogModel model = new LogModel(name, swfitSize, originSize, (swfitSize - originSize));
					context.write(new Text(name), new Text(model.toString()));
					
				}else {
					
					log.info("Invalid line: ", value.toString());
				}
				
			}
		} catch (NumberFormatException e) {
			log.error(e.getMessage(), e);
		}
	}

	
}
=======
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
package com.ifeng.hadoop.thinker;

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
 * @since  Apr 27, 2015
 */
public class LogMapper extends Mapper<LongWritable, Text, Text, Text> {
	
	private static final Logger log = LoggerFactory.getLogger(LogMapper.class);

	/* (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Mapper#map(java.lang.Object, java.lang.Object, org.apache.hadoop.mapreduce.Mapper.Context)
	 */
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		
		try {
			if(value != null && value.toString().trim().length() > 0) {
				String line = value.toString();
				String[] items  = line.split("\\s+");
				if(items.length == 3){
					String name = items[0];
					String swift  =items[1].split(":")[1];
					String origin = items[2].split(":")[1];
					
					long swfitSize = Long.parseLong(swift);
					long originSize = Long.parseLong(origin);
					
					LogModel model = new LogModel(name, swfitSize, originSize, (swfitSize - originSize));
					context.write(new Text(name), new Text(model.toString()));
					
				}else {
					
					log.info("Invalid line: ", value.toString());
				}
				
			}
		} catch (NumberFormatException e) {
			log.error(e.getMessage(), e);
		}
	}

	
}
>>>>>>> 58224f434f269d2ad40a63ae62b24ab359fb7b7e
