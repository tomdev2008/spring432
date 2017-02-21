package com.gtp.pool.test_future;

public class MyFutureTask {
	public MyData request(final String string) {
		final MyFutureData futureData = new MyFutureData();

		new Thread(new Runnable() {
			@Override
			public void run() {
				// RealData的构建很慢，所以放在单独的线程中运行
				MyRealData realData = new MyRealData(string);
				futureData.setRealData(realData);
			}
		}).start();

		return futureData; // 先直接返回FutureData
	}
}
