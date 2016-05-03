package org.awesome.netmon.common;

public class ControlFlag {


	private int flag;

	public ControlFlag(int flag) {
		this.flag = flag;
	}
	
	public static int CRW=0b10000000;
	public static int ECE=0b01000000;
	public static int URG=0b00100000;
	public static int ACK=0b00010000;
	public static int PSH=0b00001000;
	public static int RST=0b00000100;
	public static int SYN=0b00000010;
	public static int FIN=0b00000001;

	String f[] = { "FIN", "SYN", "RST", "PSH", "ACK", "URG", "ECE", "CRW" };
	public String toString() {
		String retStr = "";

		for (int i = 0; i < f.length; i++) {
			if ((flag & (int)Math.pow(2, i)) > 0) {
				retStr += (f[i] + " ");
			}
		}

		return retStr;
	}
	

}
