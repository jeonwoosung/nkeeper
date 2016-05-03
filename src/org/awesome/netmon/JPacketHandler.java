package org.awesome.netmon;

import org.awesome.netmon.filter.Filter;
import org.awesome.netmon.filter.FilterChain;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

public class JPacketHandler implements PcapPacketHandler<String> {
	public void nextPacket(PcapPacket packet, String user) {
		// packet이 tcp일 경우에만 동작.
		if (packet.hasHeader(new Udp())&&packet.hasHeader(new Ip4())){
			Ip4 ip = packet.getHeader(new Ip4());
			
			byte[] bSrcIp = ip.source();
			byte[] bDestIp = ip.destination();

			Udp udp = packet.getHeader(new Udp());

			String srcIp = (String.valueOf((bSrcIp[0] & 0xff)) + "." + String.valueOf((bSrcIp[1] & 0xff)) + "." + String.valueOf((bSrcIp[2] & 0xff)) + "." + String.valueOf((bSrcIp[3] & 0xff)));
			int srcPort = udp.source();

			String destIp = (String.valueOf((bDestIp[0] & 0xff)) + "." + String.valueOf((bDestIp[1] & 0xff)) + "." + String.valueOf((bDestIp[2] & 0xff)) + "." + String.valueOf((bDestIp[3] & 0xff)));
			int destPort = udp.destination();

			if(destIp.equals("10.10.10.129"))
				System.out.println(srcIp + ":" + srcPort + "==>"+destIp + ":" + destPort);
			return;
		}
		
		if (!packet.hasHeader(new Tcp()))
			return;

		try {
			Filter filter = FilterChain.getFilter();
			filter.doFilter(packet);
		} catch (Exception e) {
			if (!(e instanceof NullPointerException))
				e.printStackTrace();
		}
	}
}