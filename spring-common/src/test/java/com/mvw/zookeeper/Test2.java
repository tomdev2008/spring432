package com.mvw.zookeeper;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Test2 {
	
	private ZooKeeper zk=null;
	
	@Before
	public void init() throws Exception{
		zk = new ZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 3000,new Watcher(){
			@Override
			public void process(WatchedEvent event) {
				System.out.println(event);
			}
		});
	}
	
	@After
	public void close() throws Exception{
		if(zk!=null){
			zk.close();
		}
	}

	@Test
	public void test2() throws Exception{
		
		ZooKeeper z = new ZooKeeper("127.0.0.1:2181", 3000,new Watcher(){

			@Override
			public void process(WatchedEvent event) {
				System.out.println(event);
			}
			
		});
		
		/*
		 * 创建节点:必须得保证父节点存在
		 * 
		 * 1.瞬时的(会话在则在)
		 * 2.持久的
		 * 3.瞬时的_版本号
		 * 4.持久的_版本号
		 */
		z.create("/abc", "abc".getBytes("utf-8"), Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
		z.create("/abc/123", "123".getBytes("utf-8"), Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
		
		//监听节点,这是一次性的事件  watchNode(path,hander)
		z.exists("/abc", new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				System.out.println(event);
			}
		});
		
		//获得子节点和数据
		System.out.println(z.getChildren("/abc", true));
		System.out.println(new String(z.getData("/abc/123", true, null)));
		
		z.close();
	}
	
	@Test
	public void test22() throws Exception{
		
		List<String> list = zk.getChildren("/", null);
		
		System.out.println(list);
	}
	
	//递归下所有目录
}
