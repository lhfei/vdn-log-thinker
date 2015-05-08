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
package com.ifeng.vdn.web.hive.service.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ifeng.vdn.web.hive.service.PasswordProcessService;
import com.ifeng.vdn.web.hive.test.BaseTestSuite;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since  May 7, 2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context.xml")
public class PasswordProcessServiceImplTest extends BaseTestSuite {

	@Test
	public void count() {
//		ThriftHiveProcessorFactory f = null;
		
		passwordProcessService.processPasswordFile("/etc/passwd");
		
		Long total = passwordProcessService.count();
		
		List<String> dbs = passwordProcessService.getDbs();
		
		for(String db : dbs) {
			log.info("User total: {}", db);
		}
		
	}
	
	@Autowired
	private PasswordProcessService passwordProcessService;
	
}
