package org.awesome.netmon.filter;

import org.awesome.netmon.common.PortLoop;
import org.awesome.netmon.common.TcpIpSummary;
import org.jnetpcap.packet.PcapPacket;

/**
 * 
 * @author Àü¿ì¼º
 *
 */
public class StoreFilter implements Filter {

	private Filter nextFilter;

	public StoreFilter(Filter nextFilter) {
		this.nextFilter = nextFilter;
	}

	@Override
	public void doFilter(PcapPacket packet) {
		TcpIpSummary s = new TcpIpSummary(packet);

		PortLoop loop = new PortLoop() {
			public boolean loop(int port) {
				if (s.getDestPort() == port || s.getSrcPort() == port) {
					System.out.println(System.currentTimeMillis() + " "+ s);
					return true;
				}
				return false;
			}
		};
		loop.start();

		nextFilter.doFilter(packet);

	}

}
