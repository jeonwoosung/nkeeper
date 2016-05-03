package org.awesome.netmon.filter;

import org.jnetpcap.packet.PcapPacket;

public interface Filter {
	public void doFilter(PcapPacket packet);
}
