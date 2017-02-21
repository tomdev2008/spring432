package com.mvw.jdk7;

import org.junit.Test;

/**
 * 能想到的负载算法
 * 
 * 1.随机/轮询:更大众化
 * 2.加权随机/轮询(全值大的随机几率越大):适合服务能力不均衡的情况
 * 3.ip hash:没有想到好的应用场景
 * 4.用户标示 hash如cookie记录，更适合session粘性服务
 *   同一个 client每次请求都将到达同一个 backend,这样更合适
 *   
 * 1.失效识别:失败自动迁移
 * 2.错误重试:最大重试此时
 */
public class TestLoading {

	@Test  //服务化框架实现方案
	public void test1(){
		/*
		 * 轮询: server=(计数器++)%总数
		 */
	}
	
	@Test //加权随机
	public void test2(){
//		Set<String> keySet = serverMap.keySet();
//		Iterator<String> it = keySet.iterator();
//
//		List<String> serverList = new ArrayList<String>();
//
//		while (it.hasNext()) {
//			String server = it.next();
//			Integer weight = serverMap.get(server);
//			for (int i = 0; i < weight; i++) {
//				serverList.add(server);
//			}
//		}		
//		Random random = new Random();
//		int randomPos = random.nextInt(serverList.size());
//		
//		String server = serverList.get(randomPos);
//		return server;
	}
	
	@Test
	public void test3(){
//		int hashCode =remoteIp.hashCode();
//		int serverListSize = keyList.size();
//		int serverPos = hashCode % serverListSize;
	}
}
