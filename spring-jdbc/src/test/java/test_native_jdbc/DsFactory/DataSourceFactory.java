package test_native_jdbc.DsFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 数据源
 */
public class DataSourceFactory {

	private static DataSourceFactory factory=new DataSourceFactory();
	
	private DataSourceFactory() {
		super();
	}
	
	private static Map<String, DataSource> pool = new HashMap<String, DataSource>();

	public Connection getConnection(String name) throws SQLException {
		try {
			if (pool.containsKey(name)) {
				return pool.get(name).getConnection();
			} else {
				DataSource ds = getDataSource(name);
				pool.put(name, ds);
				return ds.getConnection();
			}
		} catch (Exception e) {
			throw new RuntimeException("数据源没有发现",e);
		}
	}

	private DataSource getDataSource(String name) {
		return new ComboPooledDataSource(name);
	}
	
	
	public static DataSourceFactory newInstance() {
		return factory;
	}
}
