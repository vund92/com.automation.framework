package com.nopcommerce.user;

import org.testng.annotations.Test;

import commons.BaseTest;
import commons.PageGeneratorManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import pageObjects.nopCommerce.user.UserCustomerInforPageObject;
import pageObjects.nopCommerce.user.UserHomePageObject;
import pageObjects.nopCommerce.user.UserLoginPageObject;
import pageObjects.nopCommerce.user.UserRegisterPageObject;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import static org.testng.Assert.assertFalse;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

@Epic("Regression Tests")
@Feature("Register-Login Tests")
public class Level_15_Allure extends BaseTest{
	WebDriver driver; //Neu ko dung Thread local thì comment dong nay lai cung duoc, con neu co Thread local thì phai dung toi no
	private UserHomePageObject homePage;
	private UserRegisterPageObject registerPage;
	private UserLoginPageObject loginPage;
	private UserCustomerInforPageObject customerInforPage; 
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

	}
	
	@Description("User 01 - Register")
	@Story("Register to system") 
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void User_01_Register() {
		
		//log.info("Register - Step 01: Navigate to 'Register' page"); 
		//ExtentTestManagerV5.getTest().log(Status.INFO, "Register - Step 01: Navigate to 'Register' page");
		registerPage = homePage.openRegisterPage();
	
		//log.info("Register - Step 02: Enter to Firstname textbox with value is '" + firstName + "'"); 
		//ExtentTestManagerV5.getTest().log(Status.INFO, "Register - Step 02: Enter to Firstname textbox with value is '" + firstName + "'");
		registerPage.inputToFirstnameTextbox(firstName);
		
		//log.info("Register - Step 03: Enter to Lastname textbox with value is '" + lastName + "'"); 
		//ExtentTestManagerV5.getTest().log(Status.INFO, "Register - Step 03: Enter to Lastname textbox with value is '" + lastName + "'");
		registerPage.inputToLastnameTextbox(lastName);
		
		//log.info("Register - Step 04: Enter to Email textbox with value is '" + emailAddress + "'");
		//ExtentTestManagerV5.getTest().log(Status.INFO, "Register - Step 04: Enter to Email textbox with value is '" + emailAddress + "'");
		registerPage.inputToEmailTextbox(emailAddress);
		
		//log.info("Register - Step 05: Enter to Password textbox with value is '" + validPassword + "'"); 
		//ExtentTestManagerV5.getTest().log(Status.INFO, "Register - Step 05: Enter to Password textbox with value is '" + validPassword + "'");
		registerPage.inputToPasswordTextbox(validPassword);
		
		//log.info("Register - Step 06: Enter to Confirm Password textbox with value is '" + validPassword + "'");
		//ExtentTestManagerV5.getTest().log(Status.INFO, "Register - Step 06: Enter to Confirm Password textbox with value is '" + validPassword + "'");
		registerPage.inputToConfirmPasswordTextbox(validPassword);
		
		//log.info("Register - Step 07: Click to 'Register' button"); 
		//ExtentTestManagerV5.getTest().log(Status.INFO, "Register - Step 07: Click to 'Register' button");
		registerPage.clickToRegisterButton();
		
		//log.info("Register - Step 08: Verify register success message is displayed"); 
		//ExtentTestManagerV5.getTest().log(Status.INFO, "Register - Step 08: Verify register success message is displayed");
		verifyEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");
		
	}
	
	@Description("User 02 - Login")
	@Story("Login to system") 
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void User_02_Login() {
		if(homePage.isLoginLinkUndisplayed() ) {
			//log.info("Register - Step 00: Click to Logout Link"); 
			//ExtentTestManagerV5.getTest().log(Status.INFO, "Register - Step 00: Click to Logout Link");
			homePage = registerPage.clickToLogoutLink();
			
			//log.info("Login - Step 01: Navigate to 'Login' page"); 
			//ExtentTestManagerV5.getTest().log(Status.INFO, "Login - Step 01: Navigate to 'Login' page");
			loginPage = registerPage.openLoginPage();
		} else {
			//log.info("Login - Step 01: Navigate to 'Login' page"); 
			//ExtentTestManagerV5.getTest().log(Status.INFO, "Login - Step 01: Navigate to 'Login' page");
			loginPage = registerPage.openLoginPage();
		}
		
		//log.info("Login - Step 02: Enter to Email textbox with value is '" + emailAddress + "'"); 
		//ExtentTestManagerV5.getTest().log(Status.INFO, "Login - Step 02: Enter to Email textbox with value is '" + emailAddress + "'");
		loginPage.inputToEmailTextbox(emailAddress);
		
		//log.info("Login - Step 03: Enter to Password textbox with value is '" + validPassword + "'");
		//ExtentTestManagerV5.getTest().log(Status.INFO, "Login - Step 03: Enter to Password textbox with value is '" + validPassword + "'");
		loginPage.inputToPasswordTextbox(validPassword);
		
		//log.info("Login - Step 04: Click to Login button"); 
		//ExtentTestManagerV5.getTest().log(Status.INFO, "Login - Step 04: Click to Login button");
		homePage = loginPage.clickToLoginButton();
		
		//log.info("Login - Step 05: Verify 'My Account' link is displayed"); 
		//ExtentTestManagerV5.getTest().log(Status.INFO, "Login - Step 05: Verify 'My Account' link is displayed");
		assertFalse(homePage.isMyAccountLinkDisplayed());
		
		//log.info("Login - Step 06: Navigate to 'My Account' page");
		//ExtentTestManagerV5.getTest().log(Status.INFO, "Login - Step 06: Navigate to 'My Account' page");
		customerInforPage = homePage.openMyAccountPage();
		
		//log.info("Login - Step 07: Verify 'Customer Infor' page is displayed"); 
		//ExtentTestManagerV5.getTest().log(Status.INFO, "Login - Step 07: Verify 'Customer Infor' page is displayed");
		assertFalse(customerInforPage.isCustomerInforPageDisplayed());
		
	}

  @AfterClass
  public void afterClass() {
	  driver.quit();
	  //driver.close();
  }
  
  public int generateFakeNumber() {
	  Random rand = new Random();
	  return rand.nextInt(9999);
  }
}
