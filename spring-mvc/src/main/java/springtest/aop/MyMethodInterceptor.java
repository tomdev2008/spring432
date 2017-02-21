package springtest.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


/**
 * 切面:可以记录操作日志或监控运行等需求
 */
public class MyMethodInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		long s = System.currentTimeMillis();
		Object result = invocation.proceed();
		Method m = invocation.getMethod();
		System.out.println(m.getName()+"耗时=" + (System.currentTimeMillis() - s));
		return result;
	}
}
