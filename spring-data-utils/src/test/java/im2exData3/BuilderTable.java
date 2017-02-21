package im2exData3;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.spring.data.utils.dal.DataSourceFactory;

/*导入*/
public class BuilderTable {

	private String dir;/*表头文件文件*/
	
	private List<String> fileds=new ArrayList<String>(33);
	
	private String tableName;
	
	BuilderTable(String file){
		this.dir=file;
	}
	
	//建表
	public void toBuilderTable() throws Exception{
		
		File file=new File(dir);
		
		readExcel(file);
	}
	
	//获得插入语句
	public String getInsertSql(){
		StringBuilder sql=new StringBuilder();
		
		sql.append("insert into ");
		sql.append(tableName);
		sql.append(" (");
		
		for(int i=0;i<fileds.size();i++){
			if(i==0){
				sql.append(fileds.get(i));
			}else{
				sql.append(",");
				sql.append(fileds.get(i));
			}
		}
		
		sql.append(") VALUES ( ");
		for(int i=0;i<fileds.size();i++){
			if(i==0){
				sql.append("?");
			}else{
				sql.append(",?");
			}
		}
		sql.append(")");
		
		return sql.toString();
	}
	
	//读物excel
	private void readExcel(File f) throws Exception{
		
		Workbook workbook = WorkbookFactory.create(f);
		
		Sheet sheet = workbook.getSheetAt(0);
		
		String sql=builderTableSQL(sheet);
		
		System.out.println("获得建表语句:");
		System.out.println("=====================================");
		System.out.println(sql);
		System.out.println("=====================================");
		
		executeSQL(sql);
		System.out.println("表创建成功");
		
		workbook.close();
	}
		
	//执行sql
	private void executeSQL(String sql) {
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
	
	//构建建表语句
	private String builderTableSQL(Sheet sheet){

		StringBuilder sql=new StringBuilder();
		
		/*第一行是表名称*/
		tableName=getCellValue(sheet.getRow(0),1);
		sql.append("CREATE TABLE ");
		sql.append(tableName);
		sql.append(" (");
		sql.append("id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '主键'");
	
		/*从第三行起是表字段与说明:第一列说明，第二列字段*/
		int rows=sheet.getLastRowNum();
		for(int i = 2;i<=rows;i++){
			Row row = sheet.getRow(i);
			sql.append(",\r\n");
			sql.append(getCellValue(row,1));
			sql.append(" VARCHAR(256) DEFAULT NULL COMMENT");
			sql.append("'");
			sql.append(getCellValue(row,0));
			sql.append("'");
			fileds.add(getCellValue(row,1));
		}
		
		sql.append(" )");
		
		return sql.toString();
	}
	
	private String getCellValue(Row row,int k) {
		Cell cell = row.getCell(k);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		return cell.getStringCellValue();
	}
}
