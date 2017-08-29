package cn.com.yto56.coresystem.common.stl.framework.base;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class HostInfo {

	public static Map<String, String> getHostInfo(){
		Map<String, String> result = new HashMap<String, String>();
		DecimalFormat df = new DecimalFormat("######0.00");
		Runtime run = Runtime.getRuntime();
		int mb = 1024 * 1024;
		result.put("totalMemory", df.format((run.totalMemory() / mb)) + "MB");
		result.put("freeMemory", df.format((run.freeMemory() / mb)) + "MB");
		result.put("maxMemory", df.format((run.maxMemory() / mb)) + "MB");
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(getHostInfo());
	}
	
	
}