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
package com.ifeng.vdn.ip.repository.app;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.ifeng.vdn.ip.repository.bean.ali.AliIPBean;
import com.ifeng.vdn.ip.repository.service.impl.AliIPAddressChecker;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since Apr 21, 2015
 */
public class IPCheckApp {

	public static void main(String[] args) {
		Workbook wb = null;
		PrintWriter pw = null;
		try {
			
			pw = new PrintWriter(new FileOutputStream("src/test/resources/data/CDN_BAD.txt"), true);
			
			AliIPAddressChecker ipChecker = new AliIPAddressChecker();

			wb = WorkbookFactory.create(new FileInputStream("src/test/resources/data/CDN_BAD.xlsx"));
			Sheet sheet = wb.getSheetAt(0);

			int totalRows = sheet.getPhysicalNumberOfRows();
			Cell ipCell = null;
			//Cell locCell = null;

			List<String> ips = new ArrayList<String>();

			for (int i = 1; i < totalRows; i++) {
				Row row = sheet.getRow(i);
				ipCell = row.getCell(0);

				ips.add(ipCell.getStringCellValue());
			}

			for(String ip : ips){
				AliIPBean bean = (AliIPBean)ipChecker.ipcheck(ip);
				pw.println(ip +"-"+bean.getIpString() );
			}

		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		} finally {
			if (wb != null)
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(pw != null){
				pw.flush();
				pw.close();
			}
		}

	}

}
