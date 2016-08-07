package org.awesome.netmon.common;

import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;

public class TcpIpSummary {

	private String destIp;
	private String srcIp;
	private int srcPort;
	private int destPort;
	private int flags;
	private long seq;
	private long ack;
	private long tsval;
	private long tsecr;
	private byte[] bSrcIp;
	private byte[] bDestIp;
	private int tcpSize;

	public byte[] getbSrcIp() {
		return bSrcIp;
	}

	public byte[] getbDestIp() {
		return bDestIp;
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

	public int getFlags() {
		return flags;
	}

	public long getSeq() {
		return seq;
	}

	public long getAck() {
		return ack;
	}

	public long getTsval() {
		return tsval;
	}

	public long getTsecr() {
		return tsecr;
	}

	public TcpIpSummary(PcapPacket packet) {
		Ip4 ip = packet.getHeader(new Ip4());
		Tcp tcp = packet.getHeader(new Tcp());
		this.bSrcIp = ip.source();
		this.bDestIp = ip.destination();

		this.srcIp = (String.valueOf((bSrcIp[0] & 0xff)) + "." + String.valueOf((bSrcIp[1] & 0xff)) + "."
				+ String.valueOf((bSrcIp[2] & 0xff)) + "." + String.valueOf((bSrcIp[3] & 0xff)));
		this.srcPort = tcp.source();

		this.destIp = (String.valueOf((bDestIp[0] & 0xff)) + "." + String.valueOf((bDestIp[1] & 0xff)) + "."
				+ String.valueOf((bDestIp[2] & 0xff)) + "." + String.valueOf((bDestIp[3] & 0xff)));
		this.destPort = tcp.destination();
		this.flags = tcp.flags();

		this.seq = tcp.seq();
		this.ack = tcp.ack();

		this.tcpSize = tcp.getPayloadLength();

		Tcp.Timestamp tsource = null;
		if (tcp.hasSubHeader(new Tcp.Timestamp())) {
			tsource = tcp.getSubHeader(new Tcp.Timestamp());
			this.tsval = tsource.tsval();
			this.tsecr = tsource.tsecr();

		}

	}

	public String toString() {
		ControlFlag flag = new ControlFlag(flags);

		return srcIp + ":" + srcPort + " ==> " + destIp + ":" + destPort + " " + flag + " seq: " + seq + " ack: " + ack
				+ " tsval: " + tsval + " tsecr: " + tsecr + " size: " + tcpSize;
	}

}
