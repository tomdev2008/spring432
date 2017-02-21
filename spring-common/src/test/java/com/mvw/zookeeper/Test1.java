//package com.mvw.zookeeper;
//
//import java.util.List;
//
//import org.apache.zookeeper.WatchedEvent;
//import org.apache.zookeeper.Watcher;
//import org.apache.zookeeper.ZooKeeper;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//
//@ContextConfiguration(locations = { "spring/spring-zk.xml"})
//public class Test1 extends AbstractJUnit4SpringContextTests {
//
//	@Autowired
//	ZkService zk;
//	
//	@Test
//	public void test1(){
//		
//		String path="/abc/123";
//		String data="中国人";
//		
//		zk.createNode(path, data);
//		
//		byte[] b = zk.getData("/123", false, null);
//		System.out.println(new String(b));
//	}
//	
//	/*
//		//在root下面创建一个childone znode,数据为childone,不进行ACL权限控制，节点为永久性的
//		zk.create("/root/childone","childone".getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
//		
//		//取得/root节点下的子节点名称,返回List<String>
//		zk.getChildren("/root",true);
//		
//		//取得/root/childone节点下的数据,返回byte[]
//		zk.getData("/root/childone", true, null);
//		
//		//修改节点/root/childone下的数据，第三个参数为版本，如果是-1，那会无视被修改的数据版本，直接改掉
//		zk.setData("/root/childone","childonemodify".getBytes(), -1);
//		
//		//删除/root/childone这个节点，第二个参数为版本，－1的话直接删除，无视版本
//		zk.delete("/root/childone", -1);
//	 */
//	@Test
//	public void test2() throws Exception{
//		
//		ZooKeeper z = new ZooKeeper("127.0.0.1:2181", 3000,new Watcher(){
//
//			@Override
//			public void process(WatchedEvent event) {
//				System.out.println(event);
//			}
//			
//		});
//		
//		/*
//		 * 创建节点:必须得保证父节点存在
//		 * 
//		 * 1.瞬时的(会话在则在)
//		 * 2.持久的
//		 * 3.瞬时的_版本号
//		 * 4.持久的_版本号
//		 */
////		z.create("/abc", 
////				"abc".getBytes("utf-8"), 
////				Ids.OPEN_ACL_UNSAFE,
////				CreateMode.PERSISTENT);
////		z.create("/abc/123", 
////				"123".getBytes("utf-8"), 
////				Ids.OPEN_ACL_UNSAFE,
////				CreateMode.PERSISTENT);
//		
//		//监听节点,这是一次性的事件
//		z.exists("/abc", new Watcher() {
//			@Override
//			public void process(WatchedEvent event) {
//				System.out.println(event);
//			}
//		});
//		
//		System.out.println(z.getChildren("/abc", true));
//		System.out.println(new String(z.getData("/abc/123", true, null)));
//		
//		z.close();
//	}
//	
//	@Test
//	public void test22() throws Exception{
//		
//		ZooKeeper z = new ZooKeeper("127.0.0.1:2181", 3000,new Watcher(){
//
//			@Override
//			public void process(WatchedEvent event) {
//				System.out.println(event);
//			}
//			
//		});
//		
//		List<String> list = z.getChildren("/", null);
//		
//		System.out.println(list);
//	}
//}
