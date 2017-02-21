package org.spring.zookeeper.api;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


//CRUD级别的演示例子
public class TestCRUD {
	
	private ZooKeeper zk=null;
	
	@Before
	public void init() throws Exception{
		zk = new ZooKeeper("127.0.0.1:2181", 3000,new Watcher(){
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
	public void test1() throws Exception{
		
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
		//可以监听一次，然后放弃或查询设置
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
	
	/*
	 #dubbo注册的数据结构
		/dubbo/dubboxtest.UserService/consumers#消费者
		/dubbo/dubboxtest.UserService/routers#路由
		/dubbo/dubboxtest.UserService/providers#提供者  
		/dubbo/dubboxtest.UserService/configurators#配置
	 */
	@Test //显示所有目录
	public void test2() throws Exception{
		showDir("/dubbo/com.alibaba.dubbo.monitor.MonitorService");
	}
	
	/*
		本地服务和远程服务可以通过配置解决
		收缩性特别强，真的很灵活的。
	 */
	@Test /*递归删除所有目录 KeeperErrorCode = Directory not empty for /dubbo*/
	public void test3() throws Exception{
		zk.delete("/dubbo", -1);
	}
	
	//递归显示zk下的所有目录
	public void showDir(String path) throws Exception{
		if(path!=null){
			List<String> list = zk.getChildren(path, null);
			if(list==null || list.isEmpty()){
				System.out.println(path);
			}else{
				for(String p:list){
					if(!"/".equals(path)){
						showDir(path+"/"+p);
					}else{
						showDir(path+p);
					}
				}
			}
		}
	}
	
	
	@Test
	public void test8(){
		try {
			ZooKeeper zk = new ZooKeeper("192.168.1.202:2181", 3000,null);
			zk.delete("/groups/trademanager/talentOnlineStatus", -1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
