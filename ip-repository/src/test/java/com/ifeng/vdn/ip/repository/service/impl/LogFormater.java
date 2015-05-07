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
package com.ifeng.vdn.ip.repository.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since  Apr 19, 2015
 */
public class LogFormater {

	private static final Logger log = LoggerFactory.getLogger(LogFormater.class);
	
	private String[] outs = new String[]{"src/test/resources/data/ip_17_ali.txt", "src/test/resources/data/ip_18_ali.txt"};
	
	@Test
	public void format() throws FileNotFoundException {
		
		String line = null;
		
		try {
			// FileReader reads text files in the default encoding.
			FileReader reader = new FileReader(outs[0]);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = 
			    new BufferedReader(reader);

			while((line = bufferedReader.readLine()) != null) {
			    log.info(line);
			    line = new String(line.getBytes("UTF-8"));
			    log.info(line);
			}    

			// Always close files.
			bufferedReader.close();
			
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}            
		
	}
}
