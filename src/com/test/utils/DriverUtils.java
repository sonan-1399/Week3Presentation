package com.test.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverUtils {
private WebDriver driver;
	
	public DriverUtils(WebDriver driver) {		
		this.driver=driver;
	}
	
	public void click(String xpath){	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		By by = By.xpath(xpath);
		wait.until(ExpectedConditions.elementToBeClickable(by));
		driver.findElement(by).click();
	}

	public void select(String xpath , String Option){
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		By by = By.xpath(xpath);
		new Select(driver.findElement(by)).selectByVisibleText(Option);
	}

	public void sendKeys(String xpath , String input){
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		By by = By.xpath(xpath);
		wait.until(ExpectedConditions.elementToBeClickable(by));
		driver.findElement(by).clear();
		driver.findElement(by).sendKeys(input);
	}

	public String getText(String xpath){
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
		By by=By.xpath(xpath);
		wait.until(ExpectedConditions.elementToBeClickable(by));
		return driver.findElement(by).getText();
	}

	public void actions(String xpath , String xpath1) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
		By by=By.xpath(xpath);
		WebElement element = driver.findElement(by);
		By by1 = By.xpath(xpath1);
		WebElement element1 = driver.findElement(by1);
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
		actions.moveToElement(element1).click().build().perform();		
	}

	public void switchToFrame(String xpath) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
		By by=By.xpath(xpath);
		WebElement element = driver.findElement(by);
		driver.switchTo().frame(element);
	}
	
	public WebDriver detailsToOpenApp(String url) {
		System.setProperty("webdriver.chrome.exe", "C:/Users/snthadev/Documents/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get(url);
		driver.manage().window().maximize();
		System.out.println("Application Opened");
		return driver;		
	}
	
	public void closeApp(){
		driver.quit();
		System.out.println("Application is closed");
	}
}
