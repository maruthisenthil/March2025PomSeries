package com.qa.opencart.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer{

	private int count=0;
	private static int maxTry=3;
	
	@Override
	public boolean retry(ITestResult iTestResult) {
	
		if(!iTestResult.isSuccess()) { // check if the test not succeed
			if(count < maxTry) { // check if maxtry count is reached
				count++; // increase the maxTry count by 1
				iTestResult.setStatus(iTestResult.FAILURE); // Mark test as failed
				return true; // Tells TestNG to re-run the test
			}else {
				iTestResult.setStatus(iTestResult.FAILURE); // if maxCount reached then test marked as failed
			}
		}else {
			iTestResult.setStatus(iTestResult.SUCCESS); // if test passed, TestNG marks it as passed.
		}
		return false;
	}
	

}
