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
package com.ifeng.vdn.parser.tool;

import com.ifeng.vdn.loggroup.tool.VideologPair;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since  Jul 31, 2015
 */
public class VideologFilterTest {

	public static void main(String[] args) {
		String origin = "4AC51C17-9FBE-47F2-8EE0-8285A66EAFF5	#	124.236.154.36	http://v.ifeng.com/live/#4AC51C17-9FBE-47F2-8EE0-8285A66EAFF5	#	1345205484375_7037	#	live	#	0017	#	1438311540986	#	#	#	702020	0	0	0	#	vLivePlayer_v5.0.51_p	0017	ifengP2P	电信";
	
		VideologPair pair = VideologFilter.filte(origin, "2015-07-30", "0959");
		
		System.out.println(pair.getValue());
	}

}
