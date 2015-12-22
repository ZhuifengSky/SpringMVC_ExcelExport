package com.main.exportFile.bean;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.main.user.model.Student;

public class HSSFViewExcel extends AbstractExcelView {

	@Override
	public void buildExcelDocument(Map<String, Object> datas,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		HSSFSheet sheet = workbook.createSheet("学员信息");
		//Excel样式
		Map<String, CellStyle> styles = createStyles(workbook);  
		//创建合并区域
		CellRangeAddress rangeAddress=new CellRangeAddress(0, 2, 0, 3);                
        //在sheet里增加合并单元格  
        sheet.addMergedRegion(rangeAddress); 
        //设置宽度
        sheet.setColumnWidth(3, 256*40);
        //创建标题行
        HSSFRow rowTitle = sheet.createRow(0);
        //为标题行创建一个单元格
        HSSFCell titleCell = rowTitle.createCell(0);
        titleCell.setCellValue("学员列表");
        //设置单元格样式
        titleCell.setCellStyle(styles.get("title"));  
        //创建header行
        Row rowHeader = sheet.createRow(3);
        rowHeader.setRowStyle(styles.get("header"));
        //创建单元格
		Cell headerCell1 = rowHeader.createCell(0);
		headerCell1.setCellValue("学员ID");
		//创建单元格
		Cell headerCell2 = rowHeader.createCell(1);
		headerCell2.setCellValue("姓名");
		//创建单元格
		Cell headerCell3 = rowHeader.createCell(2);
		headerCell3.setCellValue("年龄");
		//创建单元格
		Cell headerCell4 = rowHeader.createCell(3);
		headerCell4.setCellValue("头像Url");
		//获取数据并填充Excel
		@SuppressWarnings("unchecked")
		List<Student> students = (List<Student>) datas.get("students");
        if (students!=null && students.size()>0) {
        	for (int i = 0; i < students.size(); i++) {
            	Row contentRow = sheet.createRow(i+4);
            	contentRow.setRowStyle(styles.get("cell"));
            	Student student = students.get(i);
            	Cell contentCell1=contentRow.createCell(0);
            	contentCell1.setCellValue(student.getId());
            	Cell contentCell2=contentRow.createCell(1);
            	contentCell2.setCellValue(student.getStudentName());
            	Cell contentCell3=contentRow.createCell(2);
            	contentCell3.setCellValue(student.getAge());
            	Cell contentCell4=contentRow.createCell(3);
            	contentCell4.setCellValue(student.getImageUrl());
            }
		}		
		
		String filename = "学员信息.xls";// 设置下载时客户端Excel的名称
		filename = MyUtils.encodeFilename(filename, request);// 处理中文文件名
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="
				+ filename);
		OutputStream ouputStream = response.getOutputStream();
		workbook.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}
	
	
	/**
	 * 创建Excel样式  
	 * @param wb
	 * @return
	 */
	private Map<String, CellStyle> createStyles(Workbook wb)  
	{  
	    Map<String, CellStyle> styles = new HashMap<String, CellStyle>();  
	    CellStyle style;  
	    Font titleFont = wb.createFont();  
	    titleFont.setFontHeightInPoints((short) 18);  
	    titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);  
	    style = wb.createCellStyle();  
	    style.setAlignment(CellStyle.ALIGN_CENTER);  
	    style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  
	    style.setFont(titleFont);  
	    styles.put("title", style);  
	  
	    style = wb.createCellStyle();  
	    style.setAlignment(CellStyle.ALIGN_CENTER);  
	    style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  
	    style.setWrapText(true);  
	    styles.put("header", style);  
	  
	    style = wb.createCellStyle();  
	    style.setAlignment(CellStyle.ALIGN_CENTER);  
	    style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  
	    style.setWrapText(true);  
	    styles.put("cell", style);  
	  
	    style = wb.createCellStyle();  
	    style.setAlignment(CellStyle.ALIGN_CENTER);  
	    style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  
	    style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());  
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);  
	    style.setDataFormat(wb.createDataFormat().getFormat("0.00"));  
	    styles.put("formula", style);  
	  
	    style = wb.createCellStyle();  
	    style.setAlignment(CellStyle.ALIGN_CENTER);  
	    style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  
	    style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());  
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);  
	    style.setDataFormat(wb.createDataFormat().getFormat("0.00"));  
	    styles.put("formula_2", style);  
	  
	    return styles;  
	}  
}
