package com.mvw.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class CheckUtil {

	@Test
	public void test1() {

		List<String> list = new ArrayList<>();

		list.add("activity/192.168.1.74/192.168.1.74");
		list.add("paycore/192.168.1.76/192.168.1.76");
		list.add("sellerAdmin/192.168.0.166/192.168.0.166");
		list.add("idgen/192.168.1.51/192.168.1.51");
		list.add("user/192.168.1.52/192.168.1.52");
		list.add("membercenter/192.168.1.130/192.168.1.130");
		list.add("ic/192.168.1.99/192.168.1.99");
		list.add("promotion/192.168.1.131/192.168.1.131");
		list.add("api/192.168.1.50/192.168.1.50");
		list.add("trademanager/192.168.1.70/192.168.1.70");
		list.add("lgcenter/192.168.1.135/192.168.1.135");
		list.add("voucher/192.168.1.132/192.168.1.132");
		list.add("tradecenter/192.168.1.98/192.168.1.98");
		list.add("resourcecenter/192.168.1.71/192.168.1.71");
		list.add("point/192.168.1.97/192.168.1.97");
		list.add("sellerAdmin/192.168.0.23/192.168.0.23");
		list.add("palace/192.168.100.3/192.168.100.3");
		list.add("sellerAdmin/192.168.0.149/192.168.0.149");
		list.add("msgcenter/192.168.1.65/192.168.1.65");
		list.add("itemmanager/192.168.1.68/192.168.1.68");
		list.add("commentcenter/192.168.1.69/192.168.1.69");
		list.add("snscenter/192.168.1.67/192.168.1.67");
		list.add("dubbo-monitor/192.168.0.175/192.168.0.175");
		list.add("solrsearch/192.168.1.144/192.168.1.144");
		list.add("solrdump/192.168.1.145/192.168.1.145");
		list.add("trademanager/192.168.100.101/192.168.100.101");
		list.add("palace/192.168.200.32/192.168.200.32");
		list.add("sellerAdmin/192.168.0.112/192.168.0.112");
		list.add("palace/192.168.1.114/192.168.1.114");
		list.add("palace/192.168.0.158/192.168.0.158");
		list.add("track/192.168.0.155/192.168.0.155");
		
		Collections.sort(list);
		
		for(String url:list){
			String[] tps = url.split("/");
			String app =tps[0];
			String ip = tps[1];
			toChechOk(app, ip);
		}

	}

	private void toChechOk(String app, String ip) {
		getCode("http://" + ip + ":8080/" + app + "/ok.jsp");
		getCode("http://" + ip + ":8080/" + app + "-web/ok.jsp");
	}

	private String getCode(String http) {
		try {
			return HttpUtil.getHtml(http);
		} catch (Exception e) {
		}
		return "-1";
	}
}
