package org.spring.data.utils.importtest;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.spring.data.utils.dal.DataSourceFactory;

public class ImportDataUtils {
	public static void main(String[] args) throws Exception {
		String fpath="E:/myWeekSpace/passport2/fid.xls";//指定路径
		String tpath="E:/myWeekSpace/passport2/fid_gtp.xls";
		
		int indexs[]={0};//指定读取的索引，从0开始
		
		String updateSql="update ss_user set ssusername=?,fid=? where ssusername=?";//指定修改语句，需要和indexs一致，并且？和总个数和indexs的个数一致
		
		//读取excel
		List<String[]> list=redExcel(fpath,indexs);
		
		//查询
		selectTable(list,"select ssuserid,ssusername,fid from ss_user where ssusername=?",tpath);
		
		//修改数据库
		updateTable(list,updateSql);
		
//		//批量修改
//		batchUpdateTable(list,updateSql);
	}

	//批量导出
	private static void selectTable(List<String[]> list, String sql,String path) {
		try{
			Connection connection = DataSourceFactory.newInstance().getConnection("sqlserver");
			PreparedStatement pst = connection.prepareStatement(sql);
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("sheet1");
			int index=-1;
			for(String[] ar:list){
				index++;
				for(String s:ar){
					pst.setString(1, "145#"+s);
					ResultSet rs = pst.executeQuery();
					toRow(index,sheet,rs);
				}
			}
			wb.write(new FileOutputStream(path));
			pst.close();
			connection.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//导出一行
	private static void toRow(int index,HSSFSheet sheet, ResultSet rs) throws SQLException {
		while(rs.next()){
		HSSFRow row = sheet.createRow(index);
		//ssuserid,ssusername,fid
		HSSFCell cell = row.createCell(0);
		sheet.autoSizeColumn(0);
		cell.setCellValue(rs.getString("ssuserid"));
		
		HSSFCell cell1 = row.createCell(1);
		sheet.autoSizeColumn(0);
		cell1.setCellValue(rs.getString("ssusername"));
		
		HSSFCell cell2 = row.createCell(2);
		sheet.autoSizeColumn(0);
		cell2.setCellValue(rs.getString("fid"));
		}
	}

	/**
	 * 批量修改数据
	 * 
	 * @param list
	 * @param sql
	 * @throws SQLException
	 */
	private static void updateTable(List<String[]> list,String sql) throws SQLException {
		long start = System.currentTimeMillis();
		Connection connection = DataSourceFactory.newInstance().getConnection("sqlserver");
		System.out.println("开始更新数据库的数据");
		System.out.println("执行sql="+sql);
		PreparedStatement pst = connection.prepareStatement(sql);
		for(String[] r:list){
			pst.setString(1, "413#"+r[0]);
			pst.setString(2, "413");
			pst.setString(3, "145"+r[0]);
			pst.executeUpdate();
		}
		System.out.println("执行结束！");
		long end=System.currentTimeMillis();
		System.out.println("耗时="+(end-start));
		connection.close();
	}

	/**
	 * 读取文件
	 * 
	 * @param path
	 * @param index
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static List<String[]> redExcel(String path,int index[]) throws FileNotFoundException, IOException {
		List<String[]> list=new ArrayList<String[]>();
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(path));
		HSSFSheet sheet = workbook.getSheetAt(0);
		int rows=sheet.getLastRowNum();
		for(int i = 1;i<=rows;i++){
			HSSFRow row = sheet.getRow(i);
			list.add(redRow(row,index));
		}
		return list;
	}
	
	/**
	 * 根据索引读取一行
	 * 
	 * @param row
	 * @param index
	 * @return
	 */
	private static String[] redRow(HSSFRow row, int[] index) {
		String str[]=new String[index.length];
		for(int j=0;j<index.length;j++){
			HSSFCell cell = row.getCell(index[j]);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			String cellValue = cell.getStringCellValue();
			if(cellValue!=null){
				str[j]=cellValue;
			}else{
				str[j]="";
			}
		}
		return str;
	}

	/*
	 * 批处理版本
	 */
	public static void batchUpdateTable(List<String[]> list,String sql) throws SQLException {
		long start = System.currentTimeMillis();
		Connection connection = DataSourceFactory.newInstance().getConnection("sqlserver");
		System.out.println("开始更新数据库的数据");
		System.out.println("执行sql="+sql);
		PreparedStatement pst = connection.prepareStatement(sql);
		int i=0;
		for(String[] r:list){
			System.out.println("当前数据="+r);
			for(int k=0;k<r.length;k++){
				pst.setString(k+1, r[k]);
			}
			pst.addBatch();
			if(i>0 && i%1000==0){
				 pst.executeBatch();
				 pst.clearBatch(); 
			}
			i++;
		}
		//执行边角
		if(list.size()%1000!=0){
			pst.executeBatch();
		}
		System.out.println("执行结束！");
		long end=System.currentTimeMillis();
		System.out.println("耗时="+(end-start));
		connection.close();
	}
}
