package cloud.liyan.emailmanager.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author liyan
 * @description
 * @date 2019-09-07 14:15
 */
public class ReadExcelUtil {
	private static final Logger logger = LoggerFactory.getLogger(ReadExcelUtil.class);
	/**
	 * 解析excel，读取数据
	 * 
	 * @param fileStream
	 * @return
	 */
	public static List<Map<String, String>> analysisExcel(InputStream fileStream, boolean isXlsx) {
		Sheet sheet = null;
		Row row = null;
		List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
		try {
			// 解析excel
			Workbook wb = readExcel(fileStream, isXlsx);
			if (wb != null) {
				sheet = wb.getSheetAt(0);
				int rownum = sheet.getPhysicalNumberOfRows();
				// 从第二行开始遍历
				for (int i = 1; i < rownum; i++) {
					row = sheet.getRow(i);
					if (row != null) {
						// 获得当前行的开始列
						int firstCellNum = row.getFirstCellNum();
						// 获得当前行的列数,为空列不获取
						int lastCellNum = row.getPhysicalNumberOfCells();
						Map<String, String> tempMap = new HashMap<String, String>();
						for (int num = firstCellNum; num <= lastCellNum; num++) {
							Cell cell = row.getCell(num);
							tempMap.put(String.valueOf(num), getCellValue(cell));
						}
						lists.add(tempMap);
					}
				}
			}
		} catch (Exception e) {
			logger.error("excel解析异常", e);
		}
		return lists;
	}

	public static Workbook readExcel(InputStream fileStream, boolean isXlsx) {
		try {
			if (isXlsx) {
				return new XSSFWorkbook(fileStream);
			} else {
				return new HSSFWorkbook(fileStream);
			}
		} catch (IOException e) {
			logger.error("excel解析异常", e);
		}
		return null;
	}

	public static String getCellValue(Cell cell) {
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}
		// 把数字当成String来读，避免出现1读成1.0的情况
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
		// 判断数据的类型
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC: // 数字
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING: // 字符串
			cellValue = String.valueOf(cell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN: // Boolean
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_BLANK: // 空值
			cellValue = "";
			break;
		default:
			cellValue = "未知类型";
			break;
		}
		return cellValue;
	}
}
