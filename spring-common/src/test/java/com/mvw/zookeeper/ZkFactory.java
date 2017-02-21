//package com.mvw.zookeeper;
//
//import java.io.IOException;
//
//import org.apache.zookeeper.CreateMode;
//import org.apache.zookeeper.Watcher;
//import org.apache.zookeeper.ZooDefs.Ids;
//import org.apache.zookeeper.ZooKeeper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.FactoryBean;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * https client 工厂类
// * 
// */
//public class ZkFactory implements FactoryBean<ZooKeeper>{
//	
//	private static Logger logger = LoggerFactory.getLogger(ZkFactory.class);
//	
//	private ZooKeeper zooKeeper=null;
//	
//	/**
//	 * 默认watcher
//	 */
//	private Watcher watcher=null;
//	
//	@Autowired
//	private MyWatcher abstractWatcher=null;
//	
//	/**
//	 * 连接字符串 ip:port,...,ip:port
//	 */
//	private String conString=null;
//	
//	/**
//	 * 连接超时时间(单位:毫秒)
//	 */
//	private int sTimeout=-1;
//	
//	public ZkFactory(){
//		
//	}
//	
//	//初始化 
//	public void init() throws Exception{
//		if(sTimeout<0){
//			logger.error("sTimeout must be greater than zero,unit:milliseconds");
//			throw new RuntimeException("sTimeout must be greater than zero,unit:milliseconds");
//		}else if(conString==null || "".equals(conString)){
//			logger.error("conString can't be empty,model:ip:port,...,ip:port");
//			throw new RuntimeException("conString can't be empty,model:ip:port,...,ip:port");
//		}else if(watcher==null){
//			watcher=abstractWatcher;
//			logger.warn("watcher is empty,Set to the default:com.chaoxing.zk.utils.AbstractWatcher");
//		}
//		zooKeeper = new ZooKeeper(conString, sTimeout,watcher);
//		logger.info("ZooKeeper initialize the success:"+conString+","+sTimeout+","+watcher);
//		initNode();
//	}
//	
//	private void initNode() {
//		try {
//			if(zooKeeper.exists("/web", true)==null){
//				zooKeeper.create("/web",
//						"".getBytes("utf-8"),
//						Ids.OPEN_ACL_UNSAFE,
//						CreateMode.PERSISTENT);
//			}else{
//				logger.info(zooKeeper.getChildren("/web", true).toString());
//			}
//		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
//		}
//	}
//
//	//销毁
//	public void destory(){
//		if(watcher!=null){
//			watcher=null;
//		}
//		if(zooKeeper!=null){
//			try {
//				zooKeeper.close();
//			} catch (InterruptedException e) {
//				logger.error(e.getMessage(),e);
//			}
//			logger.info("ZooKeeper close");
//		}
//	}
//	
//	//获得对象
//	public ZooKeeper getObject() throws Exception {
//		return zooKeeper;
//	}
//
//	//class类型
//	public Class<?> getObjectType() {
//		return ZooKeeper.class;
//	}
//
//	//是否单例
//	public boolean isSingleton() {
//		return true;
//	}
//
//	//setter getter
//	public Watcher getWatcher() {
//		return watcher;
//	}
//
//	public void setWatcher(Watcher watcher) {
//		this.watcher = watcher;
//	}
//
//	public String getConString() {
//		return conString;
//	}
//
//	public void setConString(String conString) {
//		this.conString = conString;
//	}
//
//	public int getsTimeout() {
//		return sTimeout;
//	}
//
//	public void setsTimeout(int sTimeout) {
//		this.sTimeout = sTimeout;
//	}
//
//	public ZooKeeper getZooKeeper() {
//		try {
//			return new ZooKeeper(conString, sTimeout,watcher);
//		} catch (IOException e) {
//			logger.error(e.getMessage(),e);
//			return null;
//		}
//	}
//
//
//}
