package org.spring_scottmap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	public static String get(String get) throws Exception {
		
		URL url = new URL(get);
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(false);
		connection.setDoInput(true);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		StringBuffer tmp = new StringBuffer();
		char[] buf = new char[1024];
		int len = in.read(buf);
		while ((len = in.read(buf)) > 0) {
			tmp.append(new String(buf, 0, len));
		}

		in.close();
		connection.disconnect();

		return tmp.toString();
	}
}
