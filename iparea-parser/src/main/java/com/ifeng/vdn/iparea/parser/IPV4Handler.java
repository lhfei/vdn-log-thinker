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
package com.ifeng.vdn.iparea.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0.0
 *
 * @author Hefei Li
 *
 * @since Jul 2, 2015
 */
public class IPV4Handler {

	public static Long toLong(String ipAddress) {
		if (ipAddress == null || ipAddress.isEmpty()) {
			throw new IllegalArgumentException(
					"ip address cannot be null or empty");
		}
		String[] octets = ipAddress.split(java.util.regex.Pattern.quote("."));
		if (octets.length != 4) {
			throw new IllegalArgumentException("invalid ip address");
		}
		long ip = 0;
		for (int i = 3; i >= 0; i--) {
			long octet = Long.parseLong(octets[3 - i]);
			if (octet > 255 || octet < 0) {
				throw new IllegalArgumentException("invalid ip address");
			}
			ip |= octet << (i * 8);
		}
		return ip;
	}

	public static String long2IP(long ip) {
		// if ip is bigger than 255.255.255.255 or smaller than 0.0.0.0
		if (ip > 4294967295l || ip < 0) {
			throw new IllegalArgumentException("invalid ip");
		}
		StringBuilder ipAddress = new StringBuilder();
		for (int i = 3; i >= 0; i--) {
			int shift = i * 8;
			ipAddress.append((ip & (0xff << shift)) >> shift);
			if (i > 0) {
				ipAddress.append(".");
			}
		}
		return ipAddress.toString();
	}

	public static List<String> getAllFromRange(String start, String end) {
		List<Long> ips = new ArrayList<Long>();
		List<String> ipAddresses = new ArrayList<String>();

		Long startIP = toLong(start);
		Long endIP = toLong(end);

		for (; startIP <= endIP; startIP++) {
			ips.add(startIP);
		}

		for (Long ip : ips) {
			ipAddresses.add(long2IP(ip));
		}

		return ipAddresses;
	}

}
