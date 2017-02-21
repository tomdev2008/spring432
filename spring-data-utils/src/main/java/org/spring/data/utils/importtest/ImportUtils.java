package org.spring.data.utils.importtest;

import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

/**
 * 导出excel
 * 
 * @author <a href="mailto:tingping@ssreader.cn">tingping</a>
 * @Version 2014-5-11
 */
public class ImportUtils {
	public static void main(String[] args) throws Exception {
		test1("E:/myWeekSpace/poi/test1.xls");
	}
	
	public static void test1(String path) throws Exception{
		FileInputStream in = new FileInputStream(path);
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		HSSFSheet sheet = workbook.getSheetAt(0);
		int rows=sheet.getLastRowNum();
		for(int index = 0;index<=rows;index++){
			HSSFRow row = sheet.getRow(index);
			HSSFCell cell = row.getCell(0);
			//能精确的读取到0.00000000001
			cell.setCellType(Cell.CELL_TYPE_STRING);
			String cellValue = cell.getStringCellValue();
			System.out.println("cellValue="+cellValue);
		}
		in.close();
	}
}
