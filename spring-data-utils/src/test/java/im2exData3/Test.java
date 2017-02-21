package im2exData3;

/**
 * excel批量导入工具说明：
 * 1.c3p0-config.xml 中配数据库的置账号密码和地址(ip:port)
 * 2.指定表头规则，自动创建表
 * 3.将格式化的数据文件放到指定目录下，批量导入
 * 4.适合excel 2003-2007
 * 5.数据库:mysql
 */
public class Test {

	public static void main(String[] args) throws Exception {
		
		String tableHead="E:/2016/test/表头.xlsx";/*表头文件文件*/
		
		String dir="E:/2016/test/wx/";/*数据文件目录*/
		
		BuilderTable  bt=new BuilderTable(tableHead);
		
		bt.toBuilderTable();
		
		String sql=bt.getInsertSql();
		System.out.println("插入语句是:");
		System.out.println("============================");
		System.out.println(sql);
		System.out.println("============================");
		
		ImportExcel  ime=new ImportExcel(sql,dir);
		
		ime.toImportExcel();
	}
}
