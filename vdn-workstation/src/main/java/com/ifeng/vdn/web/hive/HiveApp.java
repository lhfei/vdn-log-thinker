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
package com.ifeng.vdn.web.hive;

import java.util.List;

import org.apache.hive.service.server.HiveServer2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.hive.HiveTemplate;

import com.ifeng.vdn.web.hive.persistence.PasswordProcessRepository;
import com.ifeng.vdn.web.hive.persistence.impl.HivePasswordProcessRepository;
import com.ifeng.vdn.web.hive.test.BaseTestSuite;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since May 7, 2015
 */
public class HiveApp extends BaseTestSuite{

	private static Logger log = LoggerFactory.getLogger(HiveApp.class);
	
	public static void main(String[] args) {
		HiveServer2 hi = null;
		
		
		AbstractApplicationContext context = new ClassPathXmlApplicationContext(
				"spring/application-context.xml", HiveApp.class);
		log.info("Hive Application Running");
		context.registerShutdownHook();

		HiveTemplate template = context.getBean(HiveTemplate.class);
		List<String> tables = template.query("show tables;");
		for (String tablName : tables) {
			log.info(tablName);
		}

		PasswordProcessRepository repository = context
				.getBean(HivePasswordProcessRepository.class);
		repository.processPasswordFile("/etc/passwd");
		log.info("Count of password entries = " + repository.count());
		context.close();
		log.info("Hive Application Completed");
	}
}
