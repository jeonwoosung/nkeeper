package org.awesome.netmon.common;

public abstract class PortLoop {

	String[] strPorts = Conf.get("blockport").split(",");

	public void start() {
		for (String strPort : strPorts) {
			int port = Integer.valueOf(strPort);
			if(loop(port))
				break;
		}

	}

	public abstract boolean loop(int port);

}
