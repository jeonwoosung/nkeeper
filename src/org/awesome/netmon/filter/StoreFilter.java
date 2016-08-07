package org.awesome.netmon.filter;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.awesome.netmon.common.PortLoop;
import org.awesome.netmon.common.TcpIpSummary;
import org.awesome.netmon.common.TcpIpSummaryJson;
import org.codehaus.jackson.map.ObjectMapper;
import org.jnetpcap.packet.PcapPacket;

/**
 * 
 *
 */
public class StoreFilter implements Filter {

	private Filter nextFilter;

	public StoreFilter(Filter nextFilter) {
		this.nextFilter = nextFilter;
	}

	@Override
	public void doFilter(final PcapPacket packet) {
//		final TcpIpSummary s = new TcpIpSummary(packet);

		final TcpIpSummaryJson s = new TcpIpSummaryJson(packet);
		PortLoop loop = new PortLoop() {
			public boolean loop(int port) {
				if (s.getDestPort() == port || s.getSrcPort() == port) {
					
					

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
//					System.out.println( sdf.format(new  Date()) );

					s.setTs(sdf.format(new  Date()));

					System.out.println(System.currentTimeMillis() + " " + s);

					if(s.getDestIp().equals("175.126.112.84"))
						return true;
					
					// JSONObject o new JSONObject(s);

					ObjectMapper mapper = new ObjectMapper();

					// Object to JSON in file
					// mapper.writeValue(new File("c:\\user.json"), user);

					// Object to JSON in String
					try {
						String j = mapper.writeValueAsString(s);
						System.out.println(j);

						HttpClient httpclient = HttpClientBuilder.create().build();
						HttpPost p = new HttpPost("http://175.126.112.84:9200/blog/post/"+System.currentTimeMillis());

						ByteArrayEntity e = new ByteArrayEntity(j.getBytes());

						p.setEntity(e);

						HttpResponse r = httpclient.execute(p);

						HttpEntity e2 = r.getEntity();
						InputStream is = e2.getContent();
						int i = 0;

						StringBuffer b = new StringBuffer();
						while (i != -1) {
							i = is.read();
							b.append((char) i);
						}
						
						System.out.println(b.toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
					return true;
				}
				return false;
			}
		};
		loop.start();


		nextFilter.doFilter(packet);
	}

}
