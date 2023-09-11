package com.nopcommerce.user;

import org.testng.annotations.Test;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.nopCommerce.user.UserCustomerInforPageObject;
import pageObjects.nopCommerce.user.UserHomePageObject;
import pageObjects.nopCommerce.user.UserLoginPageObject;
import pageObjects.nopCommerce.user.UserRegisterPageObject;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Level_18_Pattern_Object extends BaseTest {
	WebDriver driver; //Neu ko dung Thread local thì comment dong nay lai cung duoc, con neu co Thread local thì phai dung toi no
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browserName) {
		driver = getBrowserDriver(browserName);
		homePage = PageGeneratorManager.getUserHomePage(driver);

		firstName = "Automation";
		lastName = "FC";
		emailAddress = "afc" + generateFakeNumber() + "@mail.vn";
		validPassword = "123456";
		
		date = "10"; 
		month = "August";
		year = "1998";

	}

	@Test
	public void User_01_Register() {
		log.info("Register Step 01: Navigate to 'Register' page");
		registerPage = homePage.openRegisterPage();
		
		registerPage.clickToRadioButtonByLabel("Female");
		
		log.info("Register Step 02: Enter to Firstname textbox with value is '" + firstName + "'");
		registerPage.inputToTextboxByID("FirstName", firstName);
		
		log.info("Register Step 03: Enter to Lastname textbox with value is '" + lastName + "'");
		registerPage.inputToTextboxByID("LastName", lastName);
		
		registerPage.selectToDropdownByName("DateOfBirthDay", date);
		registerPage.selectToDropdownByName("DateOfBirthMonth", month); 
		registerPage.selectToDropdownByName("DateOfBirthYear", year);
		
		log.info("Register Step 04: Enter to Email textbox with value is '" + emailAddress + "'");
		registerPage.inputToTextboxByID("Email", emailAddress);
		
		registerPage.clickToCheckboxByLabel("Newsletter");
		
		log.info("Register Step 05: Enter to Password textbox with value is '" + validPassword + "'");
		registerPage.inputToTextboxByID("Password", validPassword);
		
		log.info("Register - Step 06: Enter to Confirm Password textbox with value is '" + validPassword + "'");
		registerPage.inputToTextboxByID("ConfirmPassword", validPassword);
		
		registerPage.sleepInSecond(10);
		
		log.info("Register - Step 07: Click to 'Register' button");
		registerPage.clickToButtonByText("Register");
		
		log.info("Register - Step 08: Verify register success message is displayed");
		Assert.assertEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed...");
	}

	@Test
	public void User_02_Login() {
		
		if (homePage.isLoginLinkUndisplayed()) {
			log.info("Login - Step 00: Navigate to Login page");
			homePage = registerPage.clickToLogoutLink();
			log.info("Login - Step 01: Navigate to Login page");
			loginPage = registerPage.openLoginPage();
		} else {
			log.info("Login - Step 01: Navigate to Login page");
			loginPage = registerPage.openLoginPage();
		}
		
		log.info("Login - Step 02: Enter to Email textbox with value is '" + emailAddress + "'");
		loginPage.inputToTextboxByID("Email", emailAddress);
		
		log.info("Login Step 03: Enter to Password textbox with value is '" + validPassword + "'");
		loginPage.inputToTextboxByID("Password", validPassword);
		
		log.info("Login - Step 04: Click to Login button");
		loginPage.clickToButtonByText("Log in");
		homePage = PageGeneratorManager.getUserHomePage(driver);
		
		log.info("Login Step 05: Verify 'My Account' link is displayed");
		Assert.assertFalse(homePage.isMyAccountLinkDisplayed());
		
		log.info("Login Step 06: Navigate to 'My Account' page");
		customerInforPage = homePage.openMyAccountPage();
		
		log.info("Login Step 06: Verify 'Customer Infor' page is displayed");
		Assert.assertFalse(customerInforPage.isCustomerInforPageDisplayed());
	}
	
	@Test
	public void User_03_My_Account() {
		log.info("My Account Step 01: Navigate to 'My Account' page");
		customerInforPage = homePage.openMyAccountPage();
		
		log.info("My Account Step 02: Verify 'Customer Infor' page is displayed");
		Assert.assertTrue(customerInforPage.isCustomerInforPageDisplayed());
		
		log.info("My Account Step 03: Verify 'First Name' value is correctly");
		Assert.assertEquals(customerInforPage.getTextboxValueByID("FirstName"), firstName);
		
		log.info("My Account Step 04: Verify 'Last Name' value is correctly");
		Assert.assertEquals(customerInforPage.getTextboxValueByID("LastName"), lastName);
		
		log.info("My Account Step 05: Verify 'Email' value is correctly");
		Assert.assertEquals(customerInforPage.getTextboxValueByID("Email"), emailAddress);
	}

	@Parameters({"envName"})
	@AfterClass(alwaysRun = true)
	public void afterClass(String envName) {
		closeBrowserDriver(envName);
	}

	public int generateFakeNumber() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}
	
	private UserHomePageObject homePage;
	private UserRegisterPageObject registerPage;
	private UserLoginPageObject loginPage;
	private UserCustomerInforPageObject customerInforPage;
	String firstName, lastName, emailAddress, validPassword;
	private String date, month, year;
}
