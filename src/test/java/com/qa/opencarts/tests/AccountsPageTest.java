package com.qa.opencarts.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
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

@Epic("Epic-102 : Design the Open Cart App - AccountsPage")
@Feature("F-102 : design open cart Logged in User Accounts Feature")
@Story("US-51: Develop Accounts page tests for : Logout links existance and headers in the page ")
public class AccountsPageTest extends BaseTest {
	// BeforeTest (BaseTest) executes first then -- > BeforeClass(AccountsPageTest) - this is the sequence in TestNg
	// BT --> BC --> @Test
	@BeforeClass
	public void accPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

//	@Test(invocationCount = 5)
	@Description("AccountsPage : LogoutLink exists test")
	@Owner("Senthil")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
		ChainTestListener.log("AccountsPageTest -  : isLogoutLinkExistTest() :=> "+accPage.isLogoutLinkExist());
	}
	
	@Description("AccountsPage : PageHeader exists test")
	@Owner("Senthil")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void accPageHeadersTest() {
		List<String> actualHeadersList = accPage.getAccPageHeaders();
		ChainTestListener.log("AccountsPageTest -  : accPageHeadersTest().size :=> "+AppConstants.ACC_PAGE_HEADERS_COUNT);
		Assert.assertEquals(actualHeadersList.size(), AppConstants.ACC_PAGE_HEADERS_COUNT);
		ChainTestListener.log("AccountsPageTest -  : accPageHeadersList() :=> "+AppConstants.expectedAccPageHeadersList);
		Assert.assertEquals(actualHeadersList, AppConstants.expectedAccPageHeadersList);
	}
}
