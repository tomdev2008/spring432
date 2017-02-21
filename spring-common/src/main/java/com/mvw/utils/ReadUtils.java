package com.mvw.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

public class ReadUtils {

	@Test
	public void test1() throws IOException {
		InputStreamReader in = new InputStreamReader(getFileStream("ids.txt"), "UTF-8");
		BufferedReader bufIn = new BufferedReader(in);
		String str = null;
		while ((str = bufIn.readLine()) != null) {
			System.out.println(str);
		}
		bufIn.close();
	}

	private InputStream getFileStream(String fileName) {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName);
		if(in==null){
			in=this.getClass().getResourceAsStream(fileName);
		}
		return in;
	}
/*	@Test
	public void test2() {
		this.getClass().getClassLoader().getResourceAsStream("");// ClassLoader.getResourceAsStream()
																	// 无论要查找的资源前面是否带'/'
																	// 都会从classpath的根路径下查找
		this.getClass().getResourceAsStream("");// 本包下，若以'/'开头的，那么就会从classpath的根路径下开始查找
	}*/
}