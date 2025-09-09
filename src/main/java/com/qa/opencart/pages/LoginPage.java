package com.qa.opencart.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	// every page will have private driver, element util
	private WebDriver driver;
	private ElementUtil eleUtil;

	// private By locators: LoginPage Page elements
	private final By emailID = By.id("input-email");
	private final By password = By.id("input-password");
	private final By loginBtn = By.xpath("//input[@value='Login']");
	private final By forgotPwdLink = By.linkText("Forgotten Password");
	private final By header = By.tagName("h2");
	private final By registerLink = By.linkText("Register");
	private final By loginErrorMsg = By.cssSelector("div.alert.alert-danger.alert-dismissible");
	
	public static final Logger log = LogManager.getLogger(LoginPage.class);
	
	// public constructors
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	

	// public page methods/actions
	@Step("Getting the login page title")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_SHORT_WAIT);
		// System.out.println("login Page Title: "+title);
		log.info("Login page Title :"+ title);
		return title;
	}
	
	@Step("Getting the login url")
	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_FRACTION_URL, AppConstants.DEFAULT_SHORT_WAIT);
		System.out.println("login Page URL: "+url);
		log.info("login page URL :"+ url);
		return url;
	}
	
	@Step("Forgotpassword link exist ..")
	public boolean isForgotPwdLinkExist() {
		log.info("isForgotPwdLinkExist : "+ eleUtil.isElementDisplayed(forgotPwdLink));
		return eleUtil.isElementDisplayed(forgotPwdLink);
	}
	
	@Step("Page header exists ...")
	public boolean isHeaderExist() {
		log.info("isHeaderExist() : "+eleUtil.isElementDisplayed(header));
		return eleUtil.isElementDisplayed(header);
	}
	
	// Page Chaning 
	@Step("Login with user name: {0} and password")
	public AccountsPage doLogin(String username, String pwd) {
		System.out.println("Application credentials: "+username +" : "+"*********");
		log.info("Application Credentials : username: "+ username +" - password: "+ "**************");
		eleUtil.waitForElementVisible(emailID, AppConstants.DEFAULT_MEDIUM_WAIT).sendKeys(username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);
	}
	
	@Step("Login with incorect username: {0} , and password : {1}")
	public boolean doLoginWithInvalidCredentials(String invlidUserName, String inValidPwd) {
		log.info("Invalid application Credentials : username: "+ invlidUserName +" - password: "+ inValidPwd);
		
		WebElement emailEle = eleUtil.waitForElementVisible(emailID, AppConstants.DEFAULT_MEDIUM_WAIT);
		emailEle.clear();
		eleUtil.waitForElementVisible(emailID, AppConstants.DEFAULT_MEDIUM_WAIT).sendKeys(invlidUserName);
		eleUtil.doSendKeys(password, inValidPwd);
		eleUtil.doClick(loginBtn);
		String errorMsg = eleUtil.doElementGetText(loginErrorMsg);
		
		log.info("Invalid crednetials error message : "+errorMsg);
		
		if(errorMsg.contains(AppConstants.LOGIN_INVALID_CREDS_MSG)){
			return true;
		}else if(errorMsg.contains(AppConstants.LOGIN_BLANK_CREDS_MSG)) {
			return true;
		}
		return false;
	}
	
	@Step("navigating to the register page .. ")
	public RegisterPage navigateToRegisterPage() {
		log.info("Trying to navigate to the Register page ");
		eleUtil.waitForElementVisible(registerLink, AppConstants.DEFAULT_SHORT_WAIT).click();
		return new RegisterPage(driver);
	}

}
