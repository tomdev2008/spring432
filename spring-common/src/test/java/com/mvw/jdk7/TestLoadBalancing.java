package com.mvw.jdk7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

/**
 * 负载均衡算法
 * 
 * 1.随机或轮训
 * 2.加权随机或轮训
 * 
 * @author gaotingping
 *
 * 2016年7月22日 上午9:30:04
 */
public class TestLoadBalancing {

	
	private AtomicInteger counter=new AtomicInteger(Integer.MAX_VALUE);
	
	private List<String> server=new ArrayList<String>();
	
	
	@Before
	public void init(){
		for(int i=0;i<5;i++){
			server.add("s_"+i);
		}
	}
	
	@Test //轮询:注意并发:这里其实是否控制并发都无所谓
	public void testPoll(){
		
		int curNum = counter.incrementAndGet();
		
		//溢出
		if(curNum<0){
			curNum=0;
			counter.set(0);
		}
		
		//索引
		int index=curNum%server.size();
		String s = server.get(index);
		
		System.out.println("当前是="+s);
	}
	
	
	/**
	 * 加权轮训的思想:建议别差距太远，<1:5
	 *    s0 --> 1
	 *    s1 --> 2
	 *    s3 --> 1
	 *    
	 * 最后将其组装为:
	 *    s0 s1 s1 s3
	 *    
	 * 然后轮训轮训这个新的服务列表
	 */
	@Test
	public void testWeightPoll(){
		
		//初始化
		Map<String, Integer> originServer=new HashMap<String, Integer>();
		for(int i=1;i<5;i++){
			originServer.put("s_"+i,i);
		}
		
		//加权初始化
		Iterator<String> it = originServer.keySet().iterator();
		List<String> weightServer=new ArrayList<String>();
		while(it.hasNext()){
			String s = it.next();
			Integer w = originServer.get(s);
			
			for(int i=0;i<w;i++){
				weightServer.add(s);
			}
		}
		
		//加权初始化的，服务列表都是顺序的连接的，这样不是太好
		//建议打乱一下:洗牌
		Collections.shuffle(weightServer);
		
		//show
		for(int i=0;i<weightServer.size();i++){
			System.out.println(weightServer.get(i));
		}
		
		//然后就在这个list中，循环取
	}
	
	/**
	 * 一致性hash:相同的请求打到同一个服务
	 * 一般用cookie标识
	 */
}