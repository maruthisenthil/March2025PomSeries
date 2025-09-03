package com.qa.opencart.listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Attachment;

public class TestAllureListeners implements ITestListener {

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	// Text attachments for Allure
	@Attachment(value = "Page screenshot", type = "image/png")
	public byte[] saveScreenshotPNG(WebDriver driver) {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	// Text attachments for Allure
	@Attachment(value = "{0}", type = "text/plain")
	public static String saveTextLog(String message) {
		return message;
	}

	// HTML attachments for Allure
	@Attachment(value = "{0}", type = "text/html")
	public static String attachHtml(String html) {
		return html;
	}
	// Invoked before running all the test methods belonging to the classes inside the <test> tag
	// and calling all their Configuration methods.
	@Override  
	public void onStart(ITestContext iTestContext) {
		System.out.println("I am in onStart method " + iTestContext.getName());
		//iTestContext.setAttribute("WebDriver", BasePage.getDriver());
	}

	// Invoked after all the test methods belonging to the classes inside the <test> tag have run 
	// and all their Configuration methods have been called.
	@Override
	public void onFinish(ITestContext iTestContext) {
		System.out.println("I am in onFinish method " + iTestContext.getName());
	}

	// Invoked each time before a test will be invoked. 
	// The ITestResult is only partially filled with the references to class, method, start millis and status.
	@Override
	public void onTestStart(ITestResult iTestResult) {
		System.out.println("I am in onTestStart method " + getTestMethodName(iTestResult) + " start");
	}

	
	// Invoked each time a test succeeds.
	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
	}

	// Invoked each time a test fails.
	@Override
	public void onTestFailure(ITestResult iTestResult) {
		System.out.println("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");
		Object testClass = iTestResult.getInstance();

		// Allure ScreenShotRobot and SaveTestLog
		if (DriverFactory.getDriver() instanceof WebDriver) {
			System.out.println("Screenshot captured for test case:" + getTestMethodName(iTestResult));
			saveScreenshotPNG(DriverFactory.getDriver());
		}
		// Save a log on allure.
		saveTextLog(getTestMethodName(iTestResult) + " failed and screenshot taken!");		
	}
	
	// Invoked each time a test is skipped.
	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
	}

	// Invoked each time a method fails but has been annotated with successPercentage and 
	// this failure still keeps it within the success percentage requested.
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
	}
	
}
