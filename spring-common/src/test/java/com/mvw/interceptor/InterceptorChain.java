package com.mvw.interceptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 多个拦截器如何管理：
 * 
 * @author gaotingping
 *
 *         2016年11月16日 下午5:56:39
 */
public class InterceptorChain {

	private final List<Interceptor> interceptors = new ArrayList<Interceptor>();

	// 在所有的拦截器行注册目标对象
	public Object pluginAll(Object target) {
		for (Interceptor interceptor : interceptors) {
			target = interceptor.plugin(target);
		}
		return target;
	}

	public void addInterceptor(Interceptor interceptor) {
		interceptors.add(interceptor);
	}

	public List<Interceptor> getInterceptors() {
		return Collections.unmodifiableList(interceptors);
	}
}
