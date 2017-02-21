//package com.mvw.zookeeper;
//
//import java.util.List;
//
//import org.apache.zookeeper.CreateMode;
//import org.apache.zookeeper.ZooDefs.Ids;
//import org.apache.zookeeper.ZooKeeper;
//import org.apache.zookeeper.data.Stat;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * 封装zk客户端
// */
//@Service
//public class ZkService {
//	
//	@Autowired
//	private ZkFactory zkFactory;
//	
//	// 日志
//    private static Logger logger = LoggerFactory.getLogger(ZkService.class);
//
//	//节点是否存在
//	public void exists(String path, boolean watch){
//		ZooKeeper zk =null;
//		try {
//			zk= zkFactory.getZooKeeper();
//			zk.exists(path, watch);
//		}catch (Exception e) {
//			logger.error(e.getMessage(),e);
//		}finally{
//			if(zk!=null){
//				try {
//					zk.close();
//				} catch (InterruptedException e) {
//					logger.error(e.getMessage(),e);
//				}
//			}
//		}
//	}
//
//	//获取子节点
//	public List<String> getChildren(String path, boolean watch) {
//		ZooKeeper zk =null;
//		try {
//			zk= zkFactory.getZooKeeper();
//			if(zk.exists(path, false)!=null){
//				return zk.getChildren(path, watch);
//			}
//		}catch (Exception e) {
//			logger.error(e.getMessage(),e);
//		}finally{
//			if(zk!=null){
//				try {
//					zk.close();
//				} catch (InterruptedException e) {
//					logger.error(e.getMessage(),e);
//				}
//			}
//		}
//		return null;
//	}
//
//	//获取节点数据
//	public byte[] getData(String path, boolean watch, Stat stat) {
//		ZooKeeper zk =null;
//		try {
//			zk= zkFactory.getZooKeeper();
//			if(zk.exists(path, false)!=null){
//				return zk.getData(path, watch, stat);
//			}
//		}catch (Exception e) {
//			logger.error(e.getMessage(),e);
//		}finally{
//			if(zk!=null){
//				try {
//					zk.close();
//				} catch (InterruptedException e) {
//					logger.error(e.getMessage(),e);
//				}
//			}
//		}
//		return null;
//	}
//	
//	//创建节点
//	public void createNode(String path,String data){
//		ZooKeeper zk =null;
//		try {
//			zk= zkFactory.getZooKeeper();
//			if(zk.exists(path, false)!=null){
//				zk.create(path, data.getBytes("utf-8"), Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
//			}
//		}catch (Exception e) {
//			logger.error(e.getMessage(),e);
//		}finally{
//			if(zk!=null){
//				try {
//					zk.close();
//				} catch (InterruptedException e) {
//					logger.error(e.getMessage(),e);
//				}
//			}
//		}
//		
//	}
//}
