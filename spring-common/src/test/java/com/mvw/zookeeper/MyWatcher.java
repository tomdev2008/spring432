package com.mvw.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.springframework.stereotype.Service;

/**
 * 监听
 * 
 * @author gaotingping
 *
 * 2016年8月12日 上午11:03:11
 */
@Service
public class MyWatcher implements Watcher{

	//监控
	public void process(WatchedEvent event) {
		System.out.println(event);
	}
}
