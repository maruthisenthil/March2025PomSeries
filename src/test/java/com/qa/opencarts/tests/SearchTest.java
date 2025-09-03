package com.qa.opencarts.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic-105 : Design the Open Cart App - Search Results Page")
@Feature("F-105 : Design open cart User Search Results page From AccountsPage")
@Story("US-53: Develop new Search results page : Search reslts page checks ")
public class SearchTest extends BaseTest {
	// BeforeTest (BaseTest) executes first then -- > BeforeClass(AccountsPageTest) - this is the sequence in TestNg
	// BT --> BC --> @Test
	
	@BeforeClass
	public void searchSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	
	@DataProvider
	public Object[][] getProducts() {
		
		return new Object[][] {
			{"macbook","MacBook Pro"},
			{"samsung","Samsung SyncMaster 941BW"},
			{"imac","iMac"},
			{"canon","Canon EOS 5D"}
		};
	}
	
	@Description("SearchResults Page : Search a product from the search bar using DataProvider - getProducts()")
	@Owner("Senthil")
	@Severity(SeverityLevel.CRITICAL)
	@Test(dataProvider="getProducts")
	public void searchProductTest(String searchKey, String productname) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage =searchResultsPage.selectProduct(productname);
		String actualHeader = productInfoPage.getProductHeader();
		ChainTestListener.log("SearchTest - searchProductTest()- Using DataProvider :==> getProducts()");
		ChainTestListener.log("SearchTest - searchProductTest() - Actual Header Verification : ActualHeader : - "+actualHeader);
		Assert.assertEquals(actualHeader, productname);
		
	}
	
	
}
