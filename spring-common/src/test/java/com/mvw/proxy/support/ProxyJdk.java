package com.mvw.proxy.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.mvw.proxy.MyProxyFactory;

public class ProxyJdk implements InvocationHandler, MyProxyFactory {

	private Object target;

	public Object getInstance(Object target) throws InstantiationException, IllegalAccessException {
		this.target = target;
		//这样写适合代理有接口的类
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}

	/**
	 * 注意这里的区别
	 * 
	 * Object proxy	            实现类对象，被代理类，proxy使用不当会死循环
	 * Method method	方法
	 * Object[] args	参数
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("代理方法-1");
		Object result = method.invoke(target, args);
		System.out.println("代理方法-2");
		return result;
	}
}
