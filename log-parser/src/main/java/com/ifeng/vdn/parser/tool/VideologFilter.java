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

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since  May 25, 2015
 */
public class VideologFilter {

	/**
	 * 
	 */
	public static Set<String> VALID_ERR_CODE = new TreeSet<String>(
			Arrays.asList("208000", "303000", "301010", "301020", "301030",
					"601000", "602000"));
	
	/**
	 * 
	 */
	public static Set<String> VALID_PLAYER_VERSION = new TreeSet<String>(
			Arrays.asList("VZHPlayer_zhvp1.0.16", "vNsPlayer_nsvp1.0.18"));
}
