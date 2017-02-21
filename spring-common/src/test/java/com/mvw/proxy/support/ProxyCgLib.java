package com.mvw.proxy.support;

import java.lang.reflect.Method;

import com.mvw.proxy.MyProxyFactory;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/*
 *  cglib proxy:对于用final修饰的无能为力
 */
public class ProxyCgLib implements MethodInterceptor,MyProxyFactory {

	private Object target;
	
	public Object getInstance(Object target) throws InstantiationException, IllegalAccessException{
		this.target=target;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(this.target.getClass());
		enhancer.setCallback(this);
		return enhancer.create();
	}

	// 实现业务逻辑拦截
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		//System.out.println("事物开始");
		Object result = proxy.invokeSuper(obj, args);
		//System.out.println("事物结束");
		return result;
	}
}
