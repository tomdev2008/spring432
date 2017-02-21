package net.paoding.rose.jade.statement;

import java.sql.SQLSyntaxErrorException;

import net.paoding.rose.jade.statement.expression.ExqlPattern;
import net.paoding.rose.jade.statement.expression.impl.ExqlContextImpl;
import net.paoding.rose.jade.statement.expression.impl.ExqlPatternImpl;

import org.springframework.jdbc.BadSqlGrammarException;

/**
 * 系统解析器，转换SQL语句中的表达式
 * <p>
 * 这也是jade中一个非常重要的特性"filter-chain"实现者<br/>
 * 对程序的后期扩展和功能增强非常重要(类似于责任链模式)
 *
 * @Company 北京医视时代科技发展有限公司
 * @Author: gaotingping
 * @Email:  gaotingping@cyberzone.cn
 * @date    2015年10月19日 上午11:07:45 
 *
 * @see     <相关类>
 * @version v1.0
 */
public class SystemInterpreter implements Interpreter {

	@Override
	public void interpret(StatementRuntime runtime) {
		// 转换语句中的表达式
		ExqlPattern pattern = ExqlPatternImpl.compile(runtime.getSQL());
		ExqlContextImpl context = new ExqlContextImpl(runtime.getSQL().length() + 32);

		try {
			pattern.execute(context, runtime.getParameters(), runtime.getMetaData().getDAOMetaData().getConstants());
			runtime.setArgs(context.getParams());
			runtime.setSQL(context.flushOut());
		} catch (Exception e) {
			String daoInfo = runtime.getMetaData().toString();
			throw new BadSqlGrammarException(daoInfo, runtime.getSQL(), new SQLSyntaxErrorException(daoInfo + " @SQL('" + runtime.getSQL() + "')", e));
		}

	}
	
}
