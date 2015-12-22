package com.main.exportFile.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import com.main.common.util.RandomStrUtil;
import com.main.exportFile.bean.HSSFViewExcel;
import com.main.exportFile.bean.ViewExcel;
import com.main.exportFile.bean.ViewPDF;
import com.main.user.model.Student;

/**
 * 
 * Title:   ExportExcelController.java
 * Description:导出Excel文件   
 * Company:   www.edu24ol.com
 * @author   pc-zw
 * @date     2015年12月17日下午3:17:54
 * @version  1.0
 */
@RequestMapping("/opExcel")
@Controller
public class ExportExcelController {
	  /**
	    * 导出Excel
	    * @param model
	    * @param projectId
	    * @param request
	    * @return
	    */
	   @RequestMapping(value="/exportExcel.do",method=RequestMethod.GET)
	   @ResponseBody
		public ModelAndView toDcExcel(ModelMap model, HttpServletRequest request,HttpServletResponse response){
		   List<Student> students = new ArrayList<Student>();  //测试数据没有用到
		   Map<String, Object> datas = new HashMap<String, Object>();  
		   for (int i = 0; i <= 100; i++) {
				Student student = new Student();
				student.setId(i);
				student.setStudentName(RandomStrUtil.getCharAndNumr(6));
				student.setAge(20+i);
				students.add(student);
			  } 
		   datas.put("students", students);
		   //获取数据库表生成的workbook    
	       HSSFWorkbook workbook = new HSSFWorkbook(); 
	       ViewExcel viewExcel = new ViewExcel(); 
	       try {
			viewExcel.buildExcelDocument(datas, workbook, request, response);
		   } catch (IOException e1) {
			e1.printStackTrace();
		   }
	       return new ModelAndView(viewExcel, model); 
	   }
	   
	   
	   
	   /* 导出Excel
	    * @param model
	    * @param projectId
	    * @param request
	    * @return
	    */
	   @RequestMapping(value="/exportPdf.do",method=RequestMethod.GET)
	   @ResponseBody
		public ModelAndView toDcPdf(ModelMap model, HttpServletRequest request,HttpServletResponse response){
		   List<Student> list = new ArrayList<Student>();  //测试数据没有用到
		   Map<String, Object> map = new HashMap<String, Object>();  
	       for (int i = 0; i <= 100; i++) {
			Student student = new Student();
			student.setId(i);
			student.setStudentName(RandomStrUtil.getCharAndNumr(6));
			student.setAge(20+i);
			list.add(student);
		  } 
	       map.put("list", list);  
	       ViewPDF viewPdf = new ViewPDF();
	       Document document = null;
	       PdfWriter writer = null;
	       try {  
	    	   document = new Document();
	    	   writer = PdfWriter.getInstance(document, new FileOutputStream(new File("D:\\POReceiveReport.pdf"),true));
	    	   viewPdf.buildPdfDocument(map, document , writer, request, response);
	       } catch (Exception e) {    
	     e.printStackTrace();  
	       } finally{
	    	   writer.close();
	    	  
	       } 
	       return new ModelAndView(); 
	   }
}
