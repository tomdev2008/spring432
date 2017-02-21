package org.spring.zookeeper.zkclient;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.junit.Test;

public class Demo1 {

	private String serverList = "127.0.0.1:2181";

	private ZkClient zk = new ZkClient(serverList);

	@Test // 创建
	public void test1() {
		ZkClient zk = new ZkClient(serverList);
		zk.create("/abc", null, CreateMode.EPHEMERAL);
		zk.close();
		
		/*
		 final String zkServers, 服务器列表
		 final int sessionTimeout, 回话超时，默认Int.max
		 final int connectionTimeout, //连接超时
		 final ZkSerializer zkSerializer, //序列化
		 final long operationRetryTimeout //操作重试超时，默认-1不限制
		 */
	}

	@Test // 是否存在
	public void test2() {
		ZkClient zk = new ZkClient(serverList);
		boolean result = zk.exists("/abc");
		System.out.println(result);
		zk.close();
	}

	/*
	 * zk.readData("/zkClient_01"); 
	 * zk.writeData
	 * 
	 * zk.getChildren(path);
	 * 
	 * zk.delete(path);//删除当前节点，有子节点无法删除 zk.deleteRecursive(path);//删除非子节点
	 * 
	 * //和官方zk有很大的差距 subscribeChildChanges subscribeDataChanges
	 */
	
	/**
	 	//setWatch() 
		org.I0Itec.zkclient.ZkClient.subscribeChildChanges(String, IZkChildListener)
		org.I0Itec.zkclient.ZkClient.unsubscribeChildChanges(String, IZkChildListener)
		org.I0Itec.zkclient.ZkClient.subscribeDataChanges(String, IZkDataListener)
		org.I0Itec.zkclient.ZkClient.unsubscribeDataChanges(String, IZkDataListener)
		org.I0Itec.zkclient.ZkClient.subscribeStateChanges(IZkStateListener)
		org.I0Itec.zkclient.ZkClient.unsubscribeStateChanges(IZkStateListener)
		org.I0Itec.zkclient.ZkClient.unsubscribeAll()
	 */
	@Test
	public void test3() {
		
		zk.subscribeChildChanges("", new IZkChildListener() {

			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {

			}
		});

		zk.subscribeDataChanges("", new IZkDataListener() {

			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {

			}

			@Override
			public void handleDataDeleted(String dataPath) throws Exception {

			}
		});

		zk.subscribeStateChanges(new IZkStateListener() {

			@Override
			public void handleStateChanged(KeeperState state) throws Exception {
				
			}

			@Override
			public void handleNewSession() throws Exception {
				
			}

			@Override
			public void handleSessionEstablishmentError(Throwable error) throws Exception {
				
			}
		});
	}
}
