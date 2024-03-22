package com.test.advanced.selenium;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class SelectActionDemo {
	
	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.exe", "C:/Users/snthadev/Documents/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://www.amazon.in/");
		driver.manage().window().maximize();
		
		WebElement category =  driver.findElement(By.xpath("//select[@id='searchDropdownBox']"));
		
		//selecting the audible audiobooks category from the categories
		Select prdtType = new Select(category);
		prdtType.selectByVisibleText("Audible Audiobooks");
		
		WebElement search = driver.findElement(By.xpath("//input[@placeholder='Search Amazon.in']"));
		search.clear();
		search.sendKeys("Ikigai");
		
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//span[text()='Results']"), "Results"));

		WebElement accountList = driver.findElement(By.xpath("//a[@id='nav-link-accountList']"));
		WebElement sigIn = driver.findElement(By.xpath("//span[text()='Sign in']"));
		
		//Mouse hover over the Accounts & lists and click on the sign in button
		Actions actions=new Actions(driver);
		actions.moveToElement(accountList).perform();
		actions.moveToElement(sigIn).click().build().perform();
		
		WebElement userId = driver.findElement(By.xpath("//input[@name='email']"));
		userId.clear();
		userId.sendKeys("abc@gmail.com");
		
		WebElement continueButton = driver.findElement(By.xpath("//input[@id='continue']"));
		//clicking the continue button using Javascript executor 
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", continueButton);
		
		WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
		password.clear();
		password.sendKeys("Password#123");
		
		WebElement signInButton = driver.findElement(By.xpath("//input[@type='submit']"));
		js.executeScript("arguments[0].click();", signInButton);
		
		String actualMsg = driver.findElement(By.xpath("//span[@class='a-list-item']")).getText();
		Assert.assertEquals(actualMsg, "Your password is incorrect");
		
		driver.quit();
		
	}

}
