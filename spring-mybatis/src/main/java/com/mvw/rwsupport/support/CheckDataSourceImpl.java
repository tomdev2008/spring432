package com.mvw.rwsupport.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * 查询数据源是否正常，保证单线程
 * 
 * @author gaotingping
 *
 *         2016年11月24日 上午10:49:07
 */
public class CheckDataSourceImpl implements CheckDataSource {

	private final static String CHECKSQL = "select 1";

	public boolean isActive(DataSource ds) {

		Connection con = null;

		try {
			con = ds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(CHECKSQL);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				rs.getRow();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return false;
	}
}
