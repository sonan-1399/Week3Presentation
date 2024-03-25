package com.test.windowsframe;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.test.utils.DriverUtils;

public class AutomationDemoSite {

	static WebDriver driver;
	static Map<String, String> excelValues = new HashMap<>();
	static DriverUtils util;

	@BeforeTest
	private static void openApplication() {
		util = new DriverUtils(driver);
		util.detailsToOpenApp("https://demo.automationtesting.in/Index.html");
	}

	@Test
	public static void scenario2() throws IOException{
		readExcelValues();
		skipSignIn();
		selectSwitchTo();
		swtParentFrame();
		txtInFrame();
		swtDefaultContent();
		selectWidgetsAndDate();
	}

	private static void readExcelValues() throws IOException {
		FileInputStream fs = new FileInputStream("C:/Users/snthadev/MiniProjectData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fs);
		XSSFSheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			String element = row.getCell(0).getStringCellValue();
			Cell valueCell = row.getCell(1);
			excelValues.put(element, getStringCellValue(valueCell));
		}
		workbook.close();
	}

	private static String getStringCellValue(Cell cell) {
		if (cell == null) {
			return null;
		} else if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue();
		} else if (cell.getCellType() == CellType.NUMERIC) {
			// here converting numeric value to string
			return String.valueOf(cell.getNumericCellValue());
		} else {
			return null;
		}
	}

	private static void skipSignIn() {
		util.click(excelValues.get("skipSignIn"));
	}

	public static void selectSwitchTo() {
		util.actions(excelValues.get("switchTo"), excelValues.get("frames"));
	}

	public static void swtParentFrame() {
		driver.switchTo().parentFrame();
	}

	public static void txtInFrame() {
		util.click(excelValues.get("withInIframe"));
		util.switchToFrame(excelValues.get("parentFrame"));
		util.switchToFrame(excelValues.get("childFrame"));
		util.sendKeys(excelValues.get("textBox"), excelValues.get("textBox_Value"));
		System.out.println("Text has been inputted");
	}

	public static void swtDefaultContent() {
		driver.switchTo().defaultContent();
	}

	public static void selectWidgetsAndDate() {
		util.actions(excelValues.get("widgets"), excelValues.get("datePicker"));
		util.click(excelValues.get("pickDate"));
		util.select(excelValues.get("month"), excelValues.get("month_Value"));
		util.click(excelValues.get("date"));
		System.out.println("Date has been selected");
	}

	@AfterTest
	public static void closeApplication() {
		driver.quit();
		System.out.println("Application closed");
	}
}
