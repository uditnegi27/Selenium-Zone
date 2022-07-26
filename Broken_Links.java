package Selenium_Concepts;

import java.io.IOException;
import java.net.HttpURLConnection;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class Broken_Links {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws  InterruptedException, IOException {
		
		System.setProperty("webdriver.chrome.driver", "/Users/honasa/Downloads/chromedriver");
		WebDriver driver = new ChromeDriver();
		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		
		driver.get("https://mamaearth.in/");
		
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Thread.sleep(5000);
		
		List<WebElement> list = driver.findElements(By.tagName("a"));
		List<String> wu = new ArrayList<>();
		
		for(int i=0; i<list.size(); i++) {
			WebElement webelement = list.get(i);
			String url = webelement.getAttribute("href");
			try {
				if(!url.equals("null")) {
					wu.add(url);
				}
				
			}
			catch(NullPointerException e) {
			}
		}
		
		for(int i=0; i<10; i++) {
			
			URL link = new URL(wu.get(i));
			HttpURLConnection httpCon = (HttpURLConnection) link.openConnection();
			httpCon.connect();
			int response = httpCon.getResponseCode();
			httpCon.disconnect();
			if(response >= 400) {
				System.out.println(wu.get(i)+" -------- is broken");
			}
			else {
				System.out.println("all okay");
			}
		}
		
		driver.quit();
	}

}
