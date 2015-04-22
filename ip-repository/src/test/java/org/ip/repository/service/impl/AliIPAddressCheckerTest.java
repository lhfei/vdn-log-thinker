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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.ip.repository.bean.ali.AliIPBean;
import org.ip.repository.service.IPAddressChecker;
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
 * @since  Apr 15, 2015
 */
public class AliIPAddressCheckerTest {
	private static final Logger log = LoggerFactory.getLogger(AliIPAddressChecker.class);

	@Test
	public void check() throws JsonParseException, JsonMappingException, IOException {
		AliIPAddressChecker checker = new AliIPAddressChecker();
		
		AliIPBean ip = (AliIPBean)checker.ipcheck("60.153.88.50");
		log.info("{}", ip.getIpString());
		
	}
	@Test
	public void ipCheckByGet() throws JsonParseException, JsonMappingException, IOException {
		AliIPAddressChecker checker = new AliIPAddressChecker();
		
		AliIPBean ip = (AliIPBean)checker.ipCheckByGet("66.66.109.12");
		log.info("{}", ip.getIpString());
		
	}
	
	@Test
	public void get() {
		String[] ins = new String[]{"src/test/resources/data/ip_17.xlsx", "src/test/resources/data/ip_18.xlsx"};
		String[] outs = new String[]{"src/test/resources/data/ip_17_ali_UTF-8.txt", "src/test/resources/data/ip_18_ali_UTF-8.txt"};
		
		for(int i = 0; i < 2; i++){
			this.wrap(ins[i], outs[i]);
		}
	}
	
	public void wrap(String input, String output) {
		Workbook wb = null;
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(output, "UTF-8");
			
			IPAddressChecker checker = new AliIPAddressChecker();
			wb = WorkbookFactory.create(new FileInputStream(input));
			Sheet sheet = wb.getSheetAt(0);
			
			int totalRows = sheet.getPhysicalNumberOfRows();
			
			Cell ipCell = null;
			Cell locCell = null;
			
			String location = "";
			String ipString = "";
			
			for (int i = 1; i < totalRows; i++) {
				Row row = sheet.getRow(i);
				
				ipCell = row.getCell(1);
				locCell = row.getCell(3);
				
				try {
					ipString = ipCell.getStringCellValue();
					AliIPBean ip = (AliIPBean)checker.ipcheck(ipString);
					location = ip.getIpString();
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					location = "ERROR";
				} finally{
					//locCell.setCellValue(location);
					
					pw.append(ipString +" "+ location);
					pw.println();
				}
			}
			
			wb.write(new FileOutputStream(output));
			
		} catch (InvalidFormatException | IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			if(wb != null)
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			pw.flush();
			pw.close();
		}
	}

}
