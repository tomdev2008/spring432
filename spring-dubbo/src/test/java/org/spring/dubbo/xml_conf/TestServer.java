package org.spring.dubbo.xml_conf;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestServer {

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-provider.xml");
		context.start();
		System.out.println(context);
		System.out.println("服务启动完毕!");
		System.in.read();
	}
}
