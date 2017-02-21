package com.mvw.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/*导入*/
public class WriteExcel {


	public void write(File f,List<String[]> data) throws Exception{
		
		Workbook workbook = new HSSFWorkbook();
		
		System.out.println("=====================================");
		System.out.println("正在处理:"+f);
		
		Sheet sheet = workbook.createSheet("sheet1");
		writeSheet(sheet,data);
		
		System.out.println("处理处理结束:"+f);
		System.out.println("=====================================");

		workbook.write(new FileOutputStream(f));
		workbook.close();
	}
	
	public void writeSheet(Sheet sheet,List<String[]> data) {
		
		for(int r=0;r<data.size();r++){
			writeRow(sheet.createRow(r), data.get(r));
		}
	}

	private void writeRow(Row row, String[] tmpData) {
		for (int i = 0; i < tmpData.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(tmpData[i]);
		}
	}
}
