
package com.zehin.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	/**
	 * Description :读取excel文件
	 * @param filePath
	 * @return list
	 * @throws IOException
	 */
	public static List<String[]> readExcel(String filePath) {
		List<String[]> returnList = new ArrayList<String[]>();
		try {
			File file = new File(filePath);
			// 扩展名为空时，
			if (filePath.equals("")) {
				throw new IOException("文件路径不能为空！");
			} else {
				if (!file.exists()) {
					throw new IOException("文件不存在！");
				}
			}
			System.out.println("====判断文件类别=====");
			if (isExcel2003(new FileInputStream(file))) {// 2003
				System.out.println("====准备读取xls文件");
				returnList = readExcel_xls(filePath);
			} else if (isExcel2007(filePath)) {// 2007
				System.out.println("====准备读取xlsx文件");
				returnList = readExcel_xlsx(filePath);
			} else {
				throw new IOException("====读取失败！！请您确保您的文件是Excel文件，并且无损，然后再试。");
			}
			System.out.println("====execl文件读取完毕=====");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnList;

	}

	/**
	 * Description :读取xlsx格式的文件
	 * @param filePath
	 * @return
	 */
	private static List<String[]> readExcel_xlsx(String filePath) {

		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String[]> returnlist = new ArrayList<String[]>();

		Sheet sheet = null;
		sheet = wb.getSheetAt(0);

		// 获取最后行号
		int lastRowNum = sheet.getLastRowNum();

		if (lastRowNum > 0) { // 如果>0，表示有数据
			System.out.println("\n开始读取名为【" + sheet.getSheetName() + "】的内容：");
		}
		Row row = null;
		// 循环读取
		String ss[] = null;
		for (int i = 0; i <= lastRowNum; i++) {
			row = sheet.getRow(i);
			if (row != null) {
				// rowList.add(row);
				ss = new String[row.getLastCellNum()];
				System.out.print("第" + (i + 1) + "行：");
				// 获取每一单元格的值
				for (int j = 0; j < row.getLastCellNum(); j++) {
					String value = getCellValue(row.getCell(j));
					ss[j] = value;
					if (!value.equals("")) {
						System.out.print(value + " | ");
					}
				}
				System.out.println("");
				returnlist.add(ss);
			}
		}
		// ======================
		return returnlist;
	}

	/**
	 * Description :读取xls格式文件
	 * @param filePath
	 * @return list
	 */
	private static List<String[]> readExcel_xls(String filePath) {

		HSSFWorkbook wb = null;

		try {
			wb = new HSSFWorkbook(new FileInputStream(new File(filePath)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String[]> returnlist = new ArrayList<String[]>();

		HSSFSheet sheet = null;
		sheet = wb.getSheetAt(0);
		// 获取最后行号
		int lastRowNum = sheet.getLastRowNum();

		if (lastRowNum > 0) { // 如果>0，表示有数据
			System.out.println("\n====开始读取xls的内容：");
		}
		HSSFRow row = null;
		// 循环读取
		String ss[] = null;
		for (int i = 0; i <= lastRowNum; i++) {
			row = sheet.getRow(i);
			if (row != null) {
				ss = new String[row.getLastCellNum()];
				System.out.print("第" + (i + 1) + "行：");
				// 获取每一单元格的值
				for (short j = 0; j < row.getLastCellNum(); j++) {
					String value = getXlsCellValue(row.getCell(j));
					ss[j] = value;
					if (!value.equals("")) {
						System.out.print(value + " | ");
					}
				}
				System.out.println("");
				returnlist.add(ss);
			}
		}
		// ======================
		return returnlist;

	}

	/**
	 * Description :获取xlsx格式的单元格值
	 * @param cell
	 * @return String
	 */
	private static String getCellValue(Cell cell) {
		Object result = "";

		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				result = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				result = cell.getNumericCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				result = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_FORMULA:
				result = cell.getCellFormula();
				break;
			case Cell.CELL_TYPE_ERROR:
				result = cell.getErrorCellValue();
				break;
			case Cell.CELL_TYPE_BLANK:
				break;
			default:
				break;
			}
		}

		return result.toString();
	}

	/**
	 * Description :获取xls格式的cell值
	 * @param hssfCell
	 * @return String
	 */
	private static String getXlsCellValue(HSSFCell hssfCell) {
		Object result = "";
		if (hssfCell != null) {
			switch (hssfCell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				result = hssfCell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				result = hssfCell.getNumericCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				result = hssfCell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_FORMULA:
				result = hssfCell.getCellFormula();
				break;
			case Cell.CELL_TYPE_ERROR:
				result = hssfCell.getErrorCellValue();
				break;
			case Cell.CELL_TYPE_BLANK:
				break;
			default:
				break;
			}
		}

		return result.toString();
	}

	/**
	 * Description :判断是否为excel版本2003
	 * @param is
	 * @return
	 */
	public static boolean isExcel2003(InputStream is) {
		try {
			new HSSFWorkbook(is);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Description :判断是否为excel版本2007
	 * @param is
	 * @return
	 */
	public static boolean isExcel2007(String pathh) {
		try {
			XSSFWorkbook xx = new XSSFWorkbook(pathh);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
