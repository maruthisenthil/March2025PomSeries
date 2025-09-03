package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class ShoppingCartPage {

	// every page will have private driver
	private WebDriver driver;
	private ElementUtil eleUtil;

	// private By locators: page objects
	private final By shoppingCartHeader = By.tagName("h1");
	private final By cartItemTotalRowCounts = By.xpath("//div[@class='table-responsive']/table[contains(@class,'table-bordered')]//tbody/tr");
	
	// Public constructor
	public ShoppingCartPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	@Step("Get the ShopingCart Page Header")
	public String getShoppingCartHeader() {
		String shopCartHeader = eleUtil.waitForElementVisible(shoppingCartHeader, AppConstants.DEFAULT_SHORT_WAIT).getText();
		 String result = shopCartHeader.replaceAll("\\s*\\(.*?\\)", "").trim();
		System.out.println("Shopping Cart Header : "+shopCartHeader);
		return result;
	}
	
	@Step("Get the ShoppingCart page title")
	public String getShoppingPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.SHOPCART_PAGE_TITLE, AppConstants.DEFAULT_SHORT_WAIT);
		System.out.println("ShoppingCartPage Title: "+title);
		return title;
		
	}
	
	@Step("Get the ShoppingCart Page URL")
	public String getShoppingPageURL() {
		String url = eleUtil.waitForURLContains(AppConstants.SHOPCART_PAGE_FRACTION_URL, AppConstants.DEFAULT_SHORT_WAIT);
		System.out.println("ShoppingCartPage URL: "+url);
		return url;
	}
	
	@Step("Get the current cart Total Item Count")
	public int getCartItemCount() {
		List<WebElement>  cartItemList=eleUtil.waitForElementsVisible(cartItemTotalRowCounts, AppConstants.DEFAULT_SHORT_WAIT);
		System.out.println("Cart Item Count : "+cartItemList.size());
		int actCartItemCount = cartItemList.size();
		return actCartItemCount;
	}
	
	
	
}
