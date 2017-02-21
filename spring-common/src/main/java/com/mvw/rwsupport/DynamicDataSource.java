package com.mvw.rwsupport;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 自定义数据源切换
 */
public class DynamicDataSource extends AbstractRoutingDataSource{

	@Override //可以在这里扩展，支持多数据源(外加负载均衡策略)
	protected Object determineCurrentLookupKey() {
		  System.out.println("获取数据源");
		  return DBContextHolder.getDbType();  
	}

}
