package com.mvw.proxy.test;

import org.junit.Test;

import com.mvw.proxy.MyProxyFactory;
import com.mvw.proxy.support.ProxyCgLib;
import com.mvw.proxy.support.ProxyJavassist;
import com.mvw.proxy.support.ProxyJdk;

/**
 * 再易用性和实际场景上，jdk自动的代理还是不错的
 * 
 * @author gaotingping
 *
 * 2016年11月15日 上午10:13:42
 */
public class Test1 {

	@Test
	public void test1() throws Exception {

		long s = System.currentTimeMillis();
		testJavassist();

		System.out.println("耗时=" + (System.currentTimeMillis() - s));

		s = System.currentTimeMillis();
		testJDK();
		System.out.println("耗时=" + (System.currentTimeMillis() - s));

		s = System.currentTimeMillis();
		testCglib();//JAVAASSIST提供者动态代理接口最慢，比JDK自带的还慢
		System.out.println("耗时=" + (System.currentTimeMillis() - s));
	}

	private void testCglib() throws InstantiationException, IllegalAccessException {
		Foo foo = new FooImpl();
		MyProxyFactory pf = new ProxyCgLib();
		foo = (Foo) pf.getInstance(foo);
		for (int i = 0; i < 10000; i++) {
			foo.bar2();
		}
	}

	private void testJDK() throws InstantiationException, IllegalAccessException {
		Foo foo = new FooImpl();
		MyProxyFactory pf = new ProxyJdk();
		foo = (Foo) pf.getInstance(foo);
		for (int i = 0; i < 10000; i++) {
			foo.bar2();
		}
	}

	private void testJavassist() throws InstantiationException, IllegalAccessException {
		Foo foo = new FooImpl();
		MyProxyFactory pf = new ProxyJavassist();
		foo = (Foo) pf.getInstance(foo);
		for (int i = 0; i < 10000; i++) {
			foo.bar2();
		}
	}
}
