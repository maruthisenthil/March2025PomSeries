package com.qa.opencarts.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
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

@Epic("Epic-106 : Design the Open Cart App - ShoppingCart Page")
@Feature("F-106 : Design open cart User ShoppingCart Page from ProductInfo pave ")
@Story("US-54: Develop new User Register page tests for : Register new users to open cart app. ")
public class ShoppingCartPageTest extends BaseTest{
	
	@BeforeClass
	public void searchSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Description("ShoppingCart Page : Add a product prodcut to Shopping cart and check Shoppint cart Size")
	@Owner("Senthil")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void checkCartItemCount() {
		String searchProduct = "MacBook Pro";
		searchResultsPage = accPage.doSearch("macbook");
		productInfoPage = searchResultsPage.selectProduct(searchProduct);
		productInfoPage.updateCartQty(2);
		productInfoPage.clickAddToCart();  
		shoppingCartPage = productInfoPage.gotoShoppingCart();
		ChainTestListener.log("ShoppingCartPageTest - checkCartItemCount() :==> ActualCart Count: "+shoppingCartPage.getCartItemCount());
		Assert.assertEquals(shoppingCartPage.getCartItemCount(), 1);
	}
	

}
