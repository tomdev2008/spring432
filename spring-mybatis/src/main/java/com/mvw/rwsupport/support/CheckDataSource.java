package com.mvw.rwsupport.support;

import javax.sql.DataSource;

/**
 * 查询数据源是否正常，保证单线程
 * 
 * @author gaotingping
 *
 * 2016年11月24日 上午10:49:07
 */
public interface CheckDataSource {

	public boolean isActive(DataSource ds);
}
