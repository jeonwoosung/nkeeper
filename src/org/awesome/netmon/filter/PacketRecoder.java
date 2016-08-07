package org.awesome.netmon.filter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.awesome.netmon.common.ControlFlag;
import org.awesome.netmon.common.PortLoop;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.tcpip.Tcp;

public class PacketRecoder implements Filter {

	@Override
	public void doFilter(final PcapPacket packet) {
		if(true)
			return;
		
		final Tcp tcp = packet.getHeader(new Tcp());
		PortLoop loop = new PortLoop() {
			public boolean loop(int port) {
				if ((tcp.destination() == port || tcp.source() == port) && (tcp.flags() == (ControlFlag.ACK | ControlFlag.FIN))) {
					
					try {
						System.out.println(packet);
						FileOutputStream fos = new FileOutputStream("c:/1");
						byte[] b = new byte[packet.getTotalSize()];
						packet.transferStateAndDataTo(b);
						fos.write(b);
						
						fos.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return true;
				}
				
				return false;
			}
			
		};
		
		loop.start();
		

	}

}
