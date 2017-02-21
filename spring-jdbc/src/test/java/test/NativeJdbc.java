package test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

/**
 * 原生jdbc测试
 * 
 * 1.Statement、PreparedStatement和CallableStatement都是接口(interface)。
 * 2.Statement继承自Wrapper、PreparedStatement继承自Statement、
 * CallableStatement继承自PreparedStatement。 3. Statement接口提供了执行语句和获取结果的基本方法；
 * PreparedStatement接口添加了处理 IN 参数的方法； CallableStatement接口添加了处理 OUT 参数的方法。 4.
 * a.Statement: 普通的不带参的查询SQL；支持批量更新,批量删除; b.PreparedStatement:
 * 可变参数的SQL,编译一次,执行多次,效率高; 安全性好，有效防止Sql注入等问题; 支持批量更新,批量删除; c.CallableStatement:
 * 继承自PreparedStatement,支持带参数的SQL操作; 支持调用存储过程,提供了对输出和输入/输出参数(INOUT)的支持;
 */
public class NativeJdbc {

	public Connection getCon() throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://127.0.0.1:3306/gtp?useUnicode=true&characterEncoding=utf-8";
		String username = "root";
		String password = "123456";

		// 获得连接
		return DriverManager.getConnection(url, username, password);
	}

	@Test // 批量
	public void test1() throws Exception {

		Connection con = getCon();

		PreparedStatement ps = con.prepareStatement("");

		for (int i = 0; i < 10000; i++) {
			ps.setString(1, "abc" + i);
			ps.setInt(2, 1);
			ps.addBatch();// 添加到同一个批处理中
		}

		ps.executeBatch();// 执行批处理
	}

	@Test
	public void test11() throws Exception {

		Connection con = getCon();

		PreparedStatement ps = con.prepareStatement("");

		for (int i = 0; i < 1000000; i++) {

			ps.setString(1, "");
			ps.addBatch();

			// 每500条执行一次，避免内存不够的情况，可参考，Eclipse设置JVM的内存参数

			if (i > 0 && i % 500 == 0) {
				ps.executeBatch();
				// 如果不想出错后，完全没保留数据，则可以没执行一次提交一次，但得保证数据不会重复
				con.commit();
			}

		}
		ps.executeBatch();// 执行最后剩下不够500条的
		ps.close();

		con.commit();// 执行完后，手动提交事务
		con.setAutoCommit(true);// 在把自动提交打开
		con.close();
	}

	/**
	 * 事务: con.setAutoCommit(false);//设置为手动提交 con.rollback();//回滚
	 * con.commit();//提交 con.setAutoCommit(true);//用完了恢复状态
	 * 
	 * service事务： 将线程Connection放入ThreadLocal,让service 内的每个dao都用这一个Connection
	 * 这就相当于上述的单个dao中开启事务了,而且都免去了手动控制和创建与销毁管理，这真的是一个创举，但 是真的原理其实特别特别简单的
	 */
	@Test // 事务
	public void test2() {
		Connection con = null;

		try {

			String sql = "select * from t1 limit 0,20";

			// 获得连接
			con = getCon();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("_name"));
			}

			//
			con.commit();

			//
			rs.close();
			ps.close();

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
					con.setAutoCommit(true);
					con.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test // 流程
	public void test3() throws Exception {
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

	@Test // 返回主键
	public void test4() throws Exception {

		Connection con = getCon();
		PreparedStatement ps = con.prepareStatement("", Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
			System.out.println("数据主键：" + rs.getLong(1));
		}
	}

	@Test // 存储过程
	public void test5() throws Exception{
		// INOUT参数使用：
		Connection conn = getCon();
		CallableStatement cstmt = conn.prepareCall("{call revise_total(?)}");
		cstmt.setInt(1, 4);
		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
		cstmt.executeUpdate();
		cstmt.getInt(1);
	}

}
