package org.spring.zookeeper.api;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

/**
 * API测试
 * 
 * 
	create
	    creates a node at a location in the tree
	delete
	    deletes a node
	exists
	    tests if a node exists at a location
	get data
	    reads the data from a node
	set data
	    writes data to a node
	get children
	    retrieves a list of children of a node
	sync
	    waits for data to be propagated

 * 
 * @author gaotingping
 *
 * 2016年8月12日 下午1:22:55
 */
public class TestAPI extends TestBaseZk{

	@Test
	public void test1() throws UnsupportedEncodingException, KeeperException, InterruptedException{
		String path="/hhh/B1";
		String result = zk.create(path,//必须已"/"开头,需要一级一级的创建，除头外不能包含"/"   java.lang.IllegalArgumentException: Path must not end with / character
				"abc".getBytes("utf-8"),//建议序列化对象存储byte[],一个节点默认最多允许1M数据
				Ids.OPEN_ACL_UNSAFE,//ACL策略
				CreateMode.PERSISTENT);//节点类型  持久 瞬时 是否带版本号
		System.out.println(result); //返回创建节点的路径(全路径)
	}
	
	
	@Test
	public void  test2() throws InterruptedException, KeeperException{
		String path="/hhh/B1";
		zk.delete(path, 
				-1);//版本是指定版本号，-1表示删除所有版本
	}
	
	@Test
	public void test3() throws Exception{
		showDir("/");
	}
	
	@Test
	public void test4() throws KeeperException, InterruptedException{
		String path="/hhh/B1";
		Stat stat=null;
		byte[] data = zk.getData(path, 
				false,//是否触发watch
				stat);
		System.out.println(new String(data));
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
	public void  test5() throws UnsupportedEncodingException, KeeperException, InterruptedException{
		String path="/hhh/B1";
		for(int i=0;i<10;i++){
			String result = zk.create(path+"/C"+i+"_",
					"abc".getBytes("utf-8"),
					Ids.OPEN_ACL_UNSAFE,
					CreateMode.PERSISTENT_SEQUENTIAL);
			System.out.println(result);
		}
	}
	
	
	/**
		 “羊群效应“就是指大量客户端收到同一事件的通知，但实际上只有很少一部分需要处理这一事件。在这种情况下，
		 只有一个客户端会成功地获取锁，但是维护过程及向所有客户端发送观察事件会产生峰值流量，
		 这会对ZooKeeper服务器造成压力。
	 */
	@Test
	public void test6(){
		
	}
}
