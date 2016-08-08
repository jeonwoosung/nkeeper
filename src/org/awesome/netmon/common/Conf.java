package org.awesome.netmon.common;

import java.util.Hashtable;

public class Conf {
	
	private static Hashtable<String, Object> map= new Hashtable<String, Object>();
	
	static{
		map.put("blockport", "9200,5601");
	}
	
	public static String get(String key){
		return (String) map.get(key);
	}

}
