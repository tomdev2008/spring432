package com.mvw.mybatis.plugins;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;


@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class }) })
public class MappedStatementInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		 Object obj = invocation.getTarget();
		 System.out.println(obj);
		 return invocation.proceed();
	}

	@Override//将你插件放入到MyBatis的插件集合中去
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override//设置插件属性
	public void setProperties(Properties properties) {

	}
}
