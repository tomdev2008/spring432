package im2exdata;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

//import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *  excel修改工具
 * 
 * @author <a href="mailto:tingping@ssreader.cn">tingping</a>
 * @Version 2014-5-12
 */
public class UpdateExcelUtils {
	public static void main(String[] args) {
		try {
			UpdateExcelUtils imp = new UpdateExcelUtils();
			imp.readExcel("E:/myWeekSpace/poi/test5.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readExcel(String path) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(path));
		HSSFSheet sheet = workbook.getSheetAt(0);
		int rows = sheet.getLastRowNum();
		// 读取一个sheet
		for (int index = 1; index <= rows; index++) {

			// 读取一行
			HSSFRow row = sheet.getRow(index);
			readRow(row);
		}
		workbook.write(new FileOutputStream(path));
	}

	/**
	 * 读取行修改
	 * 
	 * @param row
	 */
	private void readRow(HSSFRow row) {
		//HSSFCell cell1 = row.getCell(1);
	}
}
