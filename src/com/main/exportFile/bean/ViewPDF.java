package com.main.exportFile.bean;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.main.user.model.Student;

public class ViewPDF extends AbstractPdfView{

	@Override
	public void buildPdfDocument(Map<String, Object> model, Document document,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		    String excelName = "用户信息.pdf";  
	        // 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开  
	        response.setContentType("APPLICATION/OCTET-STREAM");  
	        response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(excelName, "UTF-8"));    	          
	        @SuppressWarnings("unchecked")
			List<Student> students = (List<Student>) model.get("students");            
	        //显示中文  
	        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);   
	        com.lowagie.text.Font FontChinese = new com.lowagie.text.Font(bfChinese, 12, com.lowagie.text.Font.NORMAL );          
	        document.open();
	        String value = null;
	        document.add(new Paragraph("学员信息",FontChinese)); 
	        if (students!=null && students.size()>0) {
	        	 for (int i = 0; i < students.size(); i++) {    
	 	            Student s = (Student)students.get(i);  
	 	            value = "学员ID: "+ s.getId()+"姓名: "+ s.getStudentName()+"年龄: "+s.getAge()+"头像Url: "+s.getImageUrl();  
	 	            document.add(new Paragraph(value,FontChinese));     
	 	        }
			}	       
	        document.close(); 
		
	}

	

}
