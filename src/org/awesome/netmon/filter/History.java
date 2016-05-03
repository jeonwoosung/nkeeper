package org.awesome.netmon.filter;

import java.util.HashMap;
import java.util.LinkedList;

import org.awesome.netmon.common.TcpIpSummary;

public class History {

	private static HashMap<String, LinkedList<TcpIpSummary>> map = new HashMap<String, LinkedList<TcpIpSummary>>();

	public static LinkedList<TcpIpSummary> get(String srcIp, int srcPort, String destIp, int destPort) {

		String key = srcIp + ":" + srcPort + destIp + ":" + destPort;
		
		if(!map.containsKey(key))
			map.put(key, new LinkedList<TcpIpSummary>());
		
		
		return map.get(key);
	}

}
