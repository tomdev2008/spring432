//package com.mvw.interceptor;
//
////实现演示
//public class InterceptorImpl implements Interceptor {
//	
//	public Object intercept(Invocation invocation)throws Throwable {
//		System.out.println("Go Go Go!!!");
//		return invocation.proceed();
//	}
//	
//	public Object plugin(Object target) {
//		return TargetProxy.bind(target, this);
//	}
//}