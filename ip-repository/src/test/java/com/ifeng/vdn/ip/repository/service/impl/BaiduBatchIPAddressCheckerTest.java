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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ifeng.vdn.ip.repository.bean.baidu.BaiduIPBean;
import com.ifeng.vdn.ip.repository.service.BatchIPAddressChecker;
import com.ifeng.vdn.ip.repository.service.impl.BaiduBatchIPAddressChecker;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since  Apr 15, 2015
 */
public class BaiduBatchIPAddressCheckerTest {
	private static final Logger log = LoggerFactory.getLogger(BaiduBatchIPAddressCheckerTest.class);

	@Test
	public void check() throws JsonParseException, JsonMappingException, IOException {
		BatchIPAddressChecker<BaiduIPBean> checker = new BaiduBatchIPAddressChecker();
		
		//IPAddress ip = checker.ipcheck("61.236.238.98");
		List<String> ips = new ArrayList<String>();
		ips.add("61.236.238.98");
		ips.add("61.155.167.220");
		ips.add("118.122.88.132");
		ips.add("123.151.185.103");
		
		
		List<BaiduIPBean> list = checker.check(ips);
		
		for(BaiduIPBean ip : list){
			log.info(ip.getIpString());
		}
		
	}
}
