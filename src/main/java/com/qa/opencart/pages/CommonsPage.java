package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class CommonsPage {
	
	
	
		// every page will have private driver, element util
		private WebDriver driver;
		private ElementUtil eleUtil;

		// private By locators: LoginPage Page elements
		private final By logo = By.cssSelector("img.img-responsive");
		private final By search = By.name("search");
		private final By footers = By.cssSelector("footer li a");
		
		public static final Logger log = LogManager.getLogger(LoginPage.class);
		
		// public constructors
		public CommonsPage(WebDriver driver) {
			this.driver = driver;
			eleUtil = new ElementUtil(driver);
		}
		
		public boolean isLogoExist() {
			return eleUtil.isElementDisplayed(logo);
		}
		
		public boolean isSearchFieldExsit() {
			return eleUtil.isElementDisplayed(search);
		}
		
		public List<String> footerLinkExist() {
			List<WebElement> footerList=  eleUtil.waitForElementsVisible(footers, AppConstants.DEFAULT_MEDIUM_WAIT);
			List<String> footerValueList = new ArrayList<String>();
			for (WebElement e : footerList) {
				String text = e.getText();
				footerValueList.add(text);
			}
			return footerValueList;
			
		}

}
