package org.awesome.netmon.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.awesome.netmon.PacketSender;
import org.awesome.netmon.common.ControlFlag;
import org.awesome.netmon.common.PortLoop;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;

public class PortFilter implements Filter {

	private Filter filter;

	public PortFilter(Filter nextFilter) {
		this.filter = nextFilter;
	}

	@Override
	public void doFilter(PcapPacket packet) {
		final Tcp tcp = packet.getHeader(new Tcp());
		final Ip4 ip = packet.getHeader(new Ip4());

		PortLoop loop = new PortLoop() {
			public boolean loop(int port) {
				if ((tcp.destination() == port || tcp.source() == port) && (tcp.flags_SYN())) {

					PcapPacket dummyPacket = null;
					dummyPacket = new PcapPacket(finAckPacket);

					Tcp tcp2 = dummyPacket.getHeader(new Tcp());
					Ip4 ip2 = dummyPacket.getHeader(new Ip4());

					tcp2.flags(0);
					tcp2.flags_ACK(true);
					tcp2.flags_RST(true);

					int temp = tcp.source();
					tcp2.source(tcp.destination());
					tcp2.destination(temp);

					byte[] ipTemp = ip.source();
					ip2.source(ip.destination());
					ip2.destination(ipTemp);

					long seq = tcp.seq();
					tcp2.seq(tcp.ack());
					tcp2.ack(seq);

					tcp2.checksum(tcp2.calculateChecksum());
					ip2.checksum(ip2.calculateChecksum());

					PacketSender.sendPacket(dummyPacket);

					return true;
				}
				if ((tcp.destination() == port || tcp.source() == port) && (tcp.flags() == ControlFlag.ACK && !tcp.flags_FIN())) {
					
					PcapPacket dummyPacket = null;
					dummyPacket = new PcapPacket(finAckPacket);
					
					Tcp tcp2 = dummyPacket.getHeader(new Tcp());
					Ip4 ip2 = dummyPacket.getHeader(new Ip4());
					
					tcp2.flags(0);
					tcp2.flags_ACK(true);
					tcp2.flags_FIN(true);
					
					int temp = tcp.source();
					tcp2.source(tcp.destination());
					tcp2.destination(temp);
					
					byte[] ipTemp = ip.source();
					ip2.source(ip.destination());
					ip2.destination(ipTemp);
					
					long seq = tcp.seq();
					tcp2.seq(tcp.ack());
					tcp2.ack(seq);
					
					tcp2.checksum(tcp2.calculateChecksum());
					ip2.checksum(ip2.calculateChecksum());
					
					PacketSender.sendPacket(dummyPacket);
					
					return true;
				}

				return false;

			}

		};

		loop.start();

		if (filter != null)
			filter.doFilter(packet);
	}

	private static byte[] finAckPacket;

	static {
		try {

			URL url = ClassLoader.getSystemResource("packetsample");
			InputStream input = PortFilter.class.getClassLoader().getResourceAsStream("packetsample");

			File f = new File(url.getFile());
			FileInputStream fis = new FileInputStream(url.getFile());
			byte[] b = new byte[(int) f.length()];
			input.read(b);
			finAckPacket = b;
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
