package com.qhit.utils;


import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

public class CommonUtil {

	public static Connection getConnection(){
		Connection conn=null;

		try {
//			获取mysql
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ems?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT", "root", "123456");
//			获取oracle
//			Class.forName("oracle.jdbc.OracleDriver");
			//new oracle.jdbc.driver.OracleDriver();
//			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "root", "123456");
		} catch (Exception e) {
//			 TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public static void exportExcel(String title, String[] name, ResultSet rs) throws Exception {
//		HSSFWorkbook workBook = new HSSFWorkbook(); //创建excel文件
		XSSFWorkbook workBook=new XSSFWorkbook();
		XSSFSheet sheet = workBook.createSheet();
		//创建工作表
		
		for(int i=0; i<name.length; i++){
			sheet.setColumnWidth(i, 20*256);
		}
		
		//标题样式
		XSSFCellStyle titleStyle = workBook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平居中
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		XSSFFont titleFont =  workBook.createFont(); //字体
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //加粗
		titleFont.setFontHeightInPoints((short)14);  //字号
		titleStyle.setFont(titleFont);               //标题使用字体样式
		//正文样式
		XSSFCellStyle contentStyle = workBook.createCellStyle();
		XSSFFont contentFont = workBook.createFont(); //字体
		contentFont.setFontHeightInPoints((short)12);
		contentStyle.setFont(contentFont);
		contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//边框
		contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中
		contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		XSSFRow titleRow = sheet.createRow(0);    //创建标题行
		XSSFCell titleCell = titleRow.createCell(0);
		titleCell.setCellValue(title);
		titleCell.setCellStyle(titleStyle);     //单元格样式
		CellRangeAddress address = new CellRangeAddress(0, 0, 0, name.length-1);
		sheet.addMergedRegion(address);
		titleRow.setHeightInPoints(30); //行高


		XSSFRow row2 = sheet.createRow(1);
		row2.setHeightInPoints(20);
		for(int i=0; i<name.length; i++){
			XSSFCell cell = row2.createCell(i);
			cell.setCellValue(name[i]);
			cell.setCellStyle(contentStyle);
		}
		
		int flag = 2;
		while(rs.next()){
			XSSFRow datarow = sheet.createRow(flag);
			datarow.setHeightInPoints(20);
			ResultSetMetaData metaData = rs.getMetaData();
			for(int i=0; i<metaData.getColumnCount(); i++){
				XSSFCell datacell = datarow.createCell(i);
				datacell.setCellValue(rs.getString(i+1));
				datacell.setCellStyle(contentStyle);
			}
			
			flag++;
		}
		
		String path = "e:\\demo.xlsX";
		FileOutputStream out = new FileOutputStream(path);
		workBook.write(out);	//保存单元格
		out.close();
		
	}
}
