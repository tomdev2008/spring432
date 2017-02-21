package org.spring.data.utils.common;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.spring.data.utils.dal.DataSourceFactory;

/**
 *    元数据分析 看数据库的结构
 *    用途：
 *      1) 在远程分析数据库的结构
 *      2) 数据库结构的导出/复制  组成建表语句和插入数据语句将其写入文件中
 */
public class dbMetaUtils {
    public static void main(String[] args) throws Exception {
    	dbMeta();
	}
    
    /**
     * 获取当前连接数据库的所有表的名称
     */
    public static void dbMeta() throws Exception{
    	        //1:获取连接
    			Connection con = DataSourceFactory.newInstance().getConnection("sqlserver");
    			//2:获取数据库的元数据
    			DatabaseMetaData db = con.getMetaData();
    			//3:获取数据的一些信息
    			//catalog是指数据库名称,可以设置为null，如果设置为null则为当前连接的数据库
    			//schemaPattern也是指数据库的名称。可以设置为null，如果设置为null则为当前连接的数据库
    			//tableNamePattern获取哪一个表，如果设置为null,则获取catalog中的所有表
    			//types : 数据库中有很多的对象，view,table,temp_table,如果设置为null则所有的表和视图
    			ResultSet rs = db.getTables(null,null,null,new String[]{"TABLE"});
    			//4:遍历只获取表名
    			while(rs.next()){
    				String tableName = rs.getString("TABLE_NAME");
    				System.err.println("表名为："+tableName);
    				System.out.println("*************************************");
    				System.out.println("表的数据结构");
    				tableMeta(tableName);
    				System.out.println("*************************************");
    			}
    			//关闭连接
    			con.close();
    }
    /**
     * 获取指定表的数据结构
     * @param tableName
     * @throws Exception 
     */
    public static void tableMeta(String tableName) throws Exception{
		Connection con = DataSourceFactory.newInstance().getConnection("sqlserver");
		Statement st = con.createStatement();
		String sql = "select * from "+tableName;
		ResultSet rs = st.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();//获得元数据对象
		int cols = rsmd.getColumnCount();//获得此表的列的名称
		//获取表的数据结构 
		for(int i=1;i<=cols;i++){
			String columnName = rsmd.getColumnName(i);//列的名称
			String columnType = rsmd.getColumnTypeName(i);//类型
			int size = rsmd.getColumnDisplaySize(i);//长度
			System.err.print(columnName+"["+columnType+"("+size+")]"+"\t");
			//在这里可以组成建表语句
		}
		System.err.println();
		//获取表的数据
		System.out.print("表的数据：\n");
		while(rs.next()){
			//由于不知道叫什么字段所有再去遍历字段
			for(int i=1;i<=cols;i++){
				System.err.print(rs.getObject(i)+"\t");
				//这里可以组成插入数据语句
			}
			System.err.println();
		}
		con.close();
    }
}
