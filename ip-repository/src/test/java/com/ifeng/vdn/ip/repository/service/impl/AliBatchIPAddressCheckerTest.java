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

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ifeng.vdn.ip.repository.bean.ali.AliIPBean;
import com.ifeng.vdn.ip.repository.service.BatchIPAddressChecker;
import com.ifeng.vdn.ip.repository.service.impl.AliBatchIPAddressChecker;
import com.ifeng.vdn.ip.repository.service.impl.AliIPAddressChecker;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since  Apr 15, 2015
 */
public class AliBatchIPAddressCheckerTest {
	private static final Logger log = LoggerFactory.getLogger(AliIPAddressChecker.class);
	
	@Test
	public void check() throws JsonParseException, JsonMappingException, IOException {
		BatchIPAddressChecker<AliIPBean> checker = new AliBatchIPAddressChecker();
		
		//IPAddress ip = checker.ipcheck("61.236.238.98");
		List<String> ips = new ArrayList<String>();
		ips.add("61.236.238.98");
		ips.add("61.155.167.220");
		ips.add("118.122.88.132");
		ips.add("123.151.185.103");
		
		
		List<AliIPBean> list = checker.check(ips);
		
		for(AliIPBean ip : list){
			log.info(ip.getIpString());
		}
	}
	
	
	@Test
	public void wrap() {
		Workbook wb = null;
		try {
			BatchIPAddressChecker<AliIPBean> checker = new AliBatchIPAddressChecker();
			
			wb = WorkbookFactory.create(new FileInputStream("src/test/resources/data/ip_18.xlsx"));
			Sheet sheet = wb.getSheetAt(0);
			
			int totalRows = sheet.getPhysicalNumberOfRows();
			Cell ipCell = null;
			Cell locCell = null;
			
			List<String> ips = new ArrayList<String>();
			
			for (int i = 1; i < totalRows; i++) {
				Row row = sheet.getRow(i);
				ipCell = row.getCell(1);
				
				ips.add(ipCell.getStringCellValue());
			}
			
			List<AliIPBean> locations = checker.check(ips);
			String location = "";
			
			for (int i = 1; i < totalRows; i++) {
				Row row = sheet.getRow(i);
				locCell = row.getCell(3);
				
				try {
					location = locations.get(i).getIpString();
				} catch (Exception e) {
					location = "ERROR";
				}
				
				locCell.setCellValue(location);
			}
			
			/*if(location.length() == (totalRows - 1)){
				
			}else {
				log.error("Batch executed error");
				throw new RuntimeException("Batch executed error. Some one IP location not be checked.");
			}*/
			
			wb.write(new FileOutputStream("src/test/resources/data/ip_18_Alibaba.xlsx"));
			
		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		} finally {
			if(wb != null)
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
	}
}
