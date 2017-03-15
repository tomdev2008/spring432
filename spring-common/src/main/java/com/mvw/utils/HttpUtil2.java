package com.mvw.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.util.CharArrayBuffer;

/**
 * jdk原生:get/post 不处理什么头，cookie等
 * 1.get/post
 * 2.超时机制
 */
public class HttpUtil2 {
	
	public static String get(String get) throws Exception {
		
		URL url = new URL(get);
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(false);
		connection.setDoInput(true);
	
		//connection.connect(); //getXxx会隐试的调用
		
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		//返回状态
		System.out.println(connection.getResponseCode());
		
		//长度
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

		//断开连接
		in.close();
		connection.disconnect();

		return buffer.toString();
	}
	
	public static String post(String get) throws Exception {
		
		URL url = new URL(get);
		
		//建连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true); //post必须设置
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setConnectTimeout(3000);//连接超时 单位毫秒
		connection.setReadTimeout(3000);//读取超时 单位毫秒
		connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		
		//发数据  顺序很重要
        PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
        printWriter.write(""); //格式是  k=v&k=v
        printWriter.close();
		
        //接收数据
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		System.out.println(connection.getResponseCode());
		int i = connection.getContentLength();
		if (i < 0) {
			i = 4096;
		}
		
		/**
		 * in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while((len=bis.read(arr))!= -1){
                bos.write(arr,0,len);
                bos.flush();
            }
            bos.close();
		 */
		//或 ByteArrayOutputStream
		final CharArrayBuffer buffer = new CharArrayBuffer(i);
		final char[] tmp = new char[1024];
		int l;
		while ((l = in.read(tmp)) != -1) {
			buffer.append(tmp, 0, l);
		}

		//断开连接
		in.close();//释放与此实例关联的网络资源
		connection.disconnect(); //关闭基础套接字

		return buffer.toString();
	}
	
	public static void main(String[] args) throws Exception {
		String str = get("https://www.baidu.com");
		System.out.println(str);
	}
}
