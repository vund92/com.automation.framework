package com.nopcommerce.user;

import org.testng.annotations.Test;

import com.nopcommerce.common.Common_01_Register_End_User;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.nopCommerce.user.UserAddressPageObject;
import pageObjects.nopCommerce.user.UserCustomerInforPageObject;
import pageObjects.nopCommerce.user.UserHomePageObject;
import pageObjects.nopCommerce.user.UserLoginPageObject;
import pageObjects.nopCommerce.user.UserMyProductReviewPageObject;
import pageObjects.nopCommerce.user.UserRegisterPageObject;
import pageObjects.nopCommerce.user.UserRewardPointPageObject;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

public class Level_16_Share_Data_B extends BaseTest{
	WebDriver driver; //Neu ko dung Thread local thì comment dong nay lai cung duoc, con neu co Thread local thì phai dung toi no
	private UserHomePageObject homePage;
	private UserRegisterPageObject registerPage;
	private UserLoginPageObject loginPage;
	String firstName, lastName, emailAddress, validPassword;

	@Parameters("browser")     
	@BeforeClass
	public void beforeClass(String browserName) {
		driver = getBrowserDriver(browserName);
		homePage = PageGeneratorManager.getUserHomePage(driver);
		
		firstName = "Automation";
		lastName = "FC";
		emailAddress = "afc" + generateFakeNumber() + "@mail.vn";
		validPassword = "123456";
		
		log.info("Pre-condition - Step 01: Navigate to 'Register' page"); 
		registerPage = homePage.openRegisterPage();
	
		log.info("Pre-condition - Step 02: Enter to Firstname textbox with value is '" + firstName + "'"); 
		registerPage.inputToFirstnameTextbox(firstName);
		
		log.info("Pre-condition - Step 03: Enter to Lastname textbox with value is '" + lastName + "'"); 
		registerPage.inputToLastnameTextbox(lastName);
		
		log.info("Pre-condition - Step 04: Enter to Email textbox with value is '" + emailAddress + "'");
		registerPage.inputToEmailTextbox(emailAddress);
		
		log.info("Pre-condition - Step 05: Enter to Password textbox with value is '" + validPassword + "'"); 
		registerPage.inputToPasswordTextbox(validPassword);
		
		log.info("Pre-condition - Step 06: Enter to Confirm Password textbox with value is '" + validPassword + "'");
		registerPage.inputToConfirmPasswordTextbox(validPassword);
		
		log.info("Pre-condition - Step 07: Click to 'Register' button"); 
		registerPage.clickToRegisterButton();
		
		log.info("Pre-condition - Step 08: Verify register success message is displayed"); 
		verifyEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");
		
		if(homePage.isLoginLinkUndisplayed() ) {
			log.info("Pre-condition - Step 09: Navigate to Login page"); 
			homePage = registerPage.clickToLogoutLink();
			log.info("Pre-condition - Step 09: Navigate to Login page"); 
			loginPage = registerPage.openLoginPage();
		} else {
			log.info("Pre-condition - Step 09: Navigate to Login page"); 
			loginPage = registerPage.openLoginPage();
		}
		
		log.info("Pre-condition - Step 10: Enter to Email textbox with value is '" + emailAddress + "'"); 
		loginPage.inputToEmailTextbox(emailAddress);
		
		log.info("Pre-condition - Step 11: Enter to Password textbox with value is '" + validPassword + "'");
		loginPage.inputToPasswordTextbox(validPassword);
		
		log.info("Pre-condition - Step 12: Click to Login button"); 
		homePage = loginPage.clickToLoginButton();

	}
  
	@Test
	public void Search_01_Empty_Data() {
	}
	
	@Test
	public void Search_02_Relative_Product_Name() {
	}
	
	@Test
	public void Search_03_Absolute_Product_Name() {
	}
	
	@Test
	public void Search_04_Parent_Category() {
	}
	
	@Test
	public void Search_05_Incorrect_Manufactorer() {
	}
	
	@Test
	public void Search_06_Correct_Manufactorer() {
	}

  @AfterClass
  public void afterClass() {
	  driver.quit();
  }
  
  public int generateFakeNumber() {
	  Random rand = new Random();
	  return rand.nextInt(9999);
  }
}
