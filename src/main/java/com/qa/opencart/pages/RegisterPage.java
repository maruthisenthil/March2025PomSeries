package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class RegisterPage {

	// every page will have private driver, element utill
	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1. Private By locators
	// RegistrationPage WebElements using by
	private final By firstName = By.id("input-firstname");
	private final By lastName = By.id("input-lastname");
	private final By email = By.id("input-email");
	private final By telephone = By.id("input-telephone");
	private final By password = By.id("input-password");
	private final By confirmPassword = By.id("input-confirm");

	private final By subscribeYes = By.xpath("//input[@name='newsletter' and @value='1']");
	private final By subscribeNo = By.xpath("//input[@name='newsletter' and @value='0']");

	private final By agreeCheckBox = By.name("agree");
	private final By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");

	private final By successMessg = By.cssSelector("div.container div#content h1");
	private final By logoutLink = By.linkText("Logout");
	private final By registerLink = By.linkText("Register");

	// public constructors
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// public page methods/actions
	@Step("Register user with valid and unique data: FirstName : {0}, LastName : {1}, Email : {2}, Telephone : {3}, "
			+ "Password : {4}, Subscription: {5} ")
	public boolean userREgister(String firstName, String lastName, String email, String telephone, String password,
			String subscribe) {

		eleUtil.waitForElementVisible(this.firstName, AppConstants.DEFAULT_SHORT_WAIT).sendKeys(firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, password);

		if (subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
		} else {
			eleUtil.doClick(subscribeNo);
		}

		eleUtil.doClick(agreeCheckBox);
//		eleUtil.doActionsClick(agreeCheckBox);
		eleUtil.doActionsClick(continueButton);

		String successMesg = eleUtil.waitForElementVisible(successMessg, AppConstants.DEFAULT_LARGE_WAIT).getText();

		System.out.println("Register Success message :=>>>>"+successMesg);
		
		if (successMesg.contains(AppConstants.USER_REGISTER_SUCCESS_MSG)) {
			eleUtil.waitForElementVisible(logoutLink, AppConstants.DEFAULT_SHORT_WAIT).click();
//			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			return true;
		} else {
			return false;
		}
		
	}

}
