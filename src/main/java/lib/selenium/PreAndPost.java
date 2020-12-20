package lib.selenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;
import lib.utils.DataInputProvider;

public class PreAndPost extends WebDriverServiceImpl{
	
	public String dataSheetName;		
	
	@BeforeSuite
	public void beforeSuite() {
		startReport();
	}
	
	@BeforeClass
	public void beforeClass() {
		startTestCase(testCaseName, testDescription);		
	}
	
	@BeforeMethod
	public void beforeMethod() throws MalformedURLException {
		//for reports		
		startTestModule(nodes);//each data row -> one testcase
		test.assignAuthor(authors);
		test.assignCategory(category);
		
		WebDriverManager.chromedriver().setup();
		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--disable-notifications");
		webdriver = new ChromeDriver(ops);
		/*
		webdriver = new ChromeDriver(ops);
		
		//Grid
		
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setBrowserName("chrome");
		dc.setPlatform(Platform.WINDOWS);
		
		WebDriverManager.chromedriver().setup();
		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--disable-notifications");
		webdriver = new RemoteWebDriver(new URL("http://192.168.1.102:4444/wd/hub"),ops);
		*/
		driver = new EventFiringWebDriver(webdriver);
		driver.register(this);
		driver.manage().window().maximize();
		driver.get(prop.getProperty("URL"));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	
	}

	@AfterMethod
	public void afterMethod() {
//		closeAllBrowsers();	
		}

	@AfterSuite
	public void afterSuite() {
		endResult();//write report
	}

	@DataProvider(name="fetchData", indices = 0)
	public  Object[][] getData(){
		return DataInputProvider.getSheet(dataSheetName);		
	}
	
}
