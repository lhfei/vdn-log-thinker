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
package org.ip.repository.orm.mybatis.mapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ip.repository.bean.ErrLocModel;
import org.ip.repository.bean.IPLocModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since  Apr 25, 2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context.xml")
public class ILogMapperTest {

	@Test
	public void createErrLocModel() {
		String input = "src/test/resources/data/input/group_err_loc_total_large.txt";
		
		FileReader fr = null;
		BufferedReader reader = null;
		
		List<ErrLocModel> ipList = new ArrayList<ErrLocModel>();
		
		try {
			fr = new FileReader(new File(input));
			reader = new BufferedReader(fr);
			
			String line = reader.readLine();
			
			
			String auditDate = "";
			String err = "";
			String loc = "";
			String total = "";
			
			int steps = 0;
			
			while(line != null){
				String[] items = line.split("\\t");

				if(items.length >0 && items[0] != null){
					auditDate = items[0];
				}
				if(items.length >1 && items[1] != null){
					err = items[1];
				}
				if(items.length >2 && items[2] != null){
					loc = items[2];
				}
				if(items.length >3 && items[3] != null){
					total = items[3];
				}
				
				ErrLocModel model = new ErrLocModel();
				model.setAuditDate(auditDate);
				model.setErr(err);
				model.setLoc(loc);
				model.setTotal(Integer.parseInt(total));
				
				ipList.add(model);
				
				if(steps > 0 && steps % BATCH_SIZE == 0){
					ilogMapper.createErrLocModel(ipList);
					ipList = new ArrayList<ErrLocModel>();
				}
				
				line = reader.readLine();
				steps++;
			}
			
			if(ipList.size() > 0){
				ilogMapper.createErrLocModel(ipList);;
				
				ipList = new ArrayList<ErrLocModel>();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	@Test
	public void createIPLocModel() {
		String input = "src/test/resources/data/input/group_ip_loc_total_large.txt";
		
		FileReader fr = null;
		BufferedReader reader = null;
		
		List<IPLocModel> ipList = new ArrayList<IPLocModel>();
		
		try {
			fr = new FileReader(new File(input));
			reader = new BufferedReader(fr);
			
			String line = reader.readLine();
			
			
			String ip = "";
			String loc = "";
			String total = "";
			
			int steps = 0;
			
			while(line != null){
				String[] items = line.split("\\t");

				if(items.length >0 && items[0] != null){
					ip = items[0];
				}
				if(items.length >1 && items[1] != null){
					loc = items[1];
				}
				if(items.length >2 && items[2] != null){
					total = items[2];
				}
				
				IPLocModel model = new IPLocModel();
				model.setIp(ip);
				model.setLoc(loc);
				model.setTotal(Integer.parseInt(total));
				
				ipList.add(model);
				
				if(steps > 0 && steps % BATCH_SIZE == 0){
					ilogMapper.createIPLocModel(ipList);
					ipList = new ArrayList<IPLocModel>();
				}
				
				line = reader.readLine();
				steps++;
			}
			
			if(ipList.size() > 0){
				ilogMapper.createIPLocModel(ipList);;
				
				ipList = new ArrayList<IPLocModel>();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	private static final int BATCH_SIZE = 1000;
	
	@Autowired
	private ILogMapper ilogMapper;
}
