package com.main.exportFile.controller;

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

import com.main.exportFile.bean.ViewExcel;

/**
 * 
 * Title:   ExportExcelController.java
 * Description:����Excel�ļ�   
 * Company:   www.edu24ol.com
 * @author   pc-zw
 * @date     2015��12��17������3:17:54
 * @version  1.0
 */
@RequestMapping("/opExcel")
@Controller
public class ExportExcelController {
	/**
	    * ����Excel
	    * @param model
	    * @param projectId
	    * @param request
	    * @return
	    */
	   @RequestMapping(value="/exportExcel.do",method=RequestMethod.GET)
	   @ResponseBody
		public ModelAndView toDcExcel(ModelMap model, HttpServletRequest request,HttpServletResponse response){
		   List<String> list = new ArrayList<String>();  //��������û���õ�
		   Map<String, Object> map = new HashMap<String, Object>();  
	       list.add("test1");  
	       list.add("test2");  
	       map.put("list", list);  
	       ViewExcel viewExcel = new ViewExcel();
	       Map obj = null;  
	        
	       //��ȡ���ݿ�����ɵ�workbook    
	       HSSFWorkbook workbook = new HSSFWorkbook();  
	       try {  
	        viewExcel.buildExcelDocument(map, workbook, request, response);
	       } catch (Exception e) {  
	     // TODO Auto-generated catch block  
	     e.printStackTrace();  
	       }  
	       return new ModelAndView(viewExcel, model); 
	   }
}
