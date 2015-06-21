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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import com.ifeng.vdn.loggroup.tool.VideologPair;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since  May 25, 2015
 */
public class VideologFilter {
	
	private static final String INVALID_DATE_STR = "####-##-##";

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
	
	public static String formatDate(String dateStr) {
		try {
			if(null != dateStr && dateStr.trim().length() > 0){
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				long tm = Long.parseLong(dateStr);
				Date date = new Date(tm);
				
				dateStr = sf.format(date);
			}else{
				dateStr = INVALID_DATE_STR;
			}
		} catch (NumberFormatException e) {
			dateStr = INVALID_DATE_STR;
		}
		return dateStr;
	}
	
	public static VideologPair filte(String origin, String ds){
		VideologPair pair = null;
		
		try {
			if(null != origin && origin.length() > 0){
				String[] items = origin.toString().split("\t");
				
				if(items.length == 24){
					if(null != items[20] && "vNsPlayer_nsvp1.0.18".equals(items[20])){
						StringBuilder sb = new StringBuilder();
						pair = new VideologPair(items[15]);
						
						sb.append(items[2]);
						sb.append("\t");
						
						sb.append(items[3]);
						sb.append("\t");
						
						sb.append(items[4]);
						sb.append("\t");
						
						sb.append(items[5]);	//uid
						sb.append("\t");
						
						sb.append(items[8]);
						sb.append("\t");
						
						sb.append(formatDate(items[11]));	//tm
						sb.append("\t");
						
						sb.append(items[12]);
						sb.append("\t");
						
						sb.append(items[14]);
						sb.append("\t");
						
						/*sb.append(items[15]);
						sb.append("\t");*/
						
						sb.append(items[16]);
						sb.append("\t");
						
						sb.append(items[17]);
						sb.append("\t");
						
						sb.append(items[18]);
						sb.append("\t");
						
						sb.append(items[20]);	// vid
						sb.append("\t");
						
						sb.append(items[22]);
						sb.append("\t");
						
						sb.append(items[23]);
						sb.append("\t");
						
						sb.append(items[5] +"-"+ items[0]);	//kye: uid '-' id
						sb.append("\t");
						
						sb.append(ds);
						
						pair.setValue(sb.toString());
					}
				}
			}
		} catch (Exception e) {
		}
		
		return pair;
	}
	
}
