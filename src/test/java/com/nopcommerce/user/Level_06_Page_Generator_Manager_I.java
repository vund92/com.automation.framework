package com.nopcommerce.user;

import org.testng.annotations.Test;

import com.beust.jcommander.Parameter;

import commons.BasePage;
import commons.BaseTest;
import pageObjects.nopCommerce.user.UserHomePageObject;
import pageObjects.nopCommerce.user.UserLoginPageObject;
import pageObjects.nopCommerce.user.UserRegisterPageObject;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Level_06_Page_Generator_Manager_I extends BaseTest{
 
	private WebDriver driver;
	private UserHomePageObject homePage;
	private UserRegisterPageObject registerPage;
	private UserLoginPageObject loginPage;
	private String firstName, lastName, invalidEmail, notFoundEmail, existingEmail, validPassword, invalidPassword;

	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browserName) {
		driver = getBrowserDriver(browserName);
		
		//1
		homePage = new UserHomePageObject(driver);
		
		firstName = "Automation";
		lastName = "FC";
		invalidEmail = "afc@afc.com@.vn";
		existingEmail = "afc" + generateFakeNumber() + "@mail.vn";
		notFoundEmail = "afc" + generateFakeNumber() + "@mail.com";
		validPassword = "123456";
		invalidPassword = "xamxide";
		
		System.out.println("Pre-Condition - Step 01: Click to Register link");
		homePage.openRegisterPage();
		
		//CLick Register link -> nhảy qua trang Register
		//2
		registerPage = new UserRegisterPageObject(driver);
		
		System.out.println("Pre-Condition - Step 02: Input to required fields");
		registerPage.inputToFirstnameTextbox(firstName);
		registerPage.inputToLastnameTextbox(lastName);
		registerPage.inputToEmailTextbox(existingEmail);
		registerPage.inputToPasswordTextbox(validPassword);
		registerPage.inputToConfirmPasswordTextbox(validPassword);
		
		System.out.println("Pre-Condition - Step 03: Click to Register button");
		registerPage.clickToRegisterButton();
		
		System.out.println("Pre-Condition - Step 04: Verify success message displayed"); 
		Assert.assertEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");
		
		//System.out.println("Register Page - Step 05: Click to Logout link"); 
		//registerPage.clickToLogoutLink();
		
		//Click Logout thì business nó sẽ quay về trang HomePage
		//3
		//homePage = new HomePageObject(driver);
	}
  
	@Test
	public void Login_01_Empty_Data() {
		System.out.println("Login_01_Empty_Data - Step 1: Click to Login link");
		registerPage.openLoginPage();
		
		//Từ trang Home - Click Login Link -> Qua trang Login
		//4
		loginPage = new UserLoginPageObject(driver);
		
		System.out.println("Login_01_Empty_Data - Step 2: Click to Login button");
		loginPage.clickToLoginButton();
		
		Assert.assertEquals(loginPage.getErrorMessageAtEmailTextbox(), "Please enter your email");

	}
	
	@Test
	public void Login_02_Invalid_Email() {
		System.out.println("Login_02_Invalid_Email - Step 1: Click to Login link");
		registerPage.openLoginPage();
		
		//Từ trang Home - Click Login Link -> Qua trang Login
		//5
		loginPage = new UserLoginPageObject(driver);
		
		loginPage.inputToEmailTextbox(invalidEmail);
		
		System.out.println("Login_02_Invalid_Email - Step 2: Click to Login button");
		loginPage.clickToLoginButton();
		
		Assert.assertEquals(loginPage.getErrorMessageAtEmailTextbox(), "Wrong email");

	}
	
	@Test
	public void Login_03_Email_Not_Found() {
		System.out.println("Login_03_Email_Not_Found - Step 1: Click to Login link");
		registerPage.openLoginPage();
		
		//Từ trang Home - Click Login Link -> Qua trang Login
		//6
		loginPage = new UserLoginPageObject(driver);
		
		loginPage.inputToEmailTextbox(notFoundEmail);
		
		System.out.println("Login_03_Email_Not_Found - Step 2: Click to Login button");
		loginPage.clickToLoginButton();
		
		Assert.assertEquals(loginPage.getErrorMessageUnsuccessfully(), 
				"Login was unsuccessful. Please correct the errors and try again.\nNo customer account found");
	}
	
	@Test
	public void Login_04_Existing_Email_Empty_Password() {
		System.out.println("Login_04_Existing_Email_Empty_Password - Step 1: Click to Login link");
		registerPage.openLoginPage();
		
		//Từ trang Home - Click Login Link -> Qua trang Login
		//7
		loginPage = new UserLoginPageObject(driver);
		
		loginPage.inputToEmailTextbox(existingEmail);
		loginPage.inputToPasswordTextbox("");
		
		System.out.println("Login_04_Existing_Email_Empty_Password - Step 2: Click to Login button");
		loginPage.clickToLoginButton();
		
		Assert.assertEquals(loginPage.getErrorMessageUnsuccessfully(), 
				"Login was unsuccessful. Please correct the errors and try again.\nThe credentials provided are incorrect");

	}
	
	@Test
	public void Login_05_Existing_Email_Incorrect_Password() {
		System.out.println("Login_05_Existing_Email_Incorrect_Password - Step 1: Click to Login link");
		registerPage.openLoginPage();
		
		//Từ trang Home - Click Login Link -> Qua trang Login
		//8
		loginPage = new UserLoginPageObject(driver);
		
		loginPage.inputToEmailTextbox(existingEmail);
		loginPage.inputToPasswordTextbox("xamxide");
		
		System.out.println("Login_05_Existing_Email_Incorrect_Password - Step 2: Click to Login button");
		loginPage.clickToLoginButton();
		
		Assert.assertEquals(loginPage.getErrorMessageUnsuccessfully(), 
				"Login was unsuccessful. Please correct the errors and try again.\nThe credentials provided are incorrect");

	}
	
	@Test
	public void Login_06_Valid_Email_Password() {
		System.out.println("Login_06_Valid_Email_Password - Step 1: Click to Login link");
		registerPage.openLoginPage();
		
		//Từ trang Home - Click Login Link -> Qua trang Login
		//9
		loginPage = new UserLoginPageObject(driver);
		
		loginPage.inputToEmailTextbox(existingEmail);
		loginPage.inputToPasswordTextbox(validPassword);
		
		System.out.println("Login_06_Valid_Email_Password - Step 2: Click to Login button");
		loginPage.clickToLoginButton();
		
		//Login thành công -> HomePage
		//10
		homePage = new UserHomePageObject(driver);
		
		Assert.assertTrue(homePage.isMyAccountLinkDisplayed());
		
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
