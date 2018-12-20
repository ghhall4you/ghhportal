package com.ghh.framework.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ghh.framework.core.annotation.NotProguard;


/*****************************************************************
 *
 * EXCEL辅助类
 *
 * @author ghh
 * @date 2018年12月19日下午10:44:03
 * @since v1.0.1
 ****************************************************************/
@NotProguard
public final class PoiExcelUtil {
	/**
	 * excel解析工具
	 * 
	 * @param is
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<HashMap<String, String>> parseExcel(InputStream is) throws InvalidFormatException, IOException {
		Workbook workbook = WorkbookFactory.create(is);
		return parseExcel(workbook);
	}

	/**
	 * excel解析工具
	 * 
	 * @param file
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<HashMap<String, String>> parseExcel(File file) throws InvalidFormatException, IOException {
		Workbook workbook = WorkbookFactory.create(file);
		return parseExcel(workbook);
	}

	/**
	 * excel解析工具
	 * 
	 * @param workbook
	 * @return
	 */
	public static List<HashMap<String, String>> parseExcel(Workbook workbook) {
		List<HashMap<String, String>> lst = new ArrayList<HashMap<String, String>>();
		for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
			Sheet sheet = workbook.getSheetAt(numSheet);
			if (sheet == null) {
				continue;
			}
			// 循环行Row
			for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum);
				if (row == null) {
					continue;
				}
				HashMap<String, String> hp = new HashMap<String, String>();
				for (int j = 0; j < row.getLastCellNum(); j++) {
					hp.put(ToNumberSystem26(j + 1), row.getCell(j) == null ? null : row.getCell(j).toString());
				}
				lst.add(hp);
			}
		}
		return lst;
	}

	/**
	 * 26进制 [1-26] [A-Z] 把1-26使用A-Z来表示
	 * 
	 * @param n
	 * @return
	 */
	private static String ToNumberSystem26(int n) {
		String s = "";
		while (n > 0) {
			int m = n % 26;
			if (m == 0) {
				m = 26;
			}
			s = (char) (m + 64) + s;
			n = (n - m) / 26;
		}
		return s;
	}

	/**
	 * 获取07版本以后的excel数据
	 * 
	 * @param sheet
	 * @param workbook
	 * @return
	 */
	@Deprecated
	public static List<HashMap<String, String>> getSheetLaterOn07(Workbook book) {
		XSSFWorkbook workbook = (XSSFWorkbook) book;
		List<HashMap<String, String>> lst = new ArrayList<HashMap<String, String>>();
		for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = workbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// 循环行Row
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow == null) {
					continue;
				}
				HashMap<String, String> hp = new HashMap<String, String>();
				for (int j = 0; j < xssfRow.getLastCellNum(); j++) {
					hp.put("E" + j, xssfRow.getCell(j).toString());
				}
				lst.add(hp);
			}
		}
		return lst;
	}

	/**
	 * 获取07版本以前的excel数据
	 * 
	 * @param sheet
	 * @param workbook
	 * @return
	 */
	@Deprecated
	public static List<HashMap<String, String>> getSheetBefore07(Workbook book) {
		HSSFWorkbook workbook = (HSSFWorkbook) book;
		List<HashMap<String, String>> lst = new ArrayList<HashMap<String, String>>();
		for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = workbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			// 循环行Row
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				HashMap<String, String> hp = new HashMap<String, String>();
				for (int j = 0; j < hssfRow.getLastCellNum(); j++) {
					hp.put("E" + j, hssfRow.getCell(j).toString());
				}
				lst.add(hp);
			}
		}
		return lst;
	}

}
