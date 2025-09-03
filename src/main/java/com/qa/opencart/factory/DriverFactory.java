package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.FrameworkExceptions;

public class DriverFactory {
	// Initialize the driver --- create a driver

	public WebDriver driver;
	public Properties prop;
	public static String highlightElement;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	public static final Logger log = LogManager.getLogger(DriverFactory.class);

	public OptionsManager optionsManager;

	/**
	 * THis method is initializing the driver on the basis of browser
	 * 
	 * @param browserName
	 * @return it return the driver based the browserName passed as a argument
	 */
	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser");
		// System.out.println("browser name: "+browserName);

		log.info("browser name: " + browserName);

		highlightElement = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);

		switch (browserName.trim().toLowerCase()) {
		case "chrome":
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;
		case "firefox":
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;
		case "edge":
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;
		case "safari":
			tlDriver.set(new SafariDriver());
			break;
		default:
			log.error(AppError.INVALID_BROWSER_MESG + "  : actual Browser passed: " + browserName);
			throw new FrameworkExceptions("======INVALID BROWSER");

		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		String url = prop.getProperty("url");
		log.info("App URL : " + url);
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}

	/**
	 * THis method is used to get the Local Copy of the Driver anytime..
	 * 
	 * @return
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * This method is initializing the properties files with prop
	 * @return return the prop reference
	 * @throws FileNotFoundException
	 */
	// mvn clean install -Denv="qa"
	
	
	
	
	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip = null;
		String envName = System.getProperty("env");

		log.info("Env Name: " + envName);
		try {
			if (envName == null) {
				log.warn("no env is passed , hence running testcases on QA environemnt");
				ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
			} else {
				switch (envName.trim().toLowerCase()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
					break;
					
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/config.dev.properties");
					break;

				case "stage":
					ip = new FileInputStream("./src/test/resources/config/config.stage.properties");
					break;

				case "uat":
					ip = new FileInputStream("./src/test/resources/config/config.uat.properties");
					break;

				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.prod.properties");
					break;
				default:
					log.error("Env value is invalid ... please pass valid environment value ... ");
					throw new FrameworkExceptions("=======INVALID ENVIRONMENT======");
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(" =====================>  "+prop.getProperty("url"));
		return prop;
	}

	public static File getScreenshotFile() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE); // store in temp dir
		return srcFile;
	}

	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
	}

	public static String getScreenshotBase64() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);
	}

}