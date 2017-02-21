package org.spring.data.utils.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;
import org.spring.data.utils.dal.DataSourceFactory;

/**
 * sql执行工具
 * 
 * @author gaotingping
 *
 *         2017年1月6日 下午5:09:16
 */
public class SqlExecutionTools {

	private static String dbKey = "mysql";

	// 执行sql
	public int executeUpdate(String sql, boolean needId, Object... params) {

		if (StringUtils.isEmpty(sql)) {
			return -1;
		}

		try {
			
			Connection connection = DataSourceFactory.newInstance().getConnection(dbKey);
			
			PreparedStatement pst = null;
			
			if (needId) {
				pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			} else {
				pst = connection.prepareStatement(sql);
			}
			
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					pst.setObject(i + 1, params[i]);
				}
			}
			
			int size = pst.executeUpdate();

			if (needId) {
				ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					return rs.getInt(1);
				} else {
					return -1;
				}
			}

			pst.close();
			connection.close();

			return size;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public void executeQuery(String sql, ResultSetCallBack<?> callBack, Object... params) {

		if (StringUtils.isEmpty(sql)) {
			return;
		}
		try {
			Connection connection = DataSourceFactory.newInstance().getConnection(dbKey);
			PreparedStatement pst = connection.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					pst.setObject(i + 1, params[i]);
				}
			}
			ResultSet r = pst.executeQuery();
			callBack.run(r);
			r.close();
			pst.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean executeSQL(String sql, Object... params) {

		if (StringUtils.isEmpty(sql)) {
			return false;
		}

		try {
			Connection connection = DataSourceFactory.newInstance().getConnection(dbKey);
			PreparedStatement pst = connection.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					pst.setObject(i + 1, params[i]);
				}
			}
			boolean r = pst.execute();
			pst.close();
			connection.close();

			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void setDbKey(String dbKey) {
		SqlExecutionTools.dbKey = dbKey;
	}
}
