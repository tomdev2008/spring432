package im2exdata;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

/**
 * 读取excel
 * */
public class ReaderDemo {

	/**
	 * 读取取某一个工作簿的所有工作表
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 * */
	//@Test
	public void read1() throws FileNotFoundException, IOException {
		// 声明工作簿所在的目录位置
		String pathName = "E:/myWeekSpace/poi/poi.xls";

		// 在构造的时候可以指定和某一个已经存在的excel文件关联上
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(pathName));

		// 读取工作簿
		readSheetsFromWorkbook(workbook);

	}

	/**
	 * 读取一个工作簿的所有的工作表(工作簿)
	 */
	private void readSheetsFromWorkbook(HSSFWorkbook workbook) {
		for (int index = 0; index < workbook.getNumberOfSheets(); index++) {
			HSSFSheet sheet = workbook.getSheetAt(index);// 根据索引定位表，从0开始
			System.out.println("工作表名称：" + sheet.getSheetName());
			readRowFromSheet(sheet);// 读取所有行
		}
	}

	/**
	 * 读取一个工作表的所有行(工作表)
	 */
	private void readRowFromSheet(HSSFSheet sheet) {
		 /*
		  *   空行row是null
		  *  
		  *   sheet.getLastRowNum();
		  *   
		  *   说明:行号从0开始，小于等于最后一行的行号
		  */
		for (int rowIndex = 0;rowIndex<=sheet.getLastRowNum(); rowIndex++) {
			HSSFRow row = sheet.getRow(rowIndex);
			if (row == null) {//下一个  防止空行的情况
				continue;
			}
			readCellValueFromRow(row);// 读取所有列
			System.out.println();
		}
	}

	/**
	 * 读取一行的所有列(行)
	 */
	private void readCellValueFromRow(HSSFRow row) {
		short len = row.getLastCellNum();//序号还是从0开始的啊奥
		for (int cellnum = 0; cellnum < len; cellnum++) {
			HSSFCell cell = row.getCell(cellnum);
			if (cell != null) {
				cell.setCellType(Cell.CELL_TYPE_STRING);/*忽略数据类型*/
				String cellValue = cell.getStringCellValue();
				if (cellnum != 0) {
					System.out.print("\t");
				}
				System.out.print(cellValue);
			}
		}
	}
	
	public static void main(String[] args) {
		int m=Short.MAX_VALUE;//32767 这是最大的列数
		System.out.println(m);
	}
}
