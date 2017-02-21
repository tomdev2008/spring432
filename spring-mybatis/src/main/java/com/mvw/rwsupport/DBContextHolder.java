package com.mvw.rwsupport;

/**
 * 线程本地工具: 在这里来标识本线程读写
 * 
 * @author gaotingping
 *
 * 2016年11月22日 下午4:56:55
 */
public class DBContextHolder {

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