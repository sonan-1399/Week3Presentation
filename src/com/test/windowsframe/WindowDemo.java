package com.test.windowsframe;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WindowDemo {
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "C:/Users/snthadev/Documents/chromedriver.exe");

		WebDriver driver = new ChromeDriver();
		driver.get("https://www.amazon.in/");
		driver.navigate().refresh();
		driver.manage().window().maximize();
		driver.navigate().refresh();

		WebElement Search = driver.findElement(By.xpath("//input[@placeholder='Search Amazon.in']"));
		Search.clear();
		Search.sendKeys("Headphones");
		Search.sendKeys(Keys.ENTER);


		driver.findElement(By.xpath("(//img[@class='s-image'])[1]")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		String mainWindow=driver.getWindowHandle();
		System.out.println(mainWindow);
		Set<String> set =driver.getWindowHandles();
		System.out.println(set);

		Iterator<String> itr= set.iterator();
		while(itr.hasNext()){
			String childWindow=itr.next();
			if(!mainWindow.equals(childWindow)){
				driver.switchTo().window(childWindow);
				System.out.println(driver.switchTo().window(childWindow).getTitle());
				driver.close();
			}
		}
		driver.switchTo().window(mainWindow);
		
		driver.findElement(By.xpath("//input[@type='checkbox']//following::span[text()='boAt']")).click();
		driver.quit();
		
	}

}
