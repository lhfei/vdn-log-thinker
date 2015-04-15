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
package org.ip.repository.bean.baidu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since  Apr 10, 2015
 */
@JsonIgnoreProperties({"titlecont", "origipquery", "showlamp", "showLikeShare", "shareImage", "ExtendedLocation", "OriginQuery", "tplt", "resourceid" , "fetchkey", "appinfo", "role_id", "disp_type"})
public class IPLocation {

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getOrigip() {
		return origip;
	}
	public void setOrigip(String origip) {
		this.origip = origip;
	}
	private String location;
	private String origip;
}
