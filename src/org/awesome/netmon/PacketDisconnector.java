package org.awesome.netmon;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;

public class PacketDisconnector {
	public static void main(String args[]) throws IOException {

		List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with
		// NICs
		StringBuilder errbuf = new StringBuilder(); // For any error msgs

		int r = Pcap.findAllDevs(alldevs, errbuf);
		if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
			System.err.printf("Can't read list of devices, error is %s", errbuf.toString());
			return;
		}

		int i = 0;
		for (PcapIf device : alldevs) {
			String description = (device.getDescription() != null) ? device.getDescription() : "No description available";
			System.out.printf("#%d: %s [%s]\n", i++, device.getName(), description);
		}

		PcapIf device = alldevs.get(0); // We know we have atleast 1 device
		System.out.printf("\nChoosing '%s' on your behalf:\n", (device.getDescription() != null) ? device.getDescription() : device.getName());

		int snaplen = 64 * 1024; // Capture all packets, no trucation
		int flags = Pcap.MODE_PROMISCUOUS; // capture all packets
		int timeout = 10 * 1000; // 10 seconds in millis
		Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);

		if (pcap == null) {
			System.err.printf("Error while opening device for capture: " + errbuf.toString());
			return;
		}

		
		{
			int srcPort = 33402;
			int destPort = 23;
			File f = new File("c:/1");
			FileInputStream fis = new FileInputStream(f);
			byte[] b = new byte[(int) f.length()];

			fis.read(b);
			PcapPacket packet = new PcapPacket(b);

			Ip4 ip = packet.getHeader(new Ip4());
			Tcp tcp = packet.getHeader(new Tcp());
			tcp.flags_FIN(true);
			tcp.flags_ACK(true);
			
			tcp.seq(3017609704l);
			tcp.ack(3701749514l);
			Tcp.Timestamp tsource = tcp.getSubHeader(new Tcp.Timestamp());
			tsource.tsval(17535454);
			tsource.tsecr(16335324);
			
			tcp.source(srcPort);
			tcp.destination(destPort);

			tcp.checksum(tcp.calculateChecksum());
			pcap.sendPacket(packet);
		}
				
		
		pcap.close();
		
		
		
		
	}
}
