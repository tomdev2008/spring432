package com.mvw.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.util.CharArrayBuffer;

public class HttpUtil2 {
	public static String get(String get) throws Exception {
		URL url = new URL(get);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(false);
		connection.setDoInput(true);
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		// StringBuffer tmp = new StringBuffer();
		// char[] buf = new char[1024];
		// int len = in.read(buf);
		// while ((len = in.read(buf)) > 0) {
		// 		tmp.append(new String(buf, 0, len));
		// }
		System.out.println(connection.getResponseCode());
		
		
		int i = connection.getContentLength();
		if (i < 0) {
			i = 4096;
		}
		final CharArrayBuffer buffer = new CharArrayBuffer(i);
		final char[] tmp = new char[1024];
		int l;
		while ((l = in.read(tmp)) != -1) {
			buffer.append(tmp, 0, l);
		}

		in.close();
		connection.disconnect();

		// return tmp.toString();
		return buffer.toString();
	}
	
	public static void main(String[] args) throws Exception {
		String str = get("https://www.baidu.com");
		System.out.println(str);
	}
}
