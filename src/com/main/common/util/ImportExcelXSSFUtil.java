package com.main.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


/**
 * 
 * AutoConfirmUtil Description: 根据第三方对账单进行自动对账 Company: www.edu24ol.com
 * 
 * @author pc-zw
 * @date 2015年12月3日下午5:37:38
 * @version 1.0
 */
public class ImportExcelXSSFUtil {

	private static Logger log;
	/** 行数 */
	private static int rowsNum = 0;

	/** 列数 */
	private static int columnNum = 0;

	// 根据报表类型进行Excel文件读取
	public static List<String> getExcelStringList(InputStream is) throws EncryptedDocumentException, InvalidFormatException {				
		try {
			List<String> results = new ArrayList<String>(); 
			//得到工作簿对象
			Workbook workbook = WorkbookFactory.create(is);
			//拿到第一个Sheet  
	        Sheet sheet = workbook.getSheetAt(0);   
	        // 获取Sheet的总行数
	     	rowsNum = sheet.getLastRowNum();;
	     	// 获取列数
	     	columnNum = getColumnNum(sheet, 4, 1);
	     	Row row = sheet.getRow(0);
			
	     	columnNum = getColumnNum(sheet, 4, 1);
			//开始读取
			results = readExcel(sheet, 0, 0, rowsNum, columnNum);
				
			return results;
		} catch (IOException e) {
			log.error("read Excel Throws Exception!");
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				log.error("fileinputStream close Throws Exception!");
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获取指定Sheet列数
	 * 
	 * @param sheet
	 * @param startRow
	 * @param startColumn
	 * @return
	 */
	private static int getColumnNum(Sheet sheet, int startRow,
			int startColumn) {
		int columnNum = 0;
		Row row = null;
		if (startRow == 0) {
			row = sheet.getRow(0);
		} else {
			row = sheet.getRow(startRow - 1);
		}
		for (int i = startColumn;; i++) {
			Cell cell = row.getCell(i);
			if (cell == null) {
				columnNum = i;
				break;
			} else {
				if (("").equals(cell.toString())) {
					columnNum = i;
					break;
				}
			}
		}
		return columnNum;
	}

	/**
	 * 逐行逐列解析Excel
	 * 
	 * @param sheet
	 * @param startRow
	 * @param startColumn
	 * @param totalRows
	 * @param columnNum
	 * @return
	 */
	private static List<String> readExcel(Sheet sheet, int startRow,
			int startColumn, int totalRows, int columnNum) {
		List<String> results = new ArrayList<String>();
		try {
			// 是否继续解析标记
			boolean flag = true;
			// 开始循环行
			for (int i = startRow; i <= totalRows; i++) {
				StringBuffer sb = new StringBuffer();
				if (!flag) {
					break;
				}
				Row row = sheet.getRow(i);
				if (row == null) {// 空行时跳出循环
					break;
				}
				// 开始循环列
				for (int j = startColumn; j < columnNum; j++) {
					Cell cell = row.getCell(j);
					if (cell == null) {
						continue;
					} else {
						sb.append("\t"+getCellValue(cell)) ;
					}
				}
				results.add(sb.toString()+"\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	
	/**
	 * 处理并读取单元格的内容
	 * 
	 * @param cell
	 * @return
	 */
	private static String getCellValue(Cell cell) {
		String ret;
		switch (cell.getCellType()) {
		// 空白
		case Cell.CELL_TYPE_BLANK:
			ret = "";
			break;
		// 布尔
		case Cell.CELL_TYPE_BOOLEAN:
			ret = String.valueOf(cell.getBooleanCellValue());
			break;
		// 错误
		case Cell.CELL_TYPE_ERROR:
			ret = null;
			break;
		// 数值
		case Cell.CELL_TYPE_NUMERIC:
			 double cellvalue = cell.getNumericCellValue(); 
	        //判断是否是标准日期格式或者是自定义日期格式
			if (org.apache.poi.ss.usermodel.DateUtil.isValidExcelDate(cellvalue) || cell.getCellStyle().getDataFormat()==179) {
            		SimpleDateFormat sdf = new SimpleDateFormat(
            				DateUtil.BOTH);            		
					ret = sdf.format(cell.getDateCellValue()); 
			}else{
					ret = cellvalue + "";
			}            	             
            break;  
		// 字符串
		case Cell.CELL_TYPE_STRING:
			ret = cell.getStringCellValue();
			break;
		// 默认
		default:
			ret = null;
		}
		return ret;
	}
}
