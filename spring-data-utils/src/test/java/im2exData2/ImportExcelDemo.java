package im2exData2;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.spring.data.utils.dal.DataSourceFactory;

/*导入*/
public class ImportExcelDemo {

	@Test
	public void test() throws Exception{
		
		String dir="E:/2016/test/wx";
		
		File d=new File(dir);
		File[] fs = d.listFiles();
		
		for(File f:fs){
			importExcel(f);
		}
	}
	
	private void importExcel(File f) throws Exception{
		
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(f));
		
		System.out.println("=====================================");
		System.out.println("正在处理:"+f);
		
		XSSFSheet sheet = workbook.getSheetAt(0);
		importSheet(sheet);
		
		System.out.println("处理处理结束:"+f);
		System.out.println("=====================================");

		workbook.close();
	}
	
	private String sql="INSERT INTO abc (policy_name,agent_number,agent_name,subarea,product_group_number,bu,"+
	"discount,discount_amount, sales_date,expect_repayment_date,order_number,agent_order_number,"+ 
	"invoice_amount,pre_tax,tax,sales_amount,repayment_date,"+
	"cancel_date,transfer_date,business_scope,invoice,accounting_voucher,"+
	"order_type,payment_term,province,city,calculate_date,agent_level,"+ 
	"distribution_channel,order_reason,legal_person,computing_symbol,silver_ticket)"+
	" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private int batchSize=3;
	
	public void importSheet(XSSFSheet sheet) {
		
		int rows = sheet.getLastRowNum();
		
		List<String[]> data=new ArrayList<String[]>(batchSize);
		
		for(int r=1;r<=rows;r++){
			data.add(redRow(sheet.getRow(r)));
			if(r%batchSize==0){
				executeBatchSQL(sql,data);
				data.clear();
			}
		}
		
		if(!data.isEmpty()){
			executeBatchSQL(sql,data);
		}
	}
	
	private String[] redRow(XSSFRow row) {
	
		short len = row.getLastCellNum();
		String [] parms=new String[len];
		for(int j=0;j<len;j++){
			XSSFCell cell = row.getCell(j);
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
