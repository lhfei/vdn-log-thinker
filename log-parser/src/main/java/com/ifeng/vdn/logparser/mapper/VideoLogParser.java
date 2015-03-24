/*
 * Copyright 2010-2011 the original author or authors.
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
package com.ifeng.vdn.logparser.mapper;
/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since Mar 16, 2015
 */
public class VideoLogParser {
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public String getErr() {
		return err;
	}
	public void setErr(String err) {
		this.err = err;
	}
	public long getTm() {
		return tm;
	}
	public void setTm(long tm) {
		this.tm = tm;
	}
	public long getDur() {
		return dur;
	}
	public void setDur(long dur) {
		this.dur = dur;
	}
	public long getBt() {
		return bt;
	}
	public void setBt(long bt) {
		this.bt = bt;
	}
	public long getBl() {
		return bl;
	}
	public void setBl(long bl) {
		this.bl = bl;
	}
	public long getLt() {
		return lt;
	}
	public void setLt(long lt) {
		this.lt = lt;
	}
	
	private String id;		// 视频ID
	private String ip;		// 用户IP地址
	private String loc;		// VDN IP
	private String err;		// EventRetCode 
	private long tm;		// 当前系统时间戳(毫秒)
	private long dur;		// 视频总时长(毫秒)
	private long bt;		// 文件总大小(Byte)
	private long bl;		// 已加载文件大小(Byte)
	private long lt;		// 加载文件耗时(毫秒)

}
