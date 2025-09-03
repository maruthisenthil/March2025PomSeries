package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class AccountsPage {
	
	// every page will have private driver
	private WebDriver driver;
	private ElementUtil eleUtil;

	// public constructors 
	public AccountsPage(WebDriver driver) {
		this.driver=driver;
		eleUtil= new ElementUtil(driver);
	}
	
	// private By locators: page objects
	public final By headers = By.cssSelector("h2"); //  NALSuggestion: div#content h2
	public final By logoutLink = By.linkText("Logout");
	public final By searchField = By.name("search");
	public final By searchIcon = By.xpath("//div[@id='search']//button"); // NAL Suggestion: div
	
	// public page methods/actions
	@Step("Get the AccountPage Headers ...")
	public List<String> getAccPageHeaders() {
		
		List<WebElement>headersList = eleUtil.waitForElementsPresence(headers, AppConstants.DEFAULT_SHORT_WAIT);
		
		System.out.println("Total Number of Headers: "+ headersList.size());
		List<String> headersValList = new ArrayList<String>();
		for (WebElement e : headersList) {
			String text = e.getText();
			headersValList.add(text);
		}
		return headersValList;
	}
	
	@Step("Checking Logoutlink Exiss in the page...")
	public boolean isLogoutLinkExist() {
		WebElement logoutEle = eleUtil.waitForElementVisible(logoutLink, AppConstants.DEFAULT_MEDIUM_WAIT);
		return eleUtil.isElementDisplayed(logoutEle);
	}
	@Step("search the product based on the search key {0}")
	public SearchResultsPage doSearch(String searchKey) {
		WebElement searchEle = eleUtil.waitForElementVisible(searchField, AppConstants.DEFAULT_MEDIUM_WAIT);
		searchEle.clear();
		searchEle.sendKeys(searchKey);
		eleUtil.doClick(searchIcon);
		return new SearchResultsPage(driver);
	}
	
	
	
	
	
	
	
	
	
	
}
