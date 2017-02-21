package com.mvw.beanpostprocessor.factory_bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;

/**
 * 将接口代理对象
 */
public class AutoFactoryBean2 implements FactoryBean<Object>{

	private String ref=null;//目标对象
	
	private Class<?> c=null;
	
	@Override
	public Object getObject() throws Exception {
		return createObj();
	}

	private Object createObj() throws Exception{
		return newMapperProxy(c);
	}

	@Override
	public Class<?> getObjectType() {
		if(ref!=null && c==null){
			try {
				c = Class.forName(ref);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return c;
	}
	
	//这里创建代理对象
	@SuppressWarnings("unchecked")
	public <T> T newMapperProxy(Class<T> mapperInterface) {
		
		//接口本身的类，肯定是空的
		//Class<?>[] cif = c.getInterfaces();
		
		ClassLoader classLoader = mapperInterface.getClassLoader();
		Class<?>[] interfaces = new Class[] { mapperInterface };
		return (T) Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				
				//根据method做些处理吧？
				System.out.println("我负责代理有配置的接口，你能把 我怎么样?");
				
				return "ok";
			}
		});
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}
}
