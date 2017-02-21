package im2exdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

// 原生jdbc
public class ProJdbc {

	private String url = "jdbc:mysql://192.168.8.242:3306/gtp?useUnicode=true&characterEncoding=utf-8";

	private String username = "mdb";

	private String password = "123456";
	
	@Test
	public void test2() throws Exception{
		
		String sql="INSERT INTO t1(_createTime,_name,_content, _score,_status) VALUES(?,?,?,?,?)";
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, username, password);
	
		
		long sc = System.currentTimeMillis();
		
		for(int i=0;i<5000;i++){
		
			MyModel jb=new MyModel();
			jb.setName("name"+i);
			jb.setContent("content"+i);
			jb.setScore(i);
			jb.setTime("2015-09-18");
			jb.setStatus(1);
			test(con,sql,jb);
		}
		
		
		System.out.println("耗时="+(System.currentTimeMillis()-sc));
		con.close();
		
		//251763  20次/秒  这还是通一个连接所得的，应该是最真实的
	}

	public void test(Connection con,String sql,MyModel m) throws SQLException{
		PreparedStatement pst = con.prepareStatement(sql) ;
		pst.setString(1,m.getTime());
		pst.setString(2,m.getName());
		pst.setString(3,m.getContent());
		pst.setInt(4,m.getScore());
		pst.setInt(5,m.getStatus());
		pst.executeUpdate();
		pst.close();
	}

	@Test
	public void test1() throws Exception {
		String sql="select * from t1 limit 0,20";
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, username, password);
		PreparedStatement pstmt = con.prepareStatement(sql) ; 
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			System.out.println(rs.getString("_name"));
		}
		rs.close();
		pstmt.close();
		con.close();
		
		//		//事务
		//		con.setAutoCommit(false);
		//		con.rollback();
		//		con.commit();
		//		con.setAutoCommit(true);
	}
}
