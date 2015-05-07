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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifeng.vdn.ip.repository.bean.ali.AliIPBean;
import com.ifeng.vdn.ip.repository.service.BatchIPAddressChecker;
import com.ifeng.vdn.ip.repository.service.impl.AliBatchIPAddressCheckerTest;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since  Apr 15, 2015
 */
public class AliBatchIPAddressChecker implements
		BatchIPAddressChecker<AliIPBean> {
	
	private static final Logger log = LoggerFactory.getLogger(AliBatchIPAddressCheckerTest.class);

	@Override
	public List<AliIPBean> check(List<String> ips) {
		
		List<AliIPBean> list = new ArrayList<AliIPBean>();
		
		AliIPBean ipaddress = null;
		String url = "http://ip.taobao.com/service/getIpInfo2.php";
		
		for(String ip : ips){

			// Create an instance of HttpClient.
			HttpClient clinet = new HttpClient();

			// Create a method instance.
			PostMethod postMethod = new PostMethod(url);

			// Execute the method.
			try {
				postMethod.setParameter("ip", ip);
				int resultCode = clinet.executeMethod(postMethod);
				
				if(resultCode == HttpStatus.SC_OK){
					// Read the response body.
					InputStream responseBody = postMethod.getResponseBodyAsStream();
					
					ObjectMapper mapper = new ObjectMapper();
					ipaddress = mapper.readValue(responseBody, AliIPBean.class);
					
					log.info(responseBody.toString());
					
					list.add(ipaddress);
				}else {
					list.add(new AliIPBean());
					log.error("Method failedd: [{}] , IP: [{}]", postMethod.getStatusCode(), ip);
				}

			} catch (JsonParseException | JsonMappingException | HttpException e) {
				list.add(new AliIPBean());
				log.error(e.getMessage(), e);
			} catch (IOException e) {
				list.add(new AliIPBean());
				log.error(e.getMessage(), e);
			}			
		}
		return list;
	}

}
