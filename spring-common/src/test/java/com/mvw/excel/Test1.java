package com.mvw.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * excel批量导入工具说明：
 * 1.c3p0-config.xml 中配数据库的置账号密码和地址(ip:port)
 * 2.指定表头规则，自动创建表
 * 3.将格式化的数据文件放到指定目录下，批量导入
 * 4.适合excel 2003-2007
 * 5.数据库:mysql
 */
public class Test1 {

	@Test
	public void test1() throws Exception{
		File f=new File("E:/test/123.xlsx");
		ReadExcel r=new ReadExcel();
		r.read(f);
	}
	
	@Test
	public void test2() throws Exception{
		
		List<String[]> data=new ArrayList<String[]>();
		data.add(new String []{"a","c"});
		
		WriteExcel w=new WriteExcel();
		File f=new File("E:/test/456.xls");
		f.createNewFile();
		w.write(f, data);
	}
}
