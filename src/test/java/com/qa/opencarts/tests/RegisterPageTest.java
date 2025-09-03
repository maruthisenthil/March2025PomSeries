package com.qa.opencarts.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.CsvUtil;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtils;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic-104 : Design the Open Cart App - User REgister Page")
@Feature("F-104 : Design open cart User Register page From AccountsPage")
@Story("US-53: Develop new User Register page tests for : Check new registration works perfectly. ")
public class RegisterPageTest extends BaseTest{
	
	// BT (chrome browser + login Url) --> BC(move to register page) --> @Test
	
	@BeforeClass
	public void goToRegisterPage() {
		registerPage = loginPage.navigateToRegisterPage();
		
	}
	
	@DataProvider
	public Object[][] getRegisterData() {
		return new Object[][] {
			{"Senthil","SBR","9874123412","Test@123","yes"}
		};
	}
	
	@DataProvider
	public Object[][] getUserRegTestData() {
		return ExcelUtil.getUserRegisterTestDate("register");
	}
	
	@DataProvider
	public Object[][] getRegisterCSVData(){
		return CsvUtil.csvData("register");
	}
	
	
	@Description("RegisterPage : Register new user with unique data ")
	@Owner("Senthil")
	@Severity(SeverityLevel.CRITICAL)
	@Test(dataProvider = "getRegisterCSVData")
	public void registerTest(String firstName, String lastName, String telephoneNo, String password, String subscribe) {
		ChainTestListener.log("RegisterPageTest - registerTest()- Using DataProvider :==> getRegisterData()");
		Assert.assertTrue(registerPage.userREgister(firstName, lastName, StringUtils.getRandomEmail(), telephoneNo, password, subscribe));
	}
	
	
}
