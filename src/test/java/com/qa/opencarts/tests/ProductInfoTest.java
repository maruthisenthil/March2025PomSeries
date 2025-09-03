package com.qa.opencarts.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.CsvUtil;
import com.qa.opencart.utils.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic-103 : Design the Open Cart App - ProductInfo Page")
@Feature("F-103 : Design open cart ProdcutInfo page After AccountsPage")
@Story("US-52: Develop ProductInfo page : To verify Headers, Images and Prodcut Info additionaly check AddtoCart and GoTo Shopping works fine ")
public class ProductInfoTest extends BaseTest{
	
	// BeforeTest (BaseTest) executes first then -- > BeforeClass(AccountsPageTest) - this is the sequence in TestNg
	// BT(launch browser + enter URL) --> BC (Login to the application) --> @Test
	@BeforeClass
	public void productInfoSetup() {
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
	
	@DataProvider
	public Object[][] getProductTestData() {
		return ExcelUtil.getUserRegisterTestDate("product");
	}
	
	@DataProvider
	public Object[][] getProductTestDataCsv(){
		return CsvUtil.csvData("search_product_data");
	}
	
	@Description("ProductInfo Page : Product header exists test using DataDriven")
	@Owner("Senthil")
	@Severity(SeverityLevel.NORMAL)
	@Test(dataProvider = "getProductTestDataCsv")
	public void productHeaderTest(String searchKey, String productName) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage =searchResultsPage.selectProduct(productName);
		String actualHeader = productInfoPage.getProductHeader();
		ChainTestListener.log("ProductInfoTest - productHeaderTest() - using DataProvider :==> getProductTestDataCsv() : "+actualHeader);
		Assert.assertEquals(actualHeader, productName);
	}
	
	@DataProvider
	public Object[][] getProductImages() {
		return new Object[][] {
			{"macbook","MacBook Pro", 4},
			{"samsung","Samsung SyncMaster 941BW",1},
			{"canon","Canon EOS 5D",3}
		};
	}
	
	@DataProvider
	public Object[][] getProductImagesCsv(){
		return CsvUtil.csvData("product_image_count");
	}
	
	@Description("ProductInfo Page : Product Image exists test using DataDriven")
	@Owner("Senthil")
	@Severity(SeverityLevel.NORMAL)
	@Test(dataProvider="getProductImagesCsv")
	public void productImageTest(String searchKey, String productName, String imageCount) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage =searchResultsPage.selectProduct(productName);
		int actualImagesCount = productInfoPage.getProductImagesCount();
		ChainTestListener.log("ProductInfoTest - productImageTest() using DataProvider :==> getProductImagesCsv() : "+actualImagesCount);
		Assert.assertEquals(actualImagesCount, Integer.parseInt(imageCount));
	}
	
	@Description("ProductInfo Page : Get ProductInfo ")
	@Owner("Senthil")
	@Severity(SeverityLevel.BLOCKER)
	@Test
	public void getProductInfoTest() {
		searchResultsPage = accPage.doSearch("macbook");
		productInfoPage =searchResultsPage.selectProduct("MacBook Pro");
		Map<String, String> productDataMap = productInfoPage.getProductData();
		// macbook,MacBook Pro,Apple,Out Of Stock,800,Product 18,$2,000.00,$2,000.00
		SoftAssert softAssert = new SoftAssert();
		ChainTestListener.log("ProductInfoTest - getProductInfoTest() :==> "+productDataMap);
		softAssert.assertEquals(productDataMap.get("ProductName"), "MacBook Pro");
		softAssert.assertEquals(productDataMap.get("Brand"), "Apple");
		softAssert.assertEquals(productDataMap.get("Availability"), "Out Of Stock");
		softAssert.assertEquals(productDataMap.get("Reward Points"), "800");
		softAssert.assertEquals(productDataMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(productDataMap.get("ProductPrice"), "$2,000.00");
		softAssert.assertEquals(productDataMap.get("ExTaxPrice"), "$2,000.00");
		softAssert.assertAll();

	}
	
	// AAA pattern - Arrange Act Assert
	// we can have multiple soft assertions in a single test case 
	// But only one hart assert
	// Practice: Only one assert in this test case.
	
	@Description("ProductInfo Page : Add the product to cart ")
	@Owner("Senthil")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void addProductToCart() {
		String searchProduct = "MacBook Pro";
		searchResultsPage = accPage.doSearch("macbook");
		productInfoPage = searchResultsPage.selectProduct(searchProduct);
		productInfoPage.updateCartQty(2);
		productInfoPage.clickAddToCart();   
		ChainTestListener.log("ProductInfoTest - addProductToCart() success message :==> "+productInfoPage.successMsg().trim());
		Assert.assertEquals(productInfoPage.successMsg().trim(), "Success: You have added "+searchProduct+" to your shopping cart!");
	}
	
	@Description("ProductInfo Page : Go to the shopping cart page ")
	@Owner("Senthil")
	@Severity(SeverityLevel.BLOCKER)
	@Test
	public void clickShoppingCart() {
		String searchProduct = "MacBook Pro";
		searchResultsPage = accPage.doSearch("macbook");
		productInfoPage = searchResultsPage.selectProduct(searchProduct);
		productInfoPage.updateCartQty(2);
		productInfoPage.clickAddToCart();  
		shoppingCartPage = productInfoPage.gotoShoppingCart();
		ChainTestListener.log("ProductInfoTest - clickShoppingCart() :==> "+shoppingCartPage.getShoppingCartHeader());
		Assert.assertEquals(shoppingCartPage.getShoppingCartHeader(), "Shopping Cart");
		
	}
	
}
