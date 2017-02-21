package org.spring.zookeeper.api;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;

/**
 * zk公共测试类
 * 
 * @author gaotingping
 *
 *         2016年8月12日 下午1:23:53
 */
public class TestBaseZk {

	protected ZooKeeper zk = null;

	@Before
	public void init() throws Exception {
		zk = new ZooKeeper("127.0.0.1:2181", 3000, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				System.out.println(event);
			}
		});
	}

	@After
	public void close() throws Exception {
		if (zk != null) {
			zk.close();
		}
	}
}
