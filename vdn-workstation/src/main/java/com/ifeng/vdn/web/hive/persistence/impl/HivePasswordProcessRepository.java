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
package com.ifeng.vdn.web.hive.persistence.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.hadoop.hive.HiveOperations;
import org.springframework.stereotype.Repository;

import com.ifeng.vdn.web.hive.persistence.PasswordProcessRepository;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since  May 7, 2015
 */
@Repository("hivePasswordProcessRepository")
public class HivePasswordProcessRepository implements PasswordProcessRepository {

	private @Value("${hive.table}") String tableName;
	
	private HiveOperations hiveOperations;
	
	@Autowired
	public HivePasswordProcessRepository(HiveOperations hiveOperations) {
		this.hiveOperations = hiveOperations;
	}
	
	@Override
	public Long count() {
		return hiveOperations.queryForLong("SELECT COUNT(*) FROM " + tableName);
	}

	@Override
	public void processPasswordFile(String inputFile) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("inputFile", inputFile);
		hiveOperations.query("/home/cloudland/app_tmp/password-analysis.hql", parameters);		
	}


}
