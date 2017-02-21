package com.mvw.interceptor;

/**
 * 拦截器接口
 */
public interface Interceptor {
	
	/**
	 * 业务逻辑
	 *
	 * @param invocation
	 * @return
	 * @throws Throwable
	 */
    public Object intercept(Invocation invocation)throws Throwable;
    
    /**
     * 绑定被拦截对象
     *
     * @param target
     * @return
     */
    public Object plugin(Object target);
    
}
