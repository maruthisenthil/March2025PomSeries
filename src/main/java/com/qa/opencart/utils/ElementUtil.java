package com.qa.opencart.utils;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exceptions.ElementException;
import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {

	private WebDriver driver;
	private Actions actions;
	private JavaScriptUtil jsUtil;
	public static final Logger log = LogManager.getLogger(ElementUtil.class);
	
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		actions = new Actions(driver);
		jsUtil = new JavaScriptUtil(driver);
	}

	// any driver related work we have to remove static for successful parallel
	// execution:
	@Step("Enter the value for the element {0}, and the value {1}")
	public void doSendKeys(By locator, String value) {
		log.info("enter the value : "+value+" into the locator: "+locator);
		if (value == null) {
			log.error("value : "+value +" is null");
			throw new ElementException(
					"====Value cannot be null: Keys to send should be a not null CharSequence =====");
		}
		getElement(locator).clear();
		getElement(locator).sendKeys(value);
	}

	public void doMultipleSendKeys(By locator, CharSequence... value) {
		for (CharSequence e : value) {
			if (e == null) {
				throw new ElementException("==value cannot be null==");
			}
		}
		getElement(locator).sendKeys(value);
	}

	@Step("Get the element text for the element {0}")
	public String doElementGetText(By locator) {
		return getElement(locator).getText();
	}

	@Step("Click the Element {0} ")
	public void doClick(By locator) {
		log.info("clicking on the elemnt using : "+locator);
		getElement(locator).click();
	}

	public void doClick(WebElement webElement) {

		webElement.click();

	}

	@Step("checking the element {0} isdiaplayed on the page .. ")
	public boolean isElementDisplayed(By locator) {
		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException nse) {
			System.out.println("Element is not displayed on the page: " + locator);
			return false;
		}
	}
	public boolean isElementDisplayed(WebElement webElement) {
		try {
			return webElement.isDisplayed();
		} catch (NoSuchElementException nse) {
			System.out.println("Element is not displayed on the page: " + webElement);
			return false;
		}
	}

	// this will stop the execution if the element not visible.
	public WebElement isElementCheck(By locator) {
		try {
			return getElement(locator);
		} catch (NoSuchElementException e) {
			throw new ElementException("==Element not found ==");
		}
	}

	public boolean isElementEnabled(By locator) {
		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException nse) {
			System.out.println("Element is not displayed on the page: " + locator);
			return false;
		}
	}

	public String getElementDOMAttribute(By locator, String attributeName) {
		return getElement(locator).getDomAttribute(attributeName);
	}

	public String getElementDOMProperty(By locator, String propertyName) {
		return getElement(locator).getDomProperty(propertyName);
	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public WebElement getElement(By locator) {
		WebElement element= driver.findElement(locator);
		log.info("element is found using : "+locator);
		
		if(Boolean.parseBoolean(DriverFactory.highlightElement)) {
			jsUtil.flash(element);
		}
		return element;
	}

	public List<String> getElementsTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleTextList = new ArrayList<String>(); // pc=0, vc=10
		for (WebElement webElement : eleList) {
			String text = webElement.getText();
			if (text.length() != 0) {
				eleTextList.add(text);
			}
		}
		return eleTextList;
	}

	public boolean isElementPresent(By locator) {
		if (getElementsCount(locator) == 1) {
			System.out.println("the element :" + locator + " is present on the page one time ");
			log.info("the element :" + locator + " is present on the page one time ");
			return true;
		} else {
			System.out.println("the element :" + locator + " is not present on the page one time ");
			log.info("the element :" + locator + " is not present on the page one time ");
			return false;
		}
	}

	public boolean isElementPresent(By locator, int expectedEleCount) {
		if (getElementsCount(locator) == expectedEleCount) {
			System.out.println("the element :" + locator + " is present on the page " + expectedEleCount + " time ");
			log.info("the element :" + locator + " is present on the page " + expectedEleCount + " time ");
			return true;
		} else {
			System.out
					.println("the element :" + locator + " is not present on the page " + expectedEleCount + " time ");
			log.info("the element :" + locator + " is not present on the page " + expectedEleCount + " time ");
			
			return false;
		}
	}

	// June_30
	public void clickElement(By locator, String eleText) {
		List<WebElement> eleList = getElements(locator);
		System.out.println("Total Links count: " + eleList.size());

		for (WebElement webElement : eleList) {
			String text = webElement.getText();
			if (text.contains(eleText)) {
				webElement.click();
				break;
			}
		}
	}

	// June 30 -- Flipkart Search
	public void doSearch(By searchLocator, String searchKey, By suggestionsLocator, String suggestionValue)
			throws InterruptedException {

		doSendKeys(searchLocator, searchKey);
		Thread.sleep(4000);

		List<WebElement> suggestionsList = getElements(suggestionsLocator);
		System.out.println("Total suggestionsList: " + suggestionsList.size());

		for (WebElement webElement : suggestionsList) {
			String text = webElement.getText();
			System.out.println(text);
			if (text.contains(suggestionValue)) {
				webElement.click();
				break;
			}
		}
	}

	// *************************** Select dropdown list
	// ************************************//

	public void doSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectByVisibleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}

	public void doSelectByValue(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByValue(text);
	}

	public int getDropDownOptionsCount(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions().size();
	}

	public List<String> getDropDownValuesList(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		System.out.println("Total Number of options: " + optionsList.size());
		List<String> optionValues = new ArrayList<String>();
		for (WebElement webElement : optionsList) {
			String text = webElement.getText();
			optionValues.add(text);
		}
		return optionValues;
	}

	public void selectDropDownValue(By locator, String value) {

		List<WebElement> countryOptionsEle = getElements(locator);
		System.out.println(countryOptionsEle.size());
		for (WebElement e : countryOptionsEle) {
			String text = e.getText();
			if (text.contains(value)) {
				e.click();
				break;
			}
		}
	}

	public String getDropDownFistSelectValue(By locator) {
		Select select = new Select(getElement(locator));
		return select.getFirstSelectedOption().getText();
	}

	// ########################Action Utils###############

	private void moveToElement(By locator) {
		actions.moveToElement(getElement(locator)).perform();
	}

	public void menuSubMenuHandling(By parentMenu, By childMenu) throws InterruptedException {
		moveToElement(parentMenu);
		Thread.sleep(2000);
		doClick(childMenu);
	}

	public void menuSubMenuHandlingLevel3(By menuLevel1, By menuLevel2, By menuLevel3) throws InterruptedException {
		doClick(menuLevel1);
		Thread.sleep(2000);
		moveToElement(menuLevel2);
		Thread.sleep(2000);
		doClick(menuLevel3);

	}

	// to make it generic I need to pass actionType[click or mousehover]
	public void menuSubMenuHandlingLevel4(By menuLevel1, By menuLevel2, By menuLevel3, By menuLevel4, String actionType)
			throws InterruptedException {
		if (actionType.equalsIgnoreCase("click")) {
			doClick(menuLevel1);
		} else if (actionType.equalsIgnoreCase("mousehover")) {
			moveToElement(menuLevel1);
		}

		Thread.sleep(2000);
		moveToElement(menuLevel2);
		Thread.sleep(2000);
		moveToElement(menuLevel3);
		Thread.sleep(2000);
		doClick(menuLevel4);
	}

	public void doActionsSendKeys(By locator, String value) {
		actions.sendKeys(getElement(locator), value).perform();
	}

	@Step("Using actions click the web element {0}")
	public void doActionsClick(By locator) {
		actions.click(getElement(locator)).build().perform();
	}

	public void doSendKeysWithPause(By locator, String value, int pauseTime) {

		char nameChAr[] = value.toCharArray();
		for (char c : nameChAr) {
			actions.sendKeys(getElement(locator), String.valueOf(c)).pause(pauseTime).perform();
		}
	}

	//-------------------- wait util-------------
	// July 31

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible. DOM only loads
	 * first
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement element= wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		if(Boolean.parseBoolean(DriverFactory.highlightElement)) {
			jsUtil.flash(element);
		}
		return element;
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	@Step("Waiting fo the element: {0} visible with the timeout: {1}" )
	public WebElement waitForElementVisible(By locator, int timeout) {
		log.info(" waiting for the element using By locator:  "+locator +" within the timeout: "+timeout);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		if(Boolean.parseBoolean(DriverFactory.highlightElement)) {
			jsUtil.flash(element);
		}
		return element;
	}

	/***
	 * Multiple Elements - waitForElementsPresence() -
	 * presenceOfAllElementsLocatedBy() An expectation for checking that there is at
	 * least one element present on a web page.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public List<WebElement> waitForElementsPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		List<WebElement> footerList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		return footerList;
	}

	/***
	 * Multiple Elements - waitForElementsVisible() -
	 * visibilityOfAllElementsLocatedBy() An expectation for checking that all
	 * elements present on the web page that match the locator are visible.
	 * Visibility means that the elements are not only displayed but also have a
	 * height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	@Step("Wait for the element {0} to be visible with the wait time of {1} seconds")
	public List<WebElement> waitForElementsVisible(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		List<WebElement> footerList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		return footerList;
	}

	/***
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeout
	 */
	public void clickElementWhenReady(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	// Aug 4,2025
	// Alert - WebDriverWait Utils
	public Alert waitForAlert(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String getAlertText(int timeout) {
		return waitForAlert(timeout).getText();
	}

	public void acceptAlert(int timeout) {
		waitForAlert(timeout).accept();
	}

	public void dismissAlert(int timeout) {
		waitForAlert(timeout).dismiss();
	}

	public void sendKeysInAlert(int timeout, String value) {
		waitForAlert(timeout).sendKeys(value);
	}

	// Aug 4,2025
	// Title : titleContains() -> An expectation for checking that the title
	// contains a case-sensitive substring
	public String waitForTitleContains(String fractionTitleValue, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.titleContains(fractionTitleValue));
		} catch (TimeoutException e) {
			System.out.println("expected title value : " + fractionTitleValue + " is not contains in the actual");
		}
		return driver.getTitle();
	}

	// Title : titleIs() -> An expectation for checking the title of a page
	@Step("Waiting for page Title with expected value: {0} ")
	public String waitForTitleIs(String expectedTitleValue, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.titleIs(expectedTitleValue));
		} catch (TimeoutException e) {
			System.out.println("expected title value : " + expectedTitleValue + " is not present");
		}
		return driver.getTitle();
	}

	// URL : urlContains() -> An expectation for the URL of the current page to
	// contains specific url.
	@Step("Waiting for page URL with expected value: {0} ")
	public String waitForURLContains(String fractionURLValue, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.urlContains(fractionURLValue));
		} catch (TimeoutException e) {
			System.out.println("expected url fractiontext : " + fractionURLValue + " is not contains in the actual");
		}
		return driver.getCurrentUrl();
	}

	// URL: urlToBe() --> An expectation for the URL of the current page to be a
	// specific url.
	public String waitForURLIs(String expectedURLValue, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.urlToBe(expectedURLValue));
		} catch (TimeoutException e) {
			System.out.println("expected url fractiontext : " + expectedURLValue + " is not contains in the actual");
		}
		return driver.getCurrentUrl();
	}

	// Window: numberOfWindowsToBe
	public boolean waitForWindow(int expectedNoOfWindows, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			return wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNoOfWindows));
		} catch (TimeoutException e) {
			System.out.println("Expected number of windows are not correct");
			return false;
		}

	}

	// Frame: frameToBeAvailableAndSwitchToIt(By locator, int timeout)
	// -> An expectation for checking whether the given frame is available to switch
	// to.
	public boolean waitForFrame(By frameLocator, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
			return true;
		} catch (TimeoutException e) {
			System.out.println("frame is not present on the page");
			return false;
		}
	}

	// Frame: frameToBeAvailableAndSwitchToIt(int frameIndex, int timeout)
	// -> An expectation for checking whether the given frame is available to switch
	// to.
	public boolean waitForFrame(int frameIndex, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
			return true;
		} catch (TimeoutException e) {
			System.out.println("frame is not present on the page");
			return false;
		}
	}

	// Frame: frameToBeAvailableAndSwitchToIt(String frameNameOrId, int timeout)
	// -> An expectation for checking whether the given frame is available to switch
	// to.
	public boolean waitForFrame(String frameNameOrId, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrId));
			return true;
		} catch (TimeoutException e) {
			System.out.println("frame is not present on the page");
			return false;
		}
	}

	// *********** FluentWait Utils *************

	// Element Visibility
	public WebElement waitForElementVisibleWithFluentWait(By locator, int timeout, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingTime)) // default polling time is .5 seconds
				.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class)
				.withMessage("=====ELEMENT NOT Visible ON THE PAGE=====");

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// Element Presence
	public WebElement waitForElementPresenceWithFluentWait(By locator, int timeout, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingTime)) // default polling time is .5 seconds
				.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class)
				.withMessage("=====ELEMENT NOT PRESENT ON THE PAGE=====");

		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	// Frame - By frameLocator
	public WebDriver waitForFrameWithFluentWait(By frameLocator, int timeout, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingTime)) // default polling time is .5 seconds
				.ignoring(NoSuchFrameException.class).withMessage("=====FRAME NOT PRESENT ON THE PAGE=====");

		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	// Frame - By frameIndex
	public boolean waitForFrameWithFluentWait(int frameIndex, int timeout, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingTime)) // default polling time is .5 seconds
				.ignoring(NoSuchFrameException.class).withMessage("=====FRAME NOT PRESENT ON THE PAGE=====");

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
		return true;
	}
	
	// Frame - By name
	public boolean waitForFrameWithFluentWait(String frameName, int timeout, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingTime)) // default polling time is .5 seconds
				.ignoring(NoSuchFrameException.class).withMessage("=====FRAME NOT PRESENT ON THE PAGE=====");

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
		return true;
	}

	// Check for alert Visible
	public Alert waitForAlertWithFluentWait(By frameLocator, int timeout, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingTime)) // default polling time is .5 seconds
				.ignoring(NoAlertPresentException.class).withMessage("=====Alert NOT Visible ON THE PAGE=====");

		return wait.until(ExpectedConditions.alertIsPresent());
	}

	// clickElement
	public void clickElementWhenReadyWithFluentWait(By locator, int timeout, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingTime)) // default polling time is .5 seconds
				.ignoring(NoAlertPresentException.class).withMessage("=====Element is NOT Visible ON THE PAGE=====");

		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	// check TitleContains
	public String waitForTitleContainsWithFluentWait(String fractionTitleValue, int timeout, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingTime)) 
				.ignoring(NoAlertPresentException.class).withMessage("=====Element is NOT Visible ON THE PAGE=====")
				.withMessage("expected title fractionValue : " + fractionTitleValue + " is not contains in the actual");

		wait.until(ExpectedConditions.titleContains(fractionTitleValue));

		return driver.getTitle();
	}

	// check actaul TitleIs
	public String waitForTitleIsWithFluentWait(String actualTitleValue, int timeout, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingTime)) 
				.ignoring(NoAlertPresentException.class).withMessage("=====Element is NOT Visible ON THE PAGE=====")
				.withMessage("expected title is : " + actualTitleValue + " is not contains in the actual");

		wait.until(ExpectedConditions.titleIs(actualTitleValue));

		return driver.getTitle();
	}

	// check with UrlContains
	public String waitForURLContainsWithFluentWait(String fractionURLValue, int timeout, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingTime)) 
				.ignoring(MalformedURLException.class).withMessage("=====Element is NOT Visible ON THE PAGE=====")
				.withMessage("expected URL value : " + fractionURLValue + " is not contains in the actual");

		wait.until(ExpectedConditions.urlContains(fractionURLValue));

		return driver.getCurrentUrl();
	}

	// check the Actual URLIs
	public String waitForURLIsWithFluentWait(String expectedURLValue, int timeout, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingTime)) 
				.ignoring(MalformedURLException.class).withMessage("=====Element is NOT Visible ON THE PAGE=====")
				.withMessage("expected URL value : " + expectedURLValue + " is not contains in the actual");

		wait.until(ExpectedConditions.urlToBe(expectedURLValue));

		return driver.getCurrentUrl();
	}
	
	// Window: numberOfWindowsToBe
	public boolean waitForWindowWithFluentWait(int expectedNoOfWindows, int timeout, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingTime)) 
				.ignoring(MalformedURLException.class).withMessage("=====Element is NOT Visible ON THE PAGE=====")
				.withMessage("expected URL value : " + expectedNoOfWindows + " is not contains in the actual");

		return wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNoOfWindows));
	}
	
}
