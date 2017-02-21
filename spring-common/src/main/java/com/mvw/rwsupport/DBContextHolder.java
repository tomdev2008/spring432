package com.mvw.rwsupport;

//在这里来标识本线程读写
public class DBContextHolder {

	/*线程本地变量*/
	private static ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static String getDbType() {
		return contextHolder.get();
	}

	public static void setDbType(String str) {
		contextHolder.set(str);
	}

	public static void clearDBType() {
		contextHolder.remove();
	}
}