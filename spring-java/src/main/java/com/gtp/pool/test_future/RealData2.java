package com.gtp.pool.test_future;

import java.util.concurrent.Callable;

/**
 * 模拟返回很慢的数据
 * 
 * @author gaotingping
 *
 * 2017年2月3日 上午11:05:01
 */
public class RealData2 implements Callable<String> {
	
	protected String data;

	public RealData2(String data) {
		this.data = data;
	}

	@Override
	public String call() throws Exception {
		//利用sleep方法来表示真是业务是非常缓慢的
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return data;
	}
}
