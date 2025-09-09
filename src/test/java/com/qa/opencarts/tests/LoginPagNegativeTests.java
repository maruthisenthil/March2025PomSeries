package com.qa.opencarts.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class LoginPagNegativeTests extends BaseTest {
	
	
	
	@DataProvider
	public Object[][] getNegativeLoginData() {
		return new Object[][] {
			{"test@Karasai.com", "Test1234"},
			{"senthil_qa@Karasai.com", "Test12345"},
			{"senthil_qa@@Karasai.com", "Test1234"},
			{"", "Test1234s"},
			{"", ""},
		};
	}
	
	
	@Test(dataProvider="getNegativeLoginData")
	public void negativeLoginTest(String invalidUserName, String invalidPwd) {
		
		Assert.assertTrue(loginPage.doLoginWithInvalidCredentials(invalidUserName, invalidPwd));
		
	}

}
