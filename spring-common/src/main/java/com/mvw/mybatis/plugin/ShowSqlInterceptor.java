package com.mvw.mybatis.plugin;

import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;

/**
 * 可选的拦截器类型
	Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
	ParameterHandler (getParameterObject, setParameters)
	ResultSetHandler (handleResultSets, handleOutputParameters)
	StatementHandler (prepare, parameterize, batch, update, query)
 * 
 * @author gaotingping
 *
 * 2016年8月18日 下午1:55:54
 */
@Intercepts({
		@Signature(type = StatementHandler.class, method = "query", args = { Statement.class, ResultHandler.class }),
		@Signature(type = StatementHandler.class, method = "update", args = { Statement.class }) })
public class ShowSqlInterceptor implements Interceptor {

	@Override // 业务逻辑
	public Object intercept(Invocation invocation) throws Throwable {
		Object result = null;
		System.out.println("");
		System.out.println("===========================================================");
		System.out.println(invocation);
		if (invocation.getTarget() instanceof StatementHandler) {
			StatementHandler smh = (StatementHandler) invocation.getTarget();
			System.out.println("sql\t:" + smh.getBoundSql().getSql());
			System.out.println("params\t:" + smh.getBoundSql().getParameterObject());
			long s = System.currentTimeMillis();
			result = invocation.proceed();
			System.out.println("cost time\t:" + (System.currentTimeMillis() - s)+" ms");
			System.out.println("===========================================================");
			System.out.println("");
		} else {
			result = invocation.proceed();
		}
		return result;
	}

	@Override // 将你插件放入到MyBatis的插件集合中去
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override // 设置插件属性
	public void setProperties(Properties properties) {

	}
}