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
		    String excelName = "�û���Ϣ.pdf";  
	        // ����response��ʽ,ʹִ�д�controllerʱ���Զ���������ҳ��,����ֱ��ʹ��excel��  
	        response.setContentType("APPLICATION/OCTET-STREAM");  
	        response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(excelName, "UTF-8"));    
	          
	        @SuppressWarnings("unchecked")
			List<Student> stuList = (List<Student>) model.get("list");            
	        //��ʾ����  
	        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);   
	        com.lowagie.text.Font FontChinese = new com.lowagie.text.Font(bfChinese, 12, com.lowagie.text.Font.NORMAL );          
	        document.open();
	        String value = null;  
	        for (int i = 0; i < stuList.size(); i++) {    
	            Student s = (Student)stuList.get(i);  
	            value = "���: "+ s.getId()+"����: "+ s.getStudentName()+",����: "+s.getAge();  
	            document.add(new Paragraph(value,FontChinese));     
	        }
	        document.close(); 
		
	}

	

}
