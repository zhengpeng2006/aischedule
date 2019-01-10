package com.asiainfo.common.utils;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelWriter {
	
	/**
	 * 创建工作簿
	 * 不附加表单创建是为了方便设置格式  默认只有居中
	 * @return
	 * @throws Exception
	 */
	public static HSSFWorkbook createWorkBook() throws Exception{
		// 创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        return wb;
	}
	
	/**
	 * 创建表单
	 * @param wb
	 * @param name
	 * @return
	 */
	public static HSSFSheet createSheet(HSSFWorkbook wb,String name) throws Exception{
		if (wb == null){
			throw new Exception("Workbook is null");
		}
		HSSFSheet sheet = null;
		if (StringUtils.isNotBlank(name)){
			sheet = wb.createSheet(name); 
		}else{
	        sheet = wb.createSheet(); 
		}
        return sheet;
	}
	
	
	/**
	 * 写行
	 * @param sheet
	 * @param index 第几行（小于0时则在本页面最后一行后添加）
	 * @return
	 */
	public static HSSFRow createNewRow(HSSFSheet sheet,int index) throws Exception{
		if (sheet == null){
			throw new Exception("sheet is null");
		}
		if (index < 0){
			if (sheet.getLastRowNum() == 0 && null == sheet.getRow(0)){
				index = 0;
			}else{
				index = sheet.getLastRowNum()+1;
			}
		}
        HSSFRow row = sheet.createRow(index);
        return row;
	}
	
	/**
	 * 写单元格
	 * @param row
	 * @param content
	 * @param style
	 * @param index 单元格序号 小于0时在最后一个添加
	 */
	public static void writeCell(HSSFRow row,Object content,int index) throws Exception{
		if (row == null){
			throw new Exception("row is null");
		}
		if (index < 0){
			if (row.getLastCellNum() < 0){
				index = 0;
			}else{
				index = row.getLastCellNum();
			}
		}
        HSSFCell cell = row.createCell(index);
        HSSFCellStyle style =  cell.getCellStyle();
        style.setWrapText(true);
        cell.setCellStyle(style);
        if (content instanceof String){
            cell.setCellValue((String)content);  
        }else if (content instanceof Long){
            cell.setCellValue((Long)content);  
        }else if (content instanceof Integer){
            cell.setCellValue((Integer)content);  
        }else if (content instanceof Double){
            cell.setCellValue((Double)content);  
        }else if (content instanceof Float){
            cell.setCellValue((Float)content);  
        }
	}
	
	/**
	 * 将文件写入流
	 * @param wb
	 * @param out
	 * @return
	 * @throws IOException
	 */
	public static OutputStream write2stream(HSSFWorkbook wb,OutputStream out) throws IOException{
		wb.write(out);
		return out;
	}
	
}
