package com.mvw.interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理，处理拦截逻辑
 */
public class TargetProxy implements InvocationHandler {
	
	private Object target;
	
	private Interceptor interceptor;
	
	private TargetProxy(Object target, Interceptor interceptor) {
		this.target = target;
		this.interceptor = interceptor;
	}
	
	public static Object bind(Object target, Interceptor interceptor) {
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), 
						   target.getClass().getInterfaces(),
						   new TargetProxy(target, interceptor));
	}
	
	public Object invoke(Object proxy, Method method,Object[] args) throws Throwable {
		 return interceptor.intercept(new Invocation(target,method,args));
	}
}
