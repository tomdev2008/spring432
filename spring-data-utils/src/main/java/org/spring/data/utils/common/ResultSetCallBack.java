package org.spring.data.utils.common;

import java.sql.ResultSet;

/**
 * 查询回调
 * 
 * @author gaotingping
 *
 * @param <T>
 * 2017年1月6日 下午6:39:44
 */
public interface ResultSetCallBack<T> {

	public T run(ResultSet resultSet); 
	
}
