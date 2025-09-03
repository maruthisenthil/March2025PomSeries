package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class SearchResultsPage {

	// every page will have private driver
	private WebDriver driver;
	private ElementUtil eleUtil;

	// public constructors 
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);

	}
	
	private final By searchResults = By.cssSelector("div.product-thumb");
	private final By resultHeader = By.cssSelector("div#content h1");
	
	// public page methods/actions
	@Step("Get the search results count .. ")
	public int getSearchResultsCount() {
		int resultCount= eleUtil.waitForElementsPresence(searchResults, AppConstants.DEFAULT_MEDIUM_WAIT).size();
		System.out.println("Results count =: "+resultCount);
		return resultCount;
	}
	
	@Step("Get the search reasults header Value ...")
	public String getResultsHeaderValue() {
		String actualHeader =eleUtil.doElementGetText(resultHeader);
		System.out.println("results header : "+actualHeader);
		return actualHeader;
	}
	
	@Step("select the prodcut {0} in the search results.... ")
	public ProductInfoPage selectProduct(String productName) {
		System.out.println("Product Name :==> "+productName);
		By product= By.linkText(productName);
		eleUtil.doClick(product);
		return new ProductInfoPage(driver);
	}
	
}
