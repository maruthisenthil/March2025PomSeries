package com.qa.opencart.factory;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {
	
	private Properties prop;
	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;
	
	public OptionsManager(Properties prop) {
		this.prop=prop;
	}
	
	public static final Logger log = LogManager.getLogger(OptionsManager.class);
	
	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			log.info("Running tests in Headless mode ");
			co.addArguments("--headless");
		}
		
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			log.info("Running tests in Incognito mode ");
			co.addArguments("--incognito");
		}
		return co;
	}
	
	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			log.info("Running tests in Headless mode ");
			fo.addArguments("--headless");
		}
		
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			log.info("Running tests in Incognito mode ");
			fo.addArguments("--incognito");
		}
		return fo;
	}
	
	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			log.info("Running tests in Headless mode ");
			eo.addArguments("--headless");
		}
		
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			log.info("Running tests in Incognito mode ");
			eo.addArguments("--incognito");
		}
		return eo;
	}
	

}
