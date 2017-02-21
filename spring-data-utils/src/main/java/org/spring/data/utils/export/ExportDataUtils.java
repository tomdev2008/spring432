package org.spring.data.utils.export;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.spring.data.utils.dal.DataSourceFactory;

/**
 * 数据导出工具
 * 
 */
public class ExportDataUtils {

	public static void main(String[] args) {
		try {
			ExportDataUtils imp = new ExportDataUtils("MemberFrom", 
					"id,createDate,_name,_domain,allowJoin,cityid,firstLetter",
					"cityid=3", 
					"E:/myWeekSpace/poi/test4.xls");
			imp.impData(imp.buidSQL());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String table = null;/*表*/
	private String queryField = null;/*导出那些字段*/
	private String filter = null;/*过滤条件*/
	private String path = null;/*文件存储路径*/

	/**
	 * 
	 * @param table	    	表
	 * @param queryField 	要查询的字段
	 * @param filter		过滤条件
	 * @param path 			保存路径
	 */
	public ExportDataUtils(String table, String queryField, 
			String filter,String path) {
		this.table = table;
		this.queryField = queryField;
		this.filter = filter;
		this.path = path;
	}

	/**
	 * 创建sql
	 * 
	 * @return
	 */
	private String buidSQL() {
		StringBuilder sql = new StringBuilder();
		sql.append("select");
		sql.append(" " + queryField);
		sql.append(" from " + table);
		if (filter != null) {
			sql.append(" where " + filter);
		}
		return sql.toString();
	}

	/**
	 * 根据sql语句导出数据
	 * 
	 * @param sql
	 * @throws Exception
	 */
	private void impData(String sql) throws Exception {
		System.out.println("导出语句="+sql);
		Connection connection = DataSourceFactory.newInstance().getConnection("sqlserver");
		PreparedStatement pst = connection.prepareStatement(sql);
		//pst.getGeneratedKeys().getLong(1);
		ResultSet rs = pst.executeQuery();
		toExcel(rs);
		rs.close();
		pst.close();
		connection.close();
	}

	/**
	 * 导出成excel
	 * 
	 * @param rs
	 * @throws Exception
	 */
	private void toExcel(ResultSet rs) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(table);
		String[] temp = queryField.split(",");
		writerHeader(sheet, temp);
		int index = 1;
		while (rs.next()) {
			HSSFRow row = sheet.createRow(index++);
			for (int i = 0; i < temp.length; i++) {
				HSSFCell cell = row.createCell(i);
				sheet.autoSizeColumn(i);
				cell.setCellValue(rs.getString(temp[i]));
			}
		}
		wb.write(new FileOutputStream(path));
		wb.close();
	}

	/**
	 * 写出头部
	 * 
	 * @param sheet
	 * @param temp
	 */
	private void writerHeader(HSSFSheet sheet, String[] temp) {
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < temp.length; i++) {
			HSSFCell cell = row.createCell(i);
			sheet.autoSizeColumn(i);
			cell.setCellValue(temp[i]);
		}
	}
}
