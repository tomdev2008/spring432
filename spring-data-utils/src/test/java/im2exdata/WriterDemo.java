package im2exdata;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 一个最基本的写Excel的例子
 */
public class WriterDemo {
	public static void main(String[] args) throws Exception {
		test();
	}
	
	//@Test
	public static void test() throws FileNotFoundException, IOException {
		//声明文件的保存路径
		String pathName = "E:/myWeekSpace/poi/test1.xls";
		
		//1.创建一个工作簿Workbook(空的工作簿)
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		//2.创建一个工作表:默认为sheet1，空的工作表
		HSSFSheet sheet = workbook.createSheet();
		
		//3.创建行
		HSSFRow row = sheet.createRow(0);
		
		//4.创建单元格，设置值，单元格才是最终的操作数据的对象
		//一般设置的值是字符串，格式用Excel设置即可
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("张三");
		
		//写出公式：=SUM(D6:E6)
		
		//5.写到一个文件中
		//类似于DOM解析HTML.这里是将工作簿对象写到文件中，保存下来
		//workbook.writeProtectWorkbook("password", "root");
		workbook.write(new FileOutputStream(pathName));
	}
}
