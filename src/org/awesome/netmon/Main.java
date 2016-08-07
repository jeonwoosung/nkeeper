package org.awesome.netmon;

import java.util.ArrayList;
import java.util.List;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

public class Main {
	public static void main(String[] args) {
		Main exam = new Main();
		exam.capture();
	}

	public static Pcap pcap=  null;
	public void capture() {
		List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with
		StringBuilder errbuf = new StringBuilder(); // For any error msgs

		int r = Pcap.findAllDevs(alldevs, errbuf);
		if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
			System.err.printf("Can't read list of devices, error is %s", errbuf.toString());
			return;
		}

		System.out.println("Network devices found:");

		int i = 0;
		for (PcapIf device : alldevs) {
			String description = (device.getDescription() != null) ? device.getDescription() : "No description available";
			System.out.printf("#%d: %s [%s]\n", i++, device.getName(), description);
		}

		PcapIf device = alldevs.get(5); // We know we have atleast 1 device

		System.out.printf("\nChoosing '%s' on your behalf:\n", (device.getDescription() != null) ? device.getDescription() : device.getName());

		int snaplen = 64 * 1024; // Capture all packets, no trucation
		int flags = Pcap.MODE_PROMISCUOUS; // capture all packets
		int timeout = 10 * 1000; // 10 seconds in millis
		pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);
		
		// compile to filtering
		//pcap.compile(program, str, optimize, netmask)
		if (pcap == null) {
			System.err.printf("Error while opening device for capture: " + errbuf.toString());
			return;
		}
		
		PacketSender.put(pcap);

		pcap.loop(0, new JPacketHandler(), device.getName());

		pcap.close();
	}

}
