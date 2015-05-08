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

import org.apache.hadoop.hive.service.HiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hive.HiveClientCallback;
import org.springframework.data.hadoop.hive.HiveTemplate;
import org.springframework.stereotype.Service;

import com.ifeng.vdn.web.hive.persistence.PasswordProcessRepository;
import com.ifeng.vdn.web.hive.service.PasswordProcessService;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since  May 7, 2015
 */
@Service("passwordProcessService")
public class PasswordProcessServiceImpl implements PasswordProcessService {

	@Override
	public Long count() {
		
		return hivePasswordProcessRepository.count();
	}

	@Override
	public void processPasswordFile(String inputFile) {
		hivePasswordProcessRepository.processPasswordFile(inputFile);
	}

	@Override
	 public List<String> getDbs() {
		return hiveTemplate.execute(new HiveClientCallback<List<String>>() {
			 @Override
	         public List<String> doInHive(HiveClient hiveClient) throws Exception {
	            return hiveClient.get_all_databases();
	         }
		});
	  }
	
	@Autowired
	private PasswordProcessRepository hivePasswordProcessRepository;
	
	@Autowired
	private HiveTemplate hiveTemplate;
}
