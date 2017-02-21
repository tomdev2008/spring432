package com.gtp.pool.test_future;

public class MyRealData implements MyData {
	protected String data;

	public MyRealData(String data) {
		// 利用sleep方法来表示RealData构造过程是非常缓慢的
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.data = data;
	}

	@Override
	public String getResult() {
		return data;
	}
}
