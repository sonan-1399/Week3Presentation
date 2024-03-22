package com.test.readdata;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.test.utils.DriverUtils;

public class ReadDataFromExcel {

	static WebDriver driver;
	static DriverUtils util;	
	static String adminPageTab;
	static String soapEndPoint;
	static String soapValue;
	static String restEndPoint;
	static String restValue;
	static String endPoint;
	static String endPointValue;
	static String dataAccess_Soap;
	static String initBalance;
	static String initBalance_Amt;
	static String minBalance;
	static String minBalance_Amt;
	static String loanProvider;
	static String loanProvider_JMS;
	static String loanProcessor;
	static String loanProcessor_Combined;
	static String submit;
	static String successMsg;
	static String expectedMsg;
	static Map<String, String> excelValues = new HashMap<>();

	@BeforeTest
	private static WebDriver openApplication() {
		System.setProperty("webdriver.chrome.exe", "C:/Users/snthadev/Documents/chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get("https://parabank.parasoft.com/parabank/index.htm");
		driver.manage().window().maximize();
		util=new DriverUtils(driver);
		System.out.println("Application Opened");
		return driver;
	}

	@Test
	public void testParaBankFlow() throws Exception {		
		readExcelValues();
		openAdminPage();
		selectDataAccessMode();
		fillWebServiceDetails();
		fillApplicationSettings();
		submitData();
		closeApplication();
	}

	private static void readExcelValues() throws IOException {
		FileInputStream fs = new FileInputStream("C:/Users/snthadev/ParaBankDataSheet.xlsx");
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
			return String.valueOf(cell.getNumericCellValue());
		} else {
			return null;
		}
	}

	private static void openAdminPage() throws Exception{
		util.Click(excelValues.get("adminPageTab"));
	}

	private static void selectDataAccessMode() {
		util.Click(excelValues.get("dataAccess_Soap"));
	}

	private static void fillWebServiceDetails() {
		util.SendKeys(excelValues.get("soapEndPoint"),excelValues.get("soapValue"));
		util.SendKeys(excelValues.get("restEndPoint"), excelValues.get("restValue"));
		util.SendKeys(excelValues.get("endPoint"), excelValues.get("endPointValue"));
	}

	private static void fillApplicationSettings() {
		util.SendKeys(excelValues.get("initBalance"), excelValues.get("initBalance_Amt"));
		util.SendKeys(excelValues.get("minBalance"), excelValues.get("minBalance_Amt"));
		util.Select(excelValues.get("loanProvider"), excelValues.get("loanProvider_JMS"));
		util.Select(excelValues.get("loanProcessor"), excelValues.get("loanProcessor_Combined"));	
	}

	private static void submitData() {
		util.Click(excelValues.get("submit"));
		System.out.println(util.GetText(excelValues.get("successMsg")));
		Assert.assertEquals(excelValues.get("expectedMsg"),util.GetText(excelValues.get("successMsg")), "Admin settings are not saved successfully");	
	}

	@AfterTest
	private static void closeApplication() {
		driver.quit();
		System.out.println("Application closed");
	}

}
