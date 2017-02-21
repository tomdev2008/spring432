package org.spring.data.utils.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 * 数据库导出工具类：
 * 		在需要建库和初始化库时有效,你只需要
 * 		给我一个连接(连接到数据库(本地/远程))
 * 		就可以将当前数据库中所有表的建表语句脚本
 * 		和初始化数据脚本导出。
 * 		
 *      因为不同的数据库建表语句都差不多
 */
public class CopyDBUtils {
	// 和平台无关的换行符
	private final static String hh = System.getProperty("line.separator");
	
	//每张表提取记录数定义
	private static int MAX_LEN=10;
	
	//导出脚本保存位置
	private static String path = "E:/coptyDB.txt";

	/**
	 * 将当前连接下的数据库中的所有表导出
	 * @param con
	 * @throws Exception
	 */
	public static void dbMeta(Connection con) throws Exception {
		DatabaseMetaData db = con.getMetaData();
		/*
		 * 参数说明：
				catalog是指数据库名称,可以设置为null，如果设置为null则为当前连接的数据库
				schemaPattern也是指数据库的名称。可以设置为null，如果设置为null则为当前连接的数据库
				tableNamePattern获取哪一个表，如果设置为null,则获取catalog中的所有表
				types : 数据库中有很多的对象，view,table,temp_table,如果设置为null则所有的表和视图
		 */
		//获得指定类型的数据库对象
		ResultSet rs = db.getTables(null, null, null, new String[] {"TABLE"});
		while (rs.next()) {
			//获得表名
			String tableName = rs.getString("TABLE_NAME");
			// 将当前表导出 带部分数据
			tableMeta(con,tableName);
		}
		// 关闭连接
		con.close();
	}

	/**
	 * 将指定表的建表语句和部分数据导出
	 * @param con  连接
	 * @param tableName
	 * @throws Exception
	 */
	public static void tableMeta(Connection con,String tableName) throws Exception {
		//换行
		writerUtils(hh+hh);
		Statement st = con.createStatement();
		String sql = "select * from " + tableName;//查询所有这个性能特别慢
		ResultSet rs = st.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();//获得元数据对象
		int cols = rsmd.getColumnCount();//列数
		// 组织建表语句  一次for一个表
		writerUtils("CREATE TABLE  " + tableName + " (");
		String headers = "";// 表字段数组
		for (int i = 1; i <= cols; i++) {
			String columnName = rsmd.getColumnName(i);// 列的名称
			String columnType = rsmd.getColumnTypeName(i);// 类型
			int size = rsmd.getColumnDisplaySize(i);// 长度
			//注意：最后一个不要“,”
			if (i == cols) {
				//将表的字段名称记录下来 导出数据时使用
				headers = headers + columnName;
				//一次写一行
				writerUtils("\t" + columnName + " \t" + columnType + "(" + size + ")" + hh + " )");
			  
			}else{
				headers = headers + columnName + ",";
				writerUtils("\t" + columnName + " \t" + columnType + "(" + size + "),");
			}
		}

		// 每一张表提取指定的行数
		int len=0;
		while (rs.next()) {
			if (len > MAX_LEN) {
				break;
			}
			// 一次for循环得到一行数据
			StringBuilder sb = new StringBuilder("INSERT INTO " + tableName + "(" + headers + ") VALUES(");
			//循环得到一行数据
			for (int i = 1; i <= cols; i++) {
				//注意：最后一个不要“,”
				if (i == cols) {
					sb.append("'" + rs.getObject(i) + "'");
				} else {
					sb.append("'" + rs.getObject(i) + "',");
				}
			}
			sb.append(");");
			writerUtils(sb.toString());
			len++;
		}
		// 关闭连接
		writerUtils(hh+hh);
		con.close();
	}

	// 将指定的内容写到预先指定的文件中
	public static void writerUtils(String str) throws Exception {
		FileWriter out = new FileWriter(new File(path), true);// 续写
		BufferedWriter buf = new BufferedWriter(out);
		buf.write(str);// 写出内容
		buf.write(hh);// 换行
		buf.flush();// 刷新
		out.close();// 关闭资源
	}
}
