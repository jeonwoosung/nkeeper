package org.awesome.netmon.common;

import java.util.Hashtable;

public class Conf {
	
	private static Hashtable<String, Object> map= new Hashtable<String, Object>();
	
	static{
		map.put("blockport", "23,80,22,21,20,162");
	}
	
	public static String get(String key){
		return (String) map.get(key);
	}

}
