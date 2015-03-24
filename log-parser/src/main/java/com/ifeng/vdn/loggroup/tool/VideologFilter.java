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
package com.ifeng.vdn.loggroup.tool;

import java.util.Set;
import java.util.TreeSet;

import org.apache.hadoop.io.Text;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since Mar 20, 2015
 */
public final class VideologFilter {

	{
		VALID_ERR_CODE.add("208000");
		VALID_ERR_CODE.add("301010");
		VALID_ERR_CODE.add("301020");
		VALID_ERR_CODE.add("301030");
		VALID_ERR_CODE.add("301040");
		VALID_ERR_CODE.add("601000");
		VALID_ERR_CODE.add("602000");
	}
	
	public static Set<String> VALID_ERR_CODE = new TreeSet<String>();
	
	public static final VideologPair filerByErrCode(Text value) {
		String result = "";
		VideologPair pair = null;
		
		if(value == null){
			pair = new VideologPair("");
			pair.setValue("");
			
			return pair;
		}
		
		String errCode = "";
		String[] items = value.toString().split("\t");
		
		if(items != null && items.length == 14){
			// extract the err code: column index 8:
			errCode = items[7];
			
			// check the err code is valid, if not then ignore.
			if(VALID_ERR_CODE.contains(errCode) || errCode.startsWith("") || errCode.startsWith("")){
				pair = new VideologPair(items[0] +"|"+ items[4]);
				
				result = value.toString();
				
				pair.setValue(result);
			}
		}else{
			pair = new VideologPair("");
			pair.setValue("");
		}
		
		return pair;
	}
	
	
}
