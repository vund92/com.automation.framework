package com.nopcommerce.common;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.nopCommerce.user.UserHomePageObject;
import pageObjects.nopCommerce.user.UserLoginPageObject;
import pageObjects.nopCommerce.user.UserRegisterPageObject;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.Random;
import java.util.Set;

//@Listeners(org.uncommons.reportng.HTMLReporter.class) --> neu goi Listener o day thi cai nay chi danh cho class nay thoi, 
// con muon no app dung cho tat ca cac class thi de no trong file runNopCommerce.xml
public class Common_01_Register_Cookie extends BaseTest {
	
	@Parameters("browser")
	@BeforeTest(description = "Create new common User for all Test Classes")
	public void Register(String browserName) {
		driver = getBrowserDriver(browserName);
		homePage = PageGeneratorManager.getUserHomePage(driver);

		firstName = "Automation";
		lastName = "FC";
		emailAddress = "afc" + generateFakeNumber() + "@mail.vn";
		password = "123456";

		log.info("Pre-condition - Step 01: Navigate to 'Register' page");
		registerPage = homePage.openRegisterPage();

		log.info("Pre-condition - Step 02: Enter to Firstname textbox with value is '" + firstName + "'");
		registerPage.inputToFirstnameTextbox(firstName);

		log.info("Pre-condition - Step 03: Enter to Lastname textbox with value is '" + lastName + "'");
		registerPage.inputToLastnameTextbox(lastName);

		log.info("Pre-condition - Step 04: Enter to Email textbox with value is '" + emailAddress + "'");
		registerPage.inputToEmailTextbox(emailAddress);

		log.info("Pre-condition - Step 05: Enter to Password textbox with value is '" + password + "'");
		registerPage.inputToPasswordTextbox(password);

		log.info("Pre-condition - Step 06: Enter to Confirm Password textbox with value is '" + password + "'");
		registerPage.inputToConfirmPasswordTextbox(password);

		log.info("Pre-condition - Step 07: Click to 'Register' button");
		registerPage.clickToRegisterButton();

		log.info("Pre-condition - Step 08: Verify register success message is displayed");
		verifyEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");

		if (homePage.isLoginLinkUndisplayed()) {
			log.info("Pre-condition - Step 09: Navigate to Login page");
			homePage = registerPage.clickToLogoutLink();
			log.info("Pre-condition - Step 10: Navigate to Login page");
			loginPage = registerPage.openLoginPage();
		} else {
			log.info("Pre-condition - Step 10: Navigate to Login page");
			loginPage = registerPage.openLoginPage();
		}

		log.info("Pre-condition - Step 11: Enter to Email textbox with value is '" + emailAddress + "'");
		loginPage.inputToEmailTextbox(emailAddress);

		log.info("Pre-condition - Step 12: Enter to Password textbox with value is '" + password + "'");
		loginPage.inputToPasswordTextbox(password);

		log.info("Pre-condition - Step 13: Click to Login button");
		homePage = loginPage.clickToLoginButton();
		
		loggedCookies = homePage.getAllCookies();
		
		for(Cookie cookie : loggedCookies) {
			System.out.println("Cookie at Common Class" + cookie);
		}
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public int generateFakeNumber() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}
	

	private WebDriver driver;
	private UserHomePageObject homePage;
	private UserRegisterPageObject registerPage;
	private UserLoginPageObject loginPage;
	private String firstName, lastName;
	public static String emailAddress, password;
	public static Set<Cookie> loggedCookies;
}
