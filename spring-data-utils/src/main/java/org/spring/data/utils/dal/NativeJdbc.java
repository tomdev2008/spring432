package org.spring.data.utils.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class NativeJdbc {

	@Test
	public void test4() {
		Connection con = null;

		try {

			String sql = "select * from t1 limit 0,20";
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/gtp?useUnicode=true&characterEncoding=utf-8";
			String username = "root";
			String password = "123456";

			// 获得连接
			con = DriverManager.getConnection(url, username, password);

			con.setAutoCommit(false);// 手动提交事务

			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("_name"));
			}

			//
			con.commit();// 提交

			//
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			try {
				con.rollback();// 回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.setAutoCommit(true);// 还原
					con.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test
	public void test3() {
		/**
		 * 事务: con.setAutoCommit(false);//设置为手动提交 con.rollback();//回滚
		 * con.commit();//提交 con.setAutoCommit(true);//用完了恢复状态
		 * 
		 * service事务： 将线程Connection放入ThreadLocal, 让service
		 * 内的每个dao都用这一个Connection 这就相当于上述的单个dao中开启事务了,而且都免去了手动
		 * 控制和创建与销毁管理，这真的是一个创举，但 是真的原理其实特别特别简单的
		 */
	}

	@Test // transaction
	public void test2() {
		Connection con = null;

		try {

			String sql = "select * from t1 limit 0,20";
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/gtp?useUnicode=true&characterEncoding=utf-8";
			String username = "root";
			String password = "123456";

			// con
			con = DriverManager.getConnection(url, username, password);
			con.setAutoCommit(false);

			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("_name"));
			}

			//
			con.commit();

			//
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.setAutoCommit(false);
					con.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test
	public void test1() throws Exception {
		String sql = "select * from t1 limit 0,20";
		Class.forName("com.mysql.jdbc.Driver");
		String url = null;
		String username = null;
		String password = null;
		Connection con = DriverManager.getConnection(url, username, password);
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString("_name"));
		}
		rs.close();
		pstmt.close();
		con.close();
	}
}
