package com.luv2code.springdemo.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class InstallationUtility {
	
	public static Map getIPs() {
		Map<String, String> ips = new LinkedHashMap<String, String>();
		ips.put("10.30.32.16", "10.30.32.16");
		ips.put("10.30.32.76", "10.30.32.76");
		ips.put("10.30.32.83", "10.30.32.83");
		ips.put("10.30.32.101", "10.30.32.101");
		ips.put("10.30.32.126", "10.30.32.126");
		ips.put("10.30.32.147", "10.30.32.147");
		ips.put("10.30.32.166", "10.30.32.166");
		ips.put("10.30.32.167", "10.30.32.167");
		ips.put("10.30.32.168", "10.30.32.168");
		ips.put("10.30.32.123", "10.30.32.123");
		return ips;
	}
	
	public static Map getEnvironmentTypes() {
		Map<String, String> environmentTypes = new LinkedHashMap<String, String>();
		environmentTypes.put("ICS IC", "ICS IC");
		environmentTypes.put("ICS EC", "ICS EC");
		environmentTypes.put("SOA 12C", "SOA 12C");
		environmentTypes.put("SOA 11G", "SOA 11G");
		return environmentTypes;
	}
	
	public static Map getInstallationStatuses() {
		Map<String, String> installationStatuses = new LinkedHashMap<String, String>();
		installationStatuses.put("A", "Active");
		installationStatuses.put("I", "Inactive");
		return installationStatuses;
	}
	

}
