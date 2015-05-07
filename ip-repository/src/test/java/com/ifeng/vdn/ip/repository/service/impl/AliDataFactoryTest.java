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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ifeng.vdn.ip.repository.bean.IPModel;
import com.ifeng.vdn.ip.repository.service.DataFactory;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since  Apr 20, 2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context.xml")
public class AliDataFactoryTest {
	private static final Logger log = LoggerFactory.getLogger(AliDataFactoryTest.class);
	
	@Test
	public void importDataFromExcel() {
		Workbook wb = null;
		String input = "src/test/resources/data/ip_18_Alibaba.xlsx";
		
		try {

			List<IPModel> ipList = new ArrayList<IPModel>();
			
			wb = WorkbookFactory.create(new FileInputStream(input));
			Sheet sheet = wb.getSheetAt(0);
			
			int totalRows = sheet.getPhysicalNumberOfRows();
			
			Cell ipCell = null;
			Cell totalCell = null;
			Cell locCell = null;
			
			String location = "";
			int total;
			String ipString = "";
			
			String[] items = null;
			
			
			for (int i = 1; i < totalRows; i++) {
				Row row = sheet.getRow(i);
				
				ipCell = row.getCell(1);
				totalCell = row.getCell(2);
				locCell = row.getCell(3);
				
				try {
					String country = "";
					String area = "";
					String region = "";
					String city = "";
					String isp = "";
					IPModel model = new IPModel();
					
					ipString = ipCell.getStringCellValue();
					total = (int) totalCell.getNumericCellValue();
					location = locCell.getStringCellValue();
					
					items = location.split(" ");
					if(items != null){
						if(items[0] != null){
							country = items[0];
						}
						if(items[1] != null){
							area = items[1];
						}
						if(items[2] != null){
							region = items[2];
						}
						if(items[3] != null){
							city = items[3];
						}
						if(items[4] != null){
							isp = items[4];
						}
						
						model.setIp(ipString);
						model.setCountry(country.trim());
						model.setArea(area.trim());
						model.setRegion(region.trim());
						model.setCity(city.trim());
						model.setIsp(isp.trim());
						model.setTotal(total);
						
						ipList.add(model);
					}
					
					log.info("IP: {}, Total{}, location: {}", ipString, total, location);
					
					if((i % 1000) == 0){
						aliDataFactory.importData(ipList);
						ipList = new ArrayList<IPModel>();
					}
					
				} catch (Exception e) {
					e.getMessage();
					//log.error(e.getMessage(), e);
					
				} 
				
			}
			
			if(ipList.size() > 0){
				aliDataFactory.importData(ipList);
			}
			
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void importDataFromText() {
		String input = "src/test/resources/data/input/IP_FULLY.txt";
		
		FileReader fr = null;
		BufferedReader reader = null;
		
		List<IPModel> ipList = new ArrayList<IPModel>();
		
		try {
			fr = new FileReader(new File(input));
			reader = new BufferedReader(fr);
			
			String line = reader.readLine();
			
			int batchSize = 0;
			String ip = "";
			String country = "";
			String area = "";
			String region = "";
			String city = "";
			String isp = "";
			
			while(line != null){
				String[] items = line.split("-");

				if(items.length >0 && items[0] != null){
					ip = items[0];
				}
				if(items.length >1 && items[1] != null){
					country = items[1];
				}
				if(items.length >2 && items[2] != null){
					area = items[2];
				}
				if(items.length >3 && items[3] != null){
					region = items[3];
				}
				if(items.length >4 && items[4] != null){
					city = items[4];
				}
				if(items.length >5 && items[5] != null){
					isp = items[5];
				}
				
				IPModel model = new IPModel();
				model.setIp(ip);
				model.setCountry(country);
				model.setArea(area);
				model.setRegion(region);
				model.setCity(city);
				model.setIsp(isp);
				
				ipList.add(model);
				
				
				if(batchSize > 0 && batchSize % 1000 == 0){
					aliDataFactory.importData(ipList);
					ipList = new ArrayList<IPModel>();
				}
				
				line = reader.readLine();
				batchSize++;
			}
			
			if(ipList.size() > 0){
				aliDataFactory.importData(ipList);
				ipList = new ArrayList<IPModel>();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Autowired
	private DataFactory<IPModel> aliDataFactory;
	
}
