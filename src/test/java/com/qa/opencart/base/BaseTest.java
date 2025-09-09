package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.CommonsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchResultsPage;
import com.qa.opencart.pages.ShoppingCartPage;

import io.qameta.allure.Description;

//@Listeners({ChainTestListener.class, TestAllureListeners.class})
public class BaseTest {
	
	protected WebDriver driver;
	protected Properties prop;
	DriverFactory df;
	protected LoginPage loginPage;
	protected AccountsPage accPage;
	protected SearchResultsPage searchResultsPage;
	protected ProductInfoPage  productInfoPage;
	protected ShoppingCartPage shoppingCartPage;
	protected RegisterPage registerPage;
	protected CommonsPage  commonsPage;
	
	@Description("Launch the Browser: {0} and url")
	@Parameters({"browser"})
	@BeforeTest
	public void setUp(@Optional("chrome") String browserName) {
		System.out.println("setUp() - BaseTest");
		df = new DriverFactory();
		prop = df.initProp();
		if(browserName != null) {
			prop.setProperty("browser", browserName);
		}
		driver = df.initDriver(prop); // this is threadlocal driver
		loginPage = new LoginPage(driver);
		commonsPage = new CommonsPage(driver);
	}
	
	@AfterMethod // will run after each @Test method 
	public void attachScreenshot(ITestResult result) {
		if(!result.isSuccess()) { // only for failure test cases -- true .. ie 
			ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
		}
//		ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
	}
	
	@Description("closing the browser .. ")
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
