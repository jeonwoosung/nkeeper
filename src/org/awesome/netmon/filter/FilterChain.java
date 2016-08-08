package org.awesome.netmon.filter;

public class FilterChain {
	public static Filter getFilter(){
		return 	new StoreFilter(null);
//		return 	new StoreFilter(new PortFilter(new PacketRecoder()));
	}

}
