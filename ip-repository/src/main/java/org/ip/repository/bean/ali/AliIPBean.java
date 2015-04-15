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
package org.ip.repository.bean.ali;

import java.io.Serializable;

import org.ip.repository.bean.IPAddress;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since Apr 15, 2015
 */
public class AliIPBean extends IPAddress implements Serializable {

	private static final long serialVersionUID = -4853982034134533558L;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public AliIPWrapper getData() {
		return data;
	}
	public void setData(AliIPWrapper data) {
		this.data = data;
	}
	private String code;
	private AliIPWrapper data = new AliIPWrapper();

	@Override
	public String getIpString() {
		return data.toString();
	}
}
