package com.mvw.tair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.taobao.tair.impl.DefaultTairManager;

/**
 * http://code.taobao.org/p/tair/wiki/guide/
 * 
 * @author gaotingping
 *
 *         2016年9月6日 上午10:55:14
 */
public class TestTair {

	private DefaultTairManager tair = null;

	@Before
	public void init() {
		try {

			tair = new DefaultTairManager();

			// 服务器列表
			List<String> configServerList = new ArrayList<>();
			configServerList.add("192.168.1.55:5198");
			tair.setConfigServerList(configServerList);

			tair.setCharset("UTF-8");// 编码

			tair.setGroupName("test");// 组名称

			tair.setTimeout(3000);// 请求超时时间

			tair.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void close() {
		if (tair != null) {
			tair.close();
		}
	}

	@Test
	public void test1() {
		Map<String, String> result = tair.getStat(1, "group_1", 1);
		System.out.println(result);
	}
}
