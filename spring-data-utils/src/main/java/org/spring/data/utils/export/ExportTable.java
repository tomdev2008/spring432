package org.spring.data.utils.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 导出数据表的结构或数据 支持条件
 */
public class ExportTable {

	// 构造
	private Connection con;

	// 导出脚本保存位置
	private final String PATH = "E:/coptyDB.txt";

	// 和平台无关的换行符
	private final String hh = System.getProperty("line.separator");

	// 输出流
	private BufferedWriter buf = null;

	ExportTable(Connection con) {
		if (con == null) {
			throw new RuntimeException("params con is null!");
		}
		this.con = con;
		try {
			buf = new BufferedWriter(new FileWriter(new File(PATH), true));
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 导出表结构
	 * 
	 * @param tables  tables<表名称，null>  指定要导出的表，当此参数为空时导出当前连接下的所有表结构
	 * @throws Exception
	 */
	public void exportTableStructure(Map<String, String> tables) {
		try {
			if (tables == null || tables.size() < 1) {
				exportTableStructureAll();
			} else {
				exportTableStructureByName(tables);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new RuntimeException(e.getMessage());
				}
			}
			if (buf != null) {
				try {
					buf.close();
				} catch (IOException e) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}
	}

	// 导出所有表
	private void exportTableStructureAll() throws Exception {
		DatabaseMetaData db = con.getMetaData();
		ResultSet rs = db.getTables(null, null, null, new String[] { "TABLE" });
		while (rs.next()) {
			exportTableStructure(rs.getString("TABLE_NAME"));
		}
		con.close();
		buf.flush();
		buf.close();
	}

	// 导出指定名称的表
	private void exportTableStructureByName(Map<String, String> tables)
			throws Exception {
		DatabaseMetaData db = con.getMetaData();
		ResultSet rs = db.getTables(null, null, null, new String[] { "TABLE" });
		while (rs.next()) {
			String tableName = rs.getString("TABLE_NAME");
			if (tables.containsKey(tableName)) {
				exportTableStructure(tableName);
			}
		}
		con.close();
		buf.flush();
		buf.close();
	}

	// 导出具体表
	private void exportTableStructure(String tableName) throws Exception {
		try{
		// 换行
		writerUtils(hh + hh);
		Statement st = con.createStatement();
		String sql = "select * from " + tableName;// 查询所有这个性能特别慢
		ResultSet rs = st.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();// 获得元数据对象
		int cols = rsmd.getColumnCount();// 列数
		// 组织建表语句 一次for一个表
		writerUtils("CREATE TABLE  " + tableName + " (");
		for (int i = 1; i <= cols; i++) {
			String columnName = rsmd.getColumnName(i);// 列的名称
			String columnType = rsmd.getColumnTypeName(i);// 类型
			int size = rsmd.getColumnDisplaySize(i);// 长度
			// 注意：最后一个不要“,”
			if (i == cols) {
				// 一次写一行
				writerUtils("\t" + columnName + " \t" + columnType + "(" + size
						+ ")" + hh + " )");
			} else {
				writerUtils("\t" + columnName + " \t" + columnType + "(" + size
						+ "),");
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出表中数据
	 * 
	 * @param params   Map<表名称,过滤参数>
	 *                 按照指定条件导出指定表的数据
	 *                 当此参数为空导出所有表的所有数据
	 *                 例子：
	 *                    params.put("table","where name='张三'")
	 */
	public void exportTableData(Map<String, String> params) {
		try {
			if (params == null || params.size() < 1) {
				exportTableDataAll();
			} else {
				exportTableDataAllByName(params);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new RuntimeException(e.getMessage());
				}
			}
			if (buf != null) {
				try {
					buf.close();
				} catch (IOException e) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}
	}

	// 导出所有数据
	private void exportTableDataAll() throws Exception {
		DatabaseMetaData db = con.getMetaData();
		ResultSet rs = db.getTables(null, null, null, new String[] { "TABLE" });
		while (rs.next()) {
			exportTableData(rs.getString("TABLE_NAME"), null);
		}
		con.close();
		buf.flush();
		buf.close();
	}

	// 导出指定的表指定条件数据
	private void exportTableDataAllByName(Map<String, String> params)
			throws Exception {
		Set<String> set = params.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = params.get(key);
			exportTableData(key, value);
		}
		con.close();
		buf.flush();
		buf.close();
	}

	// 导出指定的表的数据
	private void exportTableData(String table, String params) throws Exception {
		// 换行
		writerUtils(hh + hh);
		Statement st = con.createStatement();
		String sql = null;
		if (params != null && !"".equals(params)) {
			sql = "select * from " + table + " " + params;
		} else {
			sql = "select * from " + table;
		}
		ResultSet rs = st.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();
		StringBuilder headers = new StringBuilder();
		for (int i = 1; i <= cols; i++) {// 得到所有列名称
			if (headers.length() == 0) {
				headers.append(rsmd.getColumnName(i));
			} else {
				headers.append("," + rsmd.getColumnName(i));
			}
		}
		while (rs.next()) {// 所有数据
			StringBuilder sb = new StringBuilder("INSERT INTO " + table + "("
					+ headers.toString() + ") VALUES(");
			for (int i = 1; i <= cols; i++) {
				if (i == cols) {
					sb.append("'" + rs.getObject(i) + "'");
				} else {
					sb.append("'" + rs.getObject(i) + "',");
				}
			}
			sb.append(");");
			writerUtils(sb.toString());
		}
		writerUtils(hh + hh);
	}

	// 将指定的内容写到预先指定的文件中
	private void writerUtils(String str) throws Exception {
		buf.write(str);
		buf.write(hh);
		buf.flush();
	}
}
