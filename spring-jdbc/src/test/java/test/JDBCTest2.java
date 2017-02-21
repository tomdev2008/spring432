package test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * JdbcTemplate
 * 
 * @author mail:gaotingping@cyberzone.cn
 * @date 2015年5月13日
 */
@Repository
public class JDBCTest2 {
	
	@Autowired
	private JdbcTemplate t=null;
	
	public void test1(){
		System.out.println(t);
	}
	
	public void test2() throws Exception{
		/*
			JdbcTemplate主要方法：
		    execute方法：可以用于执行任何SQL语句，一般用于执行DDL语句；
		    update方法及batchUpdate方法：update方法用于执行新增、修改、删除等语句；batchUpdate方法用于执行批处理相关语句；
		    query方法及queryForXXX方法：用于执行查询相关语句；
		    call方法：用于执行存储过程、函数相关语句
		    
		              最常用的就是update以及query
		 */
		
		/*
			//update 
			update(PreparedStatementCreator, PreparedStatementSetter)
			update(PreparedStatementCreator)
			update(PreparedStatementCreator, KeyHolder)
			update(String, PreparedStatementSetter)
			update(String, Object[], int[])
			update(String, Object...)
		*/
//		t.query("", new String[1], new RowMapper<Person>(){
//			public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
//				return null;
//			}
//		});
		t.update("", new Object[1]);
	}

	/*
	大体分为以下几个类别：
    	execute方法：可以用于执行任何SQL语句，一般用于执行DDL语句；
    	update方法及batchUpdate方法：update方法用于执行新增、修改、删除等语句；batchUpdate方法用于执行批处理相关语句；
    	query方法及queryForXXX方法：用于执行查询相关语句；
    	call方法：用于执行存储过程、函数相关语句。

              充分体现了“回调”思想

		JdbcTemplate()
		JdbcTemplate(DataSource)
		JdbcTemplate(DataSource, boolean)
		
		setNativeJdbcExtractor(NativeJdbcExtractor)
		getNativeJdbcExtractor()
		setIgnoreWarnings(boolean)
		isIgnoreWarnings()
		setFetchSize(int)
		getFetchSize()
		setMaxRows(int)
		getMaxRows()
		setQueryTimeout(int)
		getQueryTimeout()
		setSkipResultsProcessing(boolean)
		isSkipResultsProcessing()
		setSkipUndeclaredResults(boolean)
		isSkipUndeclaredResults()
		setResultsMapCaseInsensitive(boolean)
		isResultsMapCaseInsensitive()
		execute(ConnectionCallback<T>)
		createConnectionProxy(Connection)
		execute(StatementCallback<T>)
		execute(String)
		query(String, ResultSetExtractor<T>)
		query(String, RowCallbackHandler)
		query(String, RowMapper<T>)
		queryForMap(String)
		queryForObject(String, RowMapper<T>)
		queryForObject(String, Class<T>)
		queryForLong(String)
		queryForInt(String)
		queryForList(String, Class<T>)
		queryForList(String)
		queryForRowSet(String)
		update(String)
		batchUpdate(String[])
		execute(PreparedStatementCreator, PreparedStatementCallback<T>)
		execute(String, PreparedStatementCallback<T>)
		query(PreparedStatementCreator, PreparedStatementSetter, ResultSetExtractor<T>)
		query(PreparedStatementCreator, ResultSetExtractor<T>)
		query(String, PreparedStatementSetter, ResultSetExtractor<T>)
		query(String, Object[], int[], ResultSetExtractor<T>)
		query(String, Object[], ResultSetExtractor<T>)
		query(String, ResultSetExtractor<T>, Object...)
		query(PreparedStatementCreator, RowCallbackHandler)
		query(String, PreparedStatementSetter, RowCallbackHandler)
		query(String, Object[], int[], RowCallbackHandler)
		query(String, Object[], RowCallbackHandler)
		query(String, RowCallbackHandler, Object...)
		query(PreparedStatementCreator, RowMapper<T>)
		query(String, PreparedStatementSetter, RowMapper<T>)
		query(String, Object[], int[], RowMapper<T>)
		query(String, Object[], RowMapper<T>)
		query(String, RowMapper<T>, Object...)
		queryForObject(String, Object[], int[], RowMapper<T>)
		queryForObject(String, Object[], RowMapper<T>)
		queryForObject(String, RowMapper<T>, Object...)
		queryForObject(String, Object[], int[], Class<T>)
		queryForObject(String, Object[], Class<T>)
		queryForObject(String, Class<T>, Object...)
		queryForMap(String, Object[], int[])
		queryForMap(String, Object...)
		queryForLong(String, Object[], int[])
		queryForLong(String, Object...)
		queryForInt(String, Object[], int[])
		queryForInt(String, Object...)
		queryForList(String, Object[], int[], Class<T>)
		queryForList(String, Object[], Class<T>)
		queryForList(String, Class<T>, Object...)
		queryForList(String, Object[], int[])
		queryForList(String, Object...)
		queryForRowSet(String, Object[], int[])
		queryForRowSet(String, Object...)
		update(PreparedStatementCreator, PreparedStatementSetter)
		update(PreparedStatementCreator)
		update(PreparedStatementCreator, KeyHolder)
		update(String, PreparedStatementSetter)
		update(String, Object[], int[])
		update(String, Object...)
		batchUpdate(String, BatchPreparedStatementSetter)
		batchUpdate(String, List<Object[]>)
		batchUpdate(String, List<Object[]>, int[])
		batchUpdate(String, Collection<T>, int, ParameterizedPreparedStatementSetter<T>)
		execute(CallableStatementCreator, CallableStatementCallback<T>)
		execute(String, CallableStatementCallback<T>)
		call(CallableStatementCreator, List<SqlParameter>)
		extractReturnedResults(CallableStatement, List<SqlParameter>, List<SqlParameter>, int)
		extractOutputParameters(CallableStatement, List<SqlParameter>)
		processResultSet(ResultSet, ResultSetSupportingSqlParameter)
		getColumnMapRowMapper()
		getSingleColumnRowMapper(Class<T>)
		createResultsMap()
		applyStatementSettings(Statement)
		newArgPreparedStatementSetter(Object[])
		newArgTypePreparedStatementSetter(Object[], int[])
		handleWarnings(Statement)
		handleWarnings(SQLWarning)
		getSql(Object)
	 */
}
