package com.mvw.proxy.support;

import java.lang.reflect.Method;

import com.mvw.proxy.MyProxyFactory;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

/*不能代理final,static的方法*/
public class ProxyJavassist implements MethodHandler,MyProxyFactory {

	private Object delegate;

	public Object getInstance(Object target) throws InstantiationException, IllegalAccessException{
		this.delegate = target;
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setSuperclass(delegate.getClass());
		Class<?> proxyClass = proxyFactory.createClass();
		Object proxy = proxyClass.newInstance();
		((ProxyObject) proxy).setHandler(this);
		return proxy;
	}

	@Override
	public Object invoke(Object self, Method method, Method proceed, Object[] args) throws Throwable {
		//System.out.println("123456");
		//return method.invoke(delegate, args);//非public的不能访问
		return proceed.invoke(self, args);//可以访问public
	}
}
