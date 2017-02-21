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
public class ImportExcel {

	private String sql=null;/*导入sql语句*/
	
	private final int batchSize=500;/*每次批量导入的条数*/
	
	private String dir;/*excel表所在的目录*/
	
	ImportExcel(String sql,String dir){
		this.sql=sql;
		this.dir=dir;
	}
	
	public void toImportExcel() throws Exception{
		
		File d=new File(dir);
		File[] fs = d.listFiles();
		
		for(File f:fs){
			importExcel(f);
		}
	}
	
	private void importExcel(File f) throws Exception{
		
		Workbook workbook = WorkbookFactory.create(f);
		
		System.out.println("=====================================");
		System.out.println("正在处理:"+f);
		
		Sheet sheet = workbook.getSheetAt(0);
		importSheet(sheet);
		
		System.out.println("处理处理结束:"+f);
		System.out.println("=====================================");

		workbook.close();
	}
	
	public void importSheet(Sheet sheet) {
		int rows = sheet.getLastRowNum();
		
		List<String[]> data=new ArrayList<String[]>(batchSize);
		
		for(int r=1;r<=rows;r++){
			data.add(redRow(sheet.getRow(r)));
			if(r%batchSize==0){
				executeBatchSQL(sql,data);
				data=null;
				data=new ArrayList<String[]>(batchSize);
			}
		}
		
		if(data!=null){
			executeBatchSQL(sql,data);
		}
	}
	
	private String[] redRow(Row row) {
	
		short len = row.getLastCellNum();
		String [] parms=new String[len];
		for(int j=0;j<len;j++){
			Cell cell = row.getCell(j);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			parms[j]=cell.getStringCellValue();
		}
		
		return parms;
	}

	//批量执行sql
	public void executeBatchSQL(String sql,List<String []> args) {
		try{
			Connection connection = DataSourceFactory.newInstance().getConnection("mysql");
			PreparedStatement pst = connection.prepareStatement(sql);
			for(String[] r:args){
				for(int k=0;k<r.length;k++){
					pst.setString(k+1, r[k]);
				}
				pst.addBatch();
			}
			pst.executeBatch();
			connection.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
