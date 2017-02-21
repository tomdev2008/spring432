package org.spring.data.utils.export;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * POI 导出excel工作表的
 * 	思想总结：
	book--sheet--row--cell
	每个元素都是对象，并且具有层次关系，类似于DOM解析HTML文档
 */
public class App2 {

	/**
	 *POI : excel 文件有点类似于DOM树，一层一层管理的 
	 */
	public static void main(String[] args) throws Exception {
		/************************最基本的操作***************************/
		/*
		 * 创建对象，层次关系是：
		 * workbook-->sheet-->row-->cell
		 *   工作簿  表       行    列
		 */
		//创建工作簿（内存中）
		HSSFWorkbook wb = new HSSFWorkbook();
		
		//创建工作表（sheet）
		HSSFSheet sheet = wb.createSheet("sheet1");
		
		//创建行(row)，索引从0开始的
		HSSFRow row = sheet.createRow(0);
		
		//创建单元格(cell)并设置单元格值，索引从0开始的
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(true);//boolean值
		row.createCell(1).setCellValue(Calendar.getInstance());//时间
		row.createCell(2).setCellValue(new Date());//时间
		row.createCell(3).setCellValue(1234567890.9876543);//数字
		String str = "dddddddddddddddddddddddddddddddddddd" ;
		row.createCell(4).setCellValue(new HSSFRichTextString(str));//字符串
		
		
		
		/****************************一下是一些美化操作****************************/
		
		//格式化
		//格式化，样式等都封装成了对象形式。
		HSSFDataFormat format = wb.createDataFormat();//创建数据格式对象
		HSSFCellStyle style = wb.createCellStyle();//创建样式对象
		
		//日期类型数据格式化
		style.setDataFormat(format.getFormat("yyyy-MM-dd hh:mm:ss"));
		row.getCell(1).setCellStyle(style);//设置单元格样式，第2,3列是时间，将其格式化为本地形式
		row.getCell(2).setCellStyle(style);
		
		//数据类型数据格式化
		cell = row.getCell(3);//获得第四个单元格
		style = wb.createCellStyle();//创建样式
		style.setDataFormat(format.getFormat("#,###.0000"));//数据格式化
		cell.setCellStyle(style);//设置列的样式
		
		
		//列宽设置
		//设置列宽，单位是像素，自动列宽比较实用
		sheet.setColumnWidth(1, 5000);
		sheet.autoSizeColumn(2);//第3列自动列宽
		sheet.autoSizeColumn(3);//第4列自动列宽
		sheet.setColumnWidth(4, 6000);
		
		//文本类型的自动换行（回绕）
		//自动回绕文本
		cell = row.getCell(4);//获得第五列
		style = wb.createCellStyle();//创建样式
		style.setWrapText(true);//自动换行
		cell.setCellStyle(style);//将样式添加到列上
		
		//设置本文对齐方式
		row = sheet.createRow(1);
		row.createCell(0).setCellValue("左下");
		row.createCell(1).setCellValue("中中");
		row.createCell(2).setCellValue("右上");
		//设置行高
		row.setHeightInPoints(50);//行高
		
		//左下
		cell = row.getCell(0);
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);//左对齐
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);//下对齐
		cell.setCellStyle(style);
		
		//中中
		cell = row.getCell(1);
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//中对齐
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//中对齐
		cell.setCellStyle(style);
		
		//右上
		cell = row.getCell(2);
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);//右对齐
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);//上对齐
		cell.setCellStyle(style);
		
		//控制字体
		cell = row.getCell(1);
		style = cell.getCellStyle();
		HSSFFont font = wb.createFont();
				 font.setColor(HSSFColor.RED.index);//颜色
				 font.setFontName("宋体");//字体
		         font.setFontHeightInPoints((short)50);//大小
		         //font.setItalic(true);//斜体
		style.setFont(font);
		//style.setRotation((short)-30);//字体旋转
		
		//设置边框样式
		row = sheet.createRow(2);
		cell = row.createCell(0);
		style = wb.createCellStyle();
		style.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);//上边框，加粗
		style.setTopBorderColor(HSSFColor.BLUE.index);//蓝色
		cell.setCellStyle(style);
		
		//计算列
		row = sheet.createRow(3);
		row.createCell(0).setCellValue(50);
		row.createCell(1).setCellValue(80);
		row.createCell(2).setCellValue(100);
		cell = row.createCell(3);
		cell.setCellFormula("sum(A4:C4)");//计算列 即将函数写入excel
		
		//合并单元格 第5列  合并居中
		row = sheet.createRow(4);
		row.createCell(0).setCellValue("合并单元格");
		
		//合并指定行列的单元格内容
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
		
		//整体移动行
		//sheet.shiftRows(1, 3, 2);
		
		//拆分窗格
		//1000:左侧窗格的宽度
		//2000:上侧窗格的高度
		//3:右侧窗格开始显示列的索引
		//4:下侧窗格开始显示行的索引
		//1:激活的面板区
		//sheet.createSplitPane(1000, 2000, 3, 4, 1);
		
		
		// 可以设置将标题行冻结
		//冻结窗格
		//1:冻结的列数
		//2:冻结的行数
		//3:右侧窗格开始显示列的索引
		//4:下侧窗格开始显示行的索引
		//sheet.createFreezePane(100, 2);
		wb.write(new FileOutputStream("E:/myWeekSpace/poi/poi.xls"));
		wb.close();
	}
}
