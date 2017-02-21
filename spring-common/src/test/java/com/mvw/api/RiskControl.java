package com.mvw.api;

import org.junit.Test;

/*
 * 代码演示： security check
 * 
 * 	每个action对应一个code
 *  每个code需要校验多个rule{后台配置的规则，缓存到本地以提高效率，有修改时通知更新}
 *  每个rule可能触发一个evnt{uid,code,rule}
 */
public class RiskControl {

	@Test
	public void test1() {
		rc(123, "loginError");
	}

	// 风控:必须快速
	private boolean rc(int uid, String code) {
		if (!blacklist(uid)) {
			boolean tmp = checkRule(uid, code);
			if (!tmp) {
				handleEvent(uid, code);
			}
			return tmp;
		}
		return false;
	}

	// 规则校验:O(n)
	private boolean checkRule(int uid, String code) {
		return true;
	}

	// 事件处理:O(n)
	private void handleEvent(int uid, String code) {

		switch (code) {
		case "123": //重事件，不能动态添加
			System.out.println("直接异步处理掉");
			break;
		/*下面这两类事件，可以随着获得动态更新和添加，就一个计数器功能*/
		case "456": //轻事件，支持动态添加
			System.out.println("异步发消息");
			break;
		default://忽略事件，无需处理，支持动态添加
			System.out.println("直接返回给业务");
			break;
		}
	}

	// 验证用户是否在黑名单: O(1)
	private boolean blacklist(int uid) {
		return true;
	}

}
