package test_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NativeJdbc2 {
	
	private Connection con = null;
	
	@Before
	public void init(){
		
		String url = "jdbc:mysql://127.0.0.1:3306/gtp_stu?useUnicode=true&characterEncoding=utf-8";
		String username = "root";
		String password = "123456";

		// 获得连接
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void close(){
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void test1() {
		String sql="INSERT INTO c(NAME,tid) VALUES(?,?)";
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(sql);
			for(int i=0;i<10;i++){
				pst.setString(1,"课程_"+i);
				pst.setInt(2, i%5+1);
				pst.addBatch();
			}
			
			pst.executeBatch();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2() {
		String sql="INSERT INTO s(NAME) VALUES(?)";
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(sql);
			for(int i=0;i<50;i++){
				pst.setString(1,"学生_"+i);
				pst.addBatch();
			}
			
			pst.executeBatch();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test3() {
		String sql="INSERT INTO sc(sid,cid,score) VALUES(?,?,?)";
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(sql);
			for(int i=0;i<50;i++){
				pst.setInt(1, i);
				pst.setInt(2, i%15+1);
				pst.setInt(3, i%9*10);
				pst.addBatch();
			}
			
			pst.executeBatch();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
