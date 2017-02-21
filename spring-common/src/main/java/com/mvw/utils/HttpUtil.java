package com.mvw.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

	public static String getHtml(String url) throws Exception {

		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {
			HttpGet httpget = new HttpGet(url);

			RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000)
					.setConnectionRequestTimeout(3000).build();

			httpget.setConfig(defaultRequestConfig);

			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				int statusCode = response.getStatusLine().getStatusCode();
				System.out.println(url+"\t"+statusCode);
				if (statusCode == 200 || 304 == statusCode) {
					String str = EntityUtils.toString(response.getEntity(), "UTF-8");
					return str;
				}
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
		return null;
	}
}
