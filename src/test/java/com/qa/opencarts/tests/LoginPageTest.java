package com.qa.opencarts.tests;


import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic-101 : Design the Open Cart App Login page")
@Feature("F-101 : design open cart LoginFeature")
@Story("US-50: Develop login core features: title, url, user is able to login")
public class LoginPageTest extends BaseTest{
	
	@Description("LoginPage : Title test")
	@Owner("Senthil")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageTitleTest() {
		String actualTitle =  loginPage.getLoginPageTitle();
		ChainTestListener.log("login page Title :  "+actualTitle);
		Assert.assertEquals(actualTitle,AppConstants.LOGIN_PAGE_TITLE);
	}
	
	@Description("LoginPage : URL test")
	@Owner("Senthil")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void loginPageUrlTest() {
		String actualURL = loginPage.getLoginPageURL();
		ChainTestListener.log("login page URL :  "+actualURL);
		Assert.assertTrue(actualURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL));
	}
	
	@Description("LoginPage : ForgotPassword Link exist test")
	@Owner("Senthil")
	@Severity(SeverityLevel.CRITICAL)
	@Test   
	public void isForgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	@Description("LoginPage : PageHeader exist test ..")
	@Owner("Senthil")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void isLoginPageHeaderExistTest() {
		Assert.assertTrue(loginPage.isHeaderExist());
	}
	
	@Description("LoginPage : User is able to login to app ...")
	@Owner("Senthil")
	@Severity(SeverityLevel.BLOCKER)
	@Test
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
}