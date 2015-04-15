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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.ip.repository.bean.IPAddress;
import org.ip.repository.bean.baidu.BaiduIPBean;
import org.ip.repository.bean.baidu.IPLocation;
import org.ip.repository.service.impl.IPAddressBaiduChecker;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since  Apr 10, 2015
 */
public class IPAddressBaiduCheckerTest {
	private static final Logger log = LoggerFactory.getLogger(IPAddressBaiduCheckerTest.class);

	@Test
	public void check() throws JsonParseException, JsonMappingException, IOException {
		IPAddressBaiduChecker checker = new IPAddressBaiduChecker();
		
		IPAddress ip = checker.ipcheck("61.236.238.98");
		
		
		log.info(ip.getIpString());
		
	}
	
	@Test
	public void wrap() {
		
		Workbook wb = null;
		
		try {
			IPAddressBaiduChecker checker = new IPAddressBaiduChecker();
			
			wb = WorkbookFactory.create(new FileInputStream("src/test/resources/data/ip_16.xlsx"));
			Sheet sheet = wb.getSheetAt(0);
			
			int totalRows = sheet.getPhysicalNumberOfRows();
			
			Cell ipCell = null;
			Cell locCell = null;
			
			for (int i = 1; i < totalRows; i++) {
				Row row = sheet.getRow(i);
				
				ipCell = row.getCell(1);
				locCell = row.getCell(3);
				
				BaiduIPBean ip = (BaiduIPBean)checker.ipcheck(ipCell.getStringCellValue());
				List<IPLocation> locations = ip.getData();
				
				if(locations != null && locations.size() > 0){
					
					try {
						locCell.setCellValue(locations.get(0).getLocation());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
			
			wb.write(new FileOutputStream("src/test/resources/data/ip_16_loc.xlsx"));
			
			
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
