package com.qa.opencarts.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class CommonPageTest extends BaseTest{
	
	@Test
	public void checkCommonEleentsOnLoginPageTest() {
		
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(commonsPage.isLogoExist());
		softAssert.assertTrue(commonsPage.isSearchFieldExsit());
		List<String> footerList = commonsPage.footerLinkExist();
		
		softAssert.assertEquals(footerList.size(), AppConstants.DEFAULT_FOTTER_COUTN );
		softAssert.assertAll();
		
	}
	
	
	@Test
	public void checkCommonEleentsOnAccountsPageTest() {
		
		loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(commonsPage.isLogoExist());
		softAssert.assertTrue(commonsPage.isSearchFieldExsit());
		List<String> footerList = commonsPage.footerLinkExist();
		
		softAssert.assertEquals(footerList.size(), AppConstants.DEFAULT_FOTTER_COUTN );
		softAssert.assertAll();
		
	}
}
