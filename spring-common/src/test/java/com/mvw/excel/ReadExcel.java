package com.mvw.excel;

import java.io.File;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/*导入*/

/**
 * 熟悉这个数据结构，也就好操作多了
 * work
 *    |----sheet
 *         |----row
 *         		 |----cell
 *    
 * @author gaotingping
 *
 * 2016年8月19日 下午1:12:36
 */
public class ReadExcel {


	public void read(File f) throws Exception{
		
		Workbook workbook = WorkbookFactory.create(f);
		
		System.out.println("=====================================");
		System.out.println("正在处理:"+f);
		
		Sheet sheet = workbook.getSheetAt(0);
		parseSheet(sheet);
		
		System.out.println("处理处理结束:"+f);
		System.out.println("=====================================");

		workbook.close();
	}
	
	public void parseSheet(Sheet sheet) {
		
		int rows = sheet.getLastRowNum();
		for(int r=1;r<=rows;r++){
			parseRow(sheet.getRow(r));
		}
	}
	
	private void parseRow(Row row) {
		short len = row.getLastCellNum();
		for(int j=0;j<len;j++){
			Cell cell = row.getCell(j);
			cell.setCellType(Cell.CELL_TYPE_STRING);/*不管什么类型，全部以字符串的格式读取*/
			System.out.print(cell.getStringCellValue());
			System.out.print("\t");
		}
		System.out.println();
	}
}
