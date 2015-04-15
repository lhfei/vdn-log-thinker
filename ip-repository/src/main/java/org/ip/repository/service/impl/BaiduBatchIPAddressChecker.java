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
package org.ip.repository.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.ip.repository.bean.baidu.BaiduIPBean;
import org.ip.repository.service.BatchIPAddressChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since  Apr 15, 2015
 */
public class BaiduBatchIPAddressChecker implements BatchIPAddressChecker<BaiduIPBean> {
	
	private static final Logger log = LoggerFactory.getLogger(BaiduBatchIPAddressChecker.class);

	@Override
	public List<BaiduIPBean> check(List<String> ips) {
		List<BaiduIPBean> list = new ArrayList<BaiduIPBean>();
		
		BaiduIPBean ipaddress = null;
		// Create an instance of HttpClient.
		HttpClient clinet = new HttpClient();
		
		for(String ip : ips){
			
			String url = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query="
					+ ip
					+ "&co=&resource_id=6006&t=1428632527853&ie=utf8&oe=gbk&cb=op_aladdin_callback&format=json&tn=baidu&cb=jQuery110204891062709502876_1428631554303&_=1428631554305";
			
			// Create a method instance.
			GetMethod getMethod = new GetMethod(url);
			
			try {
				// Execute the method.
				int statusCode = clinet.executeMethod(getMethod);
				
				if (statusCode != HttpStatus.SC_OK) {
					log.error("Method failedd: {}", getMethod.getStatusCode());
				} else {
					// Read the response body.
					byte[] responseBody = getMethod.getResponseBody();
					String responseStr = new String(responseBody, "GBK");
					
					responseStr = responseStr.substring(
							responseStr.indexOf("(") + 1, responseStr.indexOf(")"));
					
					ObjectMapper mapper = new ObjectMapper();
					
					ipaddress = mapper.readValue(responseStr, BaiduIPBean.class);
					
					list.add(ipaddress);
				}
				
			} catch (JsonParseException | JsonMappingException | HttpException e) {
				log.error(e.getMessage(), e);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		
		
		return list;
	}

}
