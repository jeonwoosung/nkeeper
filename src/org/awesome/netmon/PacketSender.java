package org.awesome.netmon;

import org.jnetpcap.Pcap;
import org.jnetpcap.packet.PcapPacket;

public class PacketSender {
	
	private static Pcap pcap;
	
	public static void put(Pcap pcap2) {
		pcap = pcap2;
	}

	public static void sendPacket(PcapPacket dummyPacket) {
		pcap.sendPacket(dummyPacket);
	}
	

}
