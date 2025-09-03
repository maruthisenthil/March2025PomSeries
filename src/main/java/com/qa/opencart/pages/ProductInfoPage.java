package com.qa.opencart.pages;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class ProductInfoPage {

	// every page will have private driver
	private WebDriver driver;
	private ElementUtil eleUtil;
	private Map<String,String> productMap;

	// Public constructor
	public ProductInfoPage(WebDriver driver) {
		this.driver =driver;
		eleUtil = new ElementUtil(driver);
	}
	
	// private By locators: page objects
	private final By productHeader = By.tagName("h1");
	private final By productImages = By.cssSelector("ul.thumbnails img");
	private final By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private final By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");

	
	private final By qtyTxtField = By.cssSelector("input#input-quantity");
	private final By addToCartBtn = By.cssSelector("div#product #button-cart");
	private final By addToCartSuccessMsg = By.xpath("//div[contains(@class,'alert-success') and contains(text(),'Success: You have added')]");

//	private final By shoppingCartLink = By.xpath("//div[contains(@class,'alert-success')]/a[contains(text(),'shopping cart')]");
	private final By shoppingCartLink = By.cssSelector("div.alert-success a[href*='checkout/cart']");
	
	// public page methods/actions
	@Step("ProductInfo page : Get product header ")
	public String getProductHeader() {
		String actualHeader = eleUtil.waitForElementVisible(productHeader, AppConstants.DEFAULT_SHORT_WAIT).getText();
		System.out.println("Product Header : "+ actualHeader);
		return actualHeader;
	}
	
	@Step("ProductInfo page : Get the product Images count ")
	public int getProductImagesCount() {
		int actualImagesCount = eleUtil.waitForElementsVisible(productImages, AppConstants.DEFAULT_MEDIUM_WAIT).size();
		System.out.println("Total number of Image Count: "+actualImagesCount);
		return actualImagesCount;
	}
	
	@Step("Get ProductData - Parse the product data from the page")
	public Map<String, String> getProductData() {
//		productMap = new HashMap<String, String>(); // Random 
//		productMap = new LinkedHashMap<String, String>(); // exact Insertion order
		productMap = new TreeMap<String, String>(); // sorted order
		productMap.put("ProductName", getProductHeader());
		productMap.put("ProductImages", String.valueOf(getProductImagesCount()));
		getProductMetaData();
		getProductPriceData();
		System.out.println("<=======Product Complete Data ==========>: \n"+productMap);
		return productMap;
	}

//	Brand: Apple
//	Product Code: Product 18
//	Reward Points: 800
//	Availability: Out Of Stock
	@Step("Get the Product Meta info")
	private void getProductMetaData() {
		
		List<WebElement>  metaList=eleUtil.waitForElementsVisible(productMetaData, AppConstants.DEFAULT_SHORT_WAIT);

		
		System.out.println("Total MetaData :"+metaList.size());
		for (WebElement webElement : metaList) {
			String metaData = webElement.getText();
			String[] metaDataArray = metaData.split(":");
			String metaKey = metaDataArray[0].trim();
			String metaValue = metaDataArray[1].trim();
			productMap.put(metaKey, metaValue);
		}
//		return productMap;
	}
	
//	$2,000.00
//	Ex Tax: $2,000.00
	@Step("Get the Product price info")
	private void getProductPriceData() {
		
		List<WebElement>  priceList=eleUtil.waitForElementsVisible(productPriceData, AppConstants.DEFAULT_SHORT_WAIT);
		System.out.println("Total PriceData :"+priceList.size());
		
		String priceValue = priceList.get(0).getText();
		String exTaxValue = priceList.get(1).getText().split(":")[1].trim();
		
		productMap.put("ProductPrice", priceValue);
		productMap.put("ExTaxPrice", exTaxValue);
	}
	
	
	// Shopping cart 
	@Step("Update the shopping cart page with the quantity by {0}")
	public void updateCartQty(int prodQty) {
		WebElement qtyTextEle = eleUtil.waitForElementVisible(qtyTxtField, AppConstants.DEFAULT_MEDIUM_WAIT);
		qtyTextEle.clear();
		eleUtil.doSendKeys(qtyTxtField, String.valueOf(prodQty));
		System.out.println("Updated the product quantity by : "+prodQty);
	}
	
	@Step("Click the AddToCart button in the Product Info Page")
	public void clickAddToCart(){
		eleUtil.doClick(addToCartBtn);
	}
	
	@Step("Get the SuccessMessage ")
	public String successMsg(){
		String successMsg=eleUtil.waitForElementVisible(addToCartSuccessMsg, AppConstants.DEFAULT_SHORT_WAIT).getText();
		successMsg = successMsg.replace("×", "").trim();
		return successMsg;
	}
	
	@Step("Navigate to ShoppingCart page")
	public ShoppingCartPage gotoShoppingCart() {
		eleUtil.waitForElementVisible(shoppingCartLink, AppConstants.DEFAULT_SHORT_WAIT).click();
		return new ShoppingCartPage(driver);
		
	}
	

}
