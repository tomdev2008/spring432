package com.mvw.interceptor.test;

import com.mvw.interceptor.Interceptor;
import com.mvw.interceptor.InterceptorChain;
import com.mvw.interceptor.Invocation;
import com.mvw.interceptor.TargetProxy;

/**
 * mybatis拦截器或插件机制原理演示
 * 
 * @author gaotingping
 *
 * 2016年11月17日 上午9:52:28
 */
public class Test1 {
	public static void main(String[] args) {

		//初始化拦截器链
		InterceptorChain ic = new InterceptorChain();

		//添加拦截器
		ic.addInterceptor(new Interceptor() {

			@Override
			public Object intercept(Invocation invocation) throws Throwable {
				System.out.println("Go1 Go Go!!!");
				return invocation.proceed();
			}

			@Override
			public Object plugin(Object target) {
				return TargetProxy.bind(target, this);
			}

		});

		ic.addInterceptor(new Interceptor() {
			@Override
			public Object intercept(Invocation invocation) throws Throwable {
				System.out.println("Go2 Go Go!!!");
				return invocation.proceed();
			}

			@Override
			public Object plugin(Object target) {
				return TargetProxy.bind(target, this);
			}
		});

		Target target = new TargetImpl();

		/**
		 * ic.pluginAll(target);//这样为啥不是代理对象？
		 * 在目标对象中明明有:
		 *     target = interceptor.plugin(target);
		 */
		//给目标对象注册拦截器
		target=(Target)ic.pluginAll(target);
		
		//执行目标对象
		target.execute();	
	}
}
