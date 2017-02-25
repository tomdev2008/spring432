package test_native_jdbc.readtest;

import java.io.File;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

/*导入*/
public class ReadExcel1 {

	@Test
	public void test1(){
		
		try{
			
			String path="E:/2017/123.xlsx";
			
			File file=new File(path);
			
			readExcel(file);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	
	public void readExcel(File f) throws Exception{
		
		//兼容2003,2007读取
		Workbook workbook = WorkbookFactory.create(f);
		
		Sheet sheet = workbook.getSheetAt(0);
		
		readSheet(sheet);

		//workbook.close();
	}
	
	public void readSheet(Sheet sheet){

		//获得总行数
		int rows=sheet.getLastRowNum();
		System.out.println("rows="+rows);
		
		for(int i = 0;i<=rows;i++){
			Row row = sheet.getRow(i);
			readRow(row);
			System.out.println();
		}
	}
	
	private void readRow(Row row) {

		//总列数
		short cells = row.getLastCellNum();
		System.out.println("\tcells="+cells);
		
		for(int i=0;i<cells;i++){
			System.out.print("\t"+getCellValue(row,i));
		}
	}

	private String getCellValue(Row row,int k) {
		
		Cell c = row.getCell(k);
		
		//都以字符串类型获取
		c.setCellType(Cell.CELL_TYPE_STRING);
		return c.getStringCellValue();
		
		//		if(c.getCellType()==Cell.CELL_TYPE_STRING){
		//			return c.getStringCellValue();
		//		}else if(c.getCellType()==Cell.CELL_TYPE_NUMERIC){
		//			return c.getNumericCellValue()+"";
		//		}
		
	}
}