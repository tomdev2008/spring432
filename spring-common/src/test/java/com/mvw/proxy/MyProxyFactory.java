package com.mvw.proxy;

/**
 * 我的动态代理
 * 
 * @author gaotingping
 *
 *         2016年11月14日 下午7:15:46
 */
public interface MyProxyFactory {
	public Object getInstance(Object target) throws InstantiationException, IllegalAccessException;
}
