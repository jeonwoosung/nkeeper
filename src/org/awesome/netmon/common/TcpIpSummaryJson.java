package org.awesome.netmon.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;

public class TcpIpSummaryJson {

	private String destIp;
	private String srcIp;
	private int srcPort;
	private int destPort;
	private String flags;
	// private long seq;
	// private long ack;
	// private long tsval;
	// private long tsecr;
	private int payloadLength;

	public int getPayloadLength() {
		return payloadLength;
	}

	public void setPayloadLength(int tcpSize) {
		this.payloadLength = tcpSize;
	}

	private long ts;
	public String getSrcDomain() {
		return srcDomain;
	}

	public void setSrcDomain(String srcDomain) {
		this.srcDomain = srcDomain;
	}

	public String getDestDomain() {
		return destDomain;
	}

	public void setDestDomain(String destDomain) {
		this.destDomain = destDomain;
	}

	private String srcDomain;
	private String destDomain;

	public long getTs() {
		return ts;
	}

	public void setTs(long l) {
		this.ts = l;
	}

	public String getDestIp() {
		return destIp;
	}

	public String getSrcIp() {
		return srcIp;
	}

	public int getSrcPort() {
		return srcPort;
	}

	public int getDestPort() {
		return destPort;
	}

	public String getFlag() {
		return flags;
	}

	public TcpIpSummaryJson(PcapPacket packet) {
		Ip4 ip = packet.getHeader(new Ip4());
		Tcp tcp = packet.getHeader(new Tcp());
		byte[] bSrcIp = ip.source();
		byte[] bDestIp = ip.destination();

		this.srcIp = (String.valueOf((bSrcIp[0] & 0xff)) + "." + String.valueOf((bSrcIp[1] & 0xff)) + "."
				+ String.valueOf((bSrcIp[2] & 0xff)) + "." + String.valueOf((bSrcIp[3] & 0xff)));
		this.srcPort = tcp.source();

		try {
			String name = InetAddress.getByName(srcIp).getHostName();
			this.srcDomain= !srcIp.equals(name)?name:null;
		} catch (UnknownHostException e) {
		}

		this.destIp = (String.valueOf((bDestIp[0] & 0xff)) + "." + String.valueOf((bDestIp[1] & 0xff)) + "."
				+ String.valueOf((bDestIp[2] & 0xff)) + "." + String.valueOf((bDestIp[3] & 0xff)));
		this.destPort = tcp.destination();

		try {
			String name = InetAddress.getByName(destIp).getHostName();
			this.destDomain= !destIp.equals(name)?name:null;
		} catch (UnknownHostException e) {
		}


		ControlFlag f = new ControlFlag(tcp.flags());
		this.flags = f.toString();

		// this.ack = tcp.ack();

		this.payloadLength = tcp.getPayloadLength();

	}

	// public String toString() {
	// ControlFlag flag = new ControlFlag(flags);
	//
	// return srcIp + ":" + srcPort + " ==> " + destIp + ":" + destPort + " " +
	// flag + " seq: " + seq + " ack: " + ack
	// + " tsval: " + tsval + " tsecr: " + tsecr + " size: " + tcpSize;
	// }

}
