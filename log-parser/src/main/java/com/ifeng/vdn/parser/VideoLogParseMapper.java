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

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ifeng.vdn.loggroup.tool.VideologPair;
import com.ifeng.vdn.parser.tool.VideologFilter;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since  May 25, 2015
 */
public class VideoLogParseMapper extends
		Mapper<LongWritable, Text, Text, Text> {
	
	private static final Logger log = LoggerFactory.getLogger(VideoLogParseMapper.class);

	/**
	 * <code>
		-----------------------------------------------------------------------------------------------------
		-- 日志文件参考标量
		-----------------------------------------------------------------------------------------------------
		16	err	EventRetCode ＝ EventCode（1位）+ActionCode（2位）+Data（3位） 详见下表	err=100000
		3	ip	用户IP地址	
		4	ref	视频所在页面url	ref=http://v.ifeng.com/v/news/djmdnz/index.shtml#01c92b9c-37c7-4510-ac87-519a1224c263
		5	sid	注册用户的用户名，取cookie[‘sid’]	sid=3232F65C8864C995D82D087D8A15FF05kzzxc1
		6	uid	访问用户的ID，用户的唯一标识	uid=1395896719356_cqf3nr8244
		9	loc	空字段	
		12	tm	当前系统时间戳，毫秒级	tm=1424048309234
		13	url	视频存储的地址	url=http://ips.ifeng.com/video19.ifeng.com/video09/2015/02/15/2999516-102-2028.mp4
		15	dur	视频总时长，取自XML	dur=155
		17	bt	文件总大小（B）	bt=12451187
		18	bl	已加载文件大小（B）	bl=12451187
		19	lt	加载文件耗时（毫秒）	lt=139059
		21	vid	播放器版本	vid=vNsPlayer_nsvp1.0.18
		23	cdnId	标记CDN（Sooner-赛维，Chinanet-网宿，Chinacache-蓝讯） （直播需要，非直播留空）	cdnId=ifengP2P
		24	netname	运营商 （直播需要，非直播留空）	netname=移动
		-----------------------------------------------------------------------------------------------------
	  </code>
	 */
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		
		if(value != null) {
			String ds = "";
			FileSplit split = (FileSplit)context.getInputSplit();
			String parentPath = split.getPath().getParent().toString();
			String[] parents = parentPath.split("/");
			if(parents.length > 0){
				ds = parents[parents.length - 1];
			}
			
			VideologPair pair = VideologFilter.filte(value.toString(), ds);
			
			if(pair != null && pair.getKey() != null && pair.getValue() != null){
				context.write(new Text(pair.getKey()), new Text(pair.getValue()));
			}
		}
	}
}
