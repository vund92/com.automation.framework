package com.nopcommerce.user;

import org.testng.annotations.Test;

import commons.BaseTest;
import pageObjects.nopCommerce.user.UserHomePageObject;
import pageObjects.nopCommerce.user.UserRegisterPageObject;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Level_04_Multiple_Browser extends BaseTest{
	
	private WebDriver driverTestClass;
	private UserHomePageObject homePage;
	private UserRegisterPageObject registerPage;
	private String firstName, lastName, emailAddress, password;

	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browserName) {
		System.out.println("Run on " +browserName);
		driverTestClass = getBrowserDriver(browserName);
		System.out.println("Driver at Class test = " + driverTestClass.toString());

		homePage = new UserHomePageObject(driverTestClass);
		
		firstName = "Automation";
		lastName = "FC";
		emailAddress = "afc" + generateFakeNumber() + "@mail.vn";
		password = "123456";
	}
  
	@Test
	public void Register_01_Empty_Data() {
//		System.out.println("Register_01 - Step 01: Click to Register link");
//		homePage.clickToRegisterLink();
//		
//		//CLick Register link -> nhảy qua trang Register
//		registerPage = new RegisterPageObject(driver);
//
//		System.out.println("Register_01 - Step 02: Click to Register button");
//		registerPage.clickToRegisterButton();
//
//		System.out.println("Register Page - Step 03: Verify error message displayed");
//		Assert.assertEquals(registerPage.getErrorMessageAtFirstnameTextbox(), "First name is required.");
//		Assert.assertEquals(registerPage.getErrorMessageAtLastnameTextbox(), "Last name is required.");
//		Assert.assertEquals(registerPage.getErrorMessageAtEmailTextbox(), "Email is required.");
//		Assert.assertEquals(registerPage.getErrorMessageAtPasswordTextbox(), "Password is required.");
//		Assert.assertEquals(registerPage.getErrorMessageAtConfirmPasswordTextbox(), "Password is required.");

	}
	
	@Test
	public void Register_02_Invalid_Email() {
//		System.out.println("Register_02 - Step 01: Click to Register link");
//		homePage.clickToRegisterLink();
//		
//		//CLick Register link -> nhảy qua trang Register
//		registerPage = new RegisterPageObject(driver);
//
//		System.out.println("Register_02 - Step 02: Input to required fields");
//		registerPage.inputToFirstnameTextbox(firstName);
//		registerPage.inputToLastnameTextbox(lastName);
//		registerPage.inputToEmailTextbox("123@456#%*");
//		registerPage.inputToPasswordTextbox(password);
//		registerPage.inputToConfirmPasswordTextbox(password);
//
//		System.out.println("Register_02 - Step 03: Click to Register button");
//		registerPage.clickToRegisterButton();
//
//		System.out.println("Register_02 - Step 04: Verify error message displayed");
//		Assert.assertEquals(registerPage.getErrorMessageAtEmailTextbox(), "Wrong email");
	}
	
	@Test
	public void Register_03_Success() {
//		System.out.println("Register_03 - Step 01: Click to Register link");
//		homePage.clickToRegisterLink();
//		
//		//CLick Register link -> nhảy qua trang Register
//		registerPage = new RegisterPageObject(driver);
//		
//		System.out.println("Register_03 - Step 02: Input to required fields");
//		registerPage.inputToFirstnameTextbox(firstName);
//		registerPage.inputToLastnameTextbox(lastName);
//		registerPage.inputToEmailTextbox(emailAddress);
//		registerPage.inputToPasswordTextbox(password);
//		registerPage.inputToConfirmPasswordTextbox(password);
//		
//		System.out.println("Register_03 - Step 03: Click to Register button");
//		registerPage.clickToRegisterButton();
//		
//		System.out.println("Register_03 - Step 04: Verify success message displayed"); 
//		Assert.assertEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");
		
//		System.out.println("Register Page - Step 05: Click to Logout link"); 
//		registerPage.clickToLogoutLink();
	}
	
	@Test
	public void Register_04_Existing_Email() {
//		System.out.println("Register_04 - Step 01: Click to Register link");
//		homePage.clickToRegisterLink();
//		
//		//CLick Register link -> nhảy qua trang Register
//		registerPage = new RegisterPageObject(driver);
//
//		System.out.println("Register_04 - Step 02: Input to required fields");
//		registerPage.inputToFirstnameTextbox(firstName);
//		registerPage.inputToLastnameTextbox(lastName);
//		registerPage.inputToEmailTextbox(emailAddress);
//		registerPage.inputToPasswordTextbox(password);
//		registerPage.inputToConfirmPasswordTextbox(password);
//		
//		System.out.println("Register_04 - Step 03: Click to Register button");
//		registerPage.clickToRegisterButton();
//
//		System.out.println("Register_04 - Step 04: Verify success message displayed");
//		Assert.assertEquals(registerPage.getErrorExistingEmailMessage(), "The specified email already exists");
	}
	
	@Test
	public void Register_05_Password_Less_Than_6_Chars() {
//		System.out.println("Register_05 - Step 01: Click to Register link");
//		homePage.clickToRegisterLink();
//		
//		//CLick Register link -> nhảy qua trang Register
//		registerPage = new RegisterPageObject(driver);
//
//		System.out.println("Register_05 - Step 02: Input to required fields");
//		registerPage.inputToFirstnameTextbox(firstName);
//		registerPage.inputToLastnameTextbox(lastName);
//		registerPage.inputToEmailTextbox(emailAddress);
//		registerPage.inputToPasswordTextbox("123");
//		registerPage.inputToConfirmPasswordTextbox("123");
//
//		System.out.println("Register_05 - Step 03: Click to Register button");
//		registerPage.clickToRegisterButton();
//
//		System.out.println("Register_05 - Step 04: Verify error message displayed");
//		Assert.assertEquals(registerPage.getErrorMessageAtPasswordTextbox(),
//				"Password must meet the following rules:\nmust have at least 6 characters");
	}
	
	@Test
	public void Register_06_Invalid_Confirm_Password() {
//		System.out.println("Register_06 - Step 01: Click to Register link");
//		homePage.clickToRegisterLink();
//		
//		//CLick Register link -> nhảy qua trang Register
//		registerPage = new RegisterPageObject(driver);
//		
//		System.out.println("Register_06 - Step 02: Input to required fields");
//		registerPage.inputToFirstnameTextbox(firstName);
//		registerPage.inputToLastnameTextbox(lastName);
//		registerPage.inputToEmailTextbox(emailAddress);
//		registerPage.inputToPasswordTextbox("123456");
//		registerPage.inputToConfirmPasswordTextbox("123666");
//		
//		System.out.println("Register_06 - Step 03: Click to Register button");
//		registerPage.clickToRegisterButton();
//		
//		System.out.println("Register_06 - Step 04: Verify error message displayed");
//		Assert.assertEquals(registerPage.getErrorMessageAtConfirmPasswordTextbox(),
//				"The password and confirmation password do not match.");
	}

  @AfterClass
  public void afterClass() {
	  driverTestClass.quit();
  }
  
  public int generateFakeNumber() {
	  Random rand = new Random();
	  return rand.nextInt(9999);
  }
}
