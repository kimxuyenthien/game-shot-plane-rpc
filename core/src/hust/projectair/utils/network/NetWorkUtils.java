package hust.projectair.utils.network;


import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.*;



public class NetWorkUtils {



	public static String getNamePC()
	{
		String hostname = "Unknown";

		//RuntimeMXBean rmx = ManagementFactory.getRuntimeMXBean();

		return hostname;
	}

	public static String getIpBroascast()
	{
		String ip = "";

		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface
					.getNetworkInterfaces();

			if (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = interfaces.nextElement();

				if (networkInterface.isLoopback()) {
					return "";
				}

				for (final InterfaceAddress addr : networkInterface
						.getInterfaceAddresses()) {
					final InetAddress inet_addr = addr.getAddress();

					if (!(inet_addr instanceof Inet4Address)) {
						continue;
					}

					ip = addr.getBroadcast().getHostAddress();

				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		return ip;
	}


	public static String getCurrentIpAndroid()
	{
		List<String> addresses = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			for(NetworkInterface ni : Collections.list(interfaces)){
				for(InetAddress address : Collections.list(ni.getInetAddresses()))
				{
					if(address instanceof Inet4Address){
						addresses.add(address.getHostAddress());
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		String ipAddress = new String("");
		for(String str:addresses)
		{
			if(!str.startsWith("127.0.0"))
			{
				ipAddress = str;
			}

		}

		return ipAddress.trim();
	}


	public static String getCurrentIp() {

		String ip = "";

		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface
					.getNetworkInterfaces();

			if (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = interfaces.nextElement();

				if (networkInterface.isLoopback()) {
					return "";
				}

				for (final InterfaceAddress addr : networkInterface
						.getInterfaceAddresses()) {
					final InetAddress inet_addr = addr.getAddress();

					if (!(inet_addr instanceof Inet4Address)) {
						continue;
					}

					ip = inet_addr.getHostAddress();

				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		return ip;
	}



	public static List<String> getQuickListIpLan(String currentIp) {

		List<String> listClient = new ArrayList<String>();

		String[] paras = currentIp.split("\\.");
		String preIp = "";
		if(paras.length == 4)
		{
			preIp = paras[0] + "." + paras[1] + "." + paras[2] + ".";
		}
		else
		{
			return new ArrayList<String>();
		}
		

		if (currentIp.startsWith("192.168.")) {


			// tesst
			for (int j = 0; j < 255; j++) {

				StringBuffer ipBuff = new StringBuffer();
				ipBuff.append(preIp);

				ipBuff.append(j);

				String ip = ipBuff.toString();

				listClient.add(ip);
			}

			listClient.remove("192.168.0.0");
			listClient.remove(currentIp);



		} else if (currentIp.startsWith("10.")) {

		
			for (int k = 0; k < 256; k++) {
				StringBuffer ipBuff = new StringBuffer();
				ipBuff.append(preIp);
				ipBuff.append(k);
				String ip = ipBuff.toString();

				listClient.add(ip);
			}

			listClient.remove("10.0.0.0");
			listClient.remove(currentIp);


		} else {
			for (int j = 180; j < 255; j++) {

				StringBuffer ipBuff = new StringBuffer();
				ipBuff.append(preIp);

				ipBuff.append(j);

				String ip = ipBuff.toString();

				listClient.add(ip);
			}

			listClient.remove(preIp+"0");
			listClient.remove(currentIp);


		}
		return listClient;
	}

	public static List<String> getFullListIpInLan(String currentIp) {
		List<String> listClient = new ArrayList<String>();

		if (currentIp.startsWith("192.168.")) {

			for (int i = 0; i < 256; i++) {

				for (int j = 0; j < 256; j++) {

					StringBuffer ipBuff = new StringBuffer();
					ipBuff.append("192.168.");
					ipBuff.append(i + ".");
					ipBuff.append(j);

					String ip = ipBuff.toString();

					listClient.add(ip);
				}
			}

			listClient.remove("192.168.0.0");
			listClient.remove(currentIp);

			return listClient;

		} else if (currentIp.startsWith("10.")) {
			for (int i = 0; i < 256; i++) {
				for (int j = 0; j < 256; j++) {

					for (int k = 0; k < 256; k++) {
						StringBuffer ipBuff = new StringBuffer();
						ipBuff.append("10.");
						ipBuff.append(i + ".");
						ipBuff.append(j + ".");
						ipBuff.append(k);
						String ip = ipBuff.toString();

						listClient.add(ip);
					}

				}
			}

			listClient.remove("10.0.0.0");
			listClient.remove(currentIp);

			return listClient;
		} else {
			return new ArrayList<String>();
		}
	}

	public static List<String> listIpOpenPort(List<String> ips, int port) {

		List<String> ipOnline = new ArrayList<String>();




		for (String ip : ips) {



			try {

				final ExecutorService es = Executors.newFixedThreadPool(20);

				final int timeout = 200;
				final List<Future<ScanResult>> futures = new ArrayList<Future<ScanResult>>();

				futures.add(portIsOpen(es, ip, port, timeout));
				es.awaitTermination(200L, TimeUnit.MILLISECONDS);

				System.out.print("ip :" + ip);

				for (final Future<ScanResult> result : futures) {
					if (result.get().isOpen()) {
						ipOnline.add(ip);
				
					}
				}



				System.out.println();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return ipOnline;

	}

	public static Future<ScanResult> portIsOpen(final ExecutorService es,
			final String ip, final int port, final int timeout) {

		Future<ScanResult> scanResultFuture = es.submit(new Callable<ScanResult>() {
			@Override
			public ScanResult call() {
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(ip, port), timeout);
					socket.close();
					return new ScanResult(port, true);

				} catch (Exception ex) {
					return new ScanResult(port, false);
				}
			}
		});


		return scanResultFuture;
	}


}
