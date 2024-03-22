package com.test.readdata;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.test.utils.DriverUtils;

public class ReadDataFromJson {

	WebDriver driver;
	DriverUtils util;

	@BeforeTest
	public WebDriver openApplication() {
		System.setProperty("webdriver.chrome.exe", "C:/Users/snthadev/Documents/chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get("https://parabank.parasoft.com/parabank/index.htm");
		driver.manage().window().maximize();
		util=new DriverUtils(driver);
		System.out.println("Application Opened");
		return driver;
	}
	
	@Test(dataProvider="dp")
	public void CustomerLogin(String data) throws IOException, ParseException {
		
		String users[]=data.split(",");
		
		WebElement userId = driver.findElement(By.xpath("//input[@name='username']"));
		userId.clear();
		userId.sendKeys(users[0]);
		
		WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
		password.clear();
		password.sendKeys(users[1]);
		
		WebElement login = driver.findElement(By.xpath("//input[@value='Log In']"));
		login.click();
		
	}
	
	@DataProvider(name="dp")
	public String[] ReadJson() throws IOException, ParseException {
		JSONParser jsonparser = new JSONParser();
		FileReader reader = new FileReader("C:/Users/snthadev/credentialsdata.json");
		Object obj=jsonparser.parse(reader);
		//JSONObject empjsonobj=(JSONObject)obj;
		
		JSONObject userloginsJsonobj=(JSONObject)obj;
		JSONArray userloginsArray=(JSONArray)userloginsJsonobj.get("userlogins");
		String arr[]=new String[userloginsArray.size()];
		for (int i=0; i<userloginsArray.size();i++)
		{
			 JSONObject users=(JSONObject) userloginsArray.get(i);
			 String user=(String)users.get("user1");
			 String pwd=(String)users.get("password");
			 arr[i]=user+"," +pwd ;
		}
		return arr;	
	}

	@AfterTest
	public void teardown() {
		driver.quit();
	}
}
