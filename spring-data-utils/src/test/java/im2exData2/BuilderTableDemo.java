package im2exData2;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.spring.data.utils.dal.DataSourceFactory;

/*导入*/
public class BuilderTableDemo {

	@Test
	public void test() throws Exception{
		
		String dir="E:/2016/test/wx/表头.xls";
		
		File file=new File(dir);
		
		readExcel(file);
	}
	
	private void readExcel(File f) throws Exception{
		
		Workbook workbook = WorkbookFactory.create(f);
		
		Sheet sheet = workbook.getSheetAt(1);
		
		String sql=builderTableSQL(sheet);
		
		System.out.println("获得建表语句:");
		System.out.println("=====================================");
		System.out.println(sql);
		System.out.println("=====================================");
		
		//executeSQL(sql);
		System.out.println("表创建成功");
		
		workbook.close();
	}
		
	//执行sql
	public void executeSQL(String sql) {
		try{
			Connection connection = DataSourceFactory.newInstance().getConnection("mysql");
			PreparedStatement pst = connection.prepareStatement(sql);
			pst.execute();
			pst.close();
			connection.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String builderTableSQL(Sheet sheet){

		StringBuilder sql=new StringBuilder();
		
		sql.append("CREATE TABLE ");
		sql.append(getCellValue(sheet.getRow(0),1));
		sql.append(" (");
		sql.append("id INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键' PRIMARY KEY");
	
		int rows=sheet.getLastRowNum();
		for(int i = 2;i<=rows;i++){
			Row row = sheet.getRow(i);
			sql.append(",\r\n");
			sql.append(getCellValue(row,1));
			sql.append(" VARCHAR(256) DEFAULT NULL COMMENT ");
			sql.append("'");
			sql.append(getCellValue(row,0));
			sql.append("'");
		}
		
		sql.append(" )");
		
		return sql.toString();
	}
	
	private Object getCellValue(Row row,int k) {
		Cell cell = row.getCell(k);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		return cell.getStringCellValue();
	}
}
