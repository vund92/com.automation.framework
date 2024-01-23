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
import utilities.DataHelper;
import utilities.Environment;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.Random;

import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

@Epic("Regression Tests") //for allure
@Feature("Register-Login Tests") //for allure
public class Level_24_Browser_Factory extends BaseTest {
	Environment environment;

	@Parameters({ "envName", "serverName", "browser", "ipAddress", "port", "osName", "osVersion" })
	@BeforeClass
	public void beforeClass(@Optional("local") String envName, @Optional("dev") String serverName,
			@Optional("chrome") String browserName, @Optional("localhost") String ipAddress,
			@Optional("4444") String portNumber, @Optional("Windows") String osName, @Optional("10") String osVersion) {
		
//		ConfigFactory.setProperty("env", environmentName);
//		environment = ConfigFactory.create(Environment.class);

		driver = getBrowserDriver(envName, serverName, browserName, ipAddress, portNumber, osName, osVersion);
		
		System.out.println("Luong id: " + Thread.currentThread().getId() + " - Luong name: " + Thread.currentThread().getName());

//		System.out.println(environment.appUrl());
//		System.out.println(environment.appPassword());
//		System.out.println(environment.appUsername());
//		System.out.println(environment.dbHostname());
//		System.out.println(environment.dbPassword());
//		System.out.println(environment.dbUsername());

		homePage = PageGeneratorManager.getUserHomePage(driver);
		dataFaker = DataHelper.getDataHelper();

		firstName = dataFaker.getFirstName();
		lastName = dataFaker.getLastName();
		emailAddress = dataFaker.getEmail();
		validPassword = dataFaker.getPassword();

		date = "10";
		month = "August";
		year = "1998";

	}

	@Description("User 01 - Register") //for allure
	@Story("Register to system") //for allure
	@Severity(SeverityLevel.NORMAL) //for allure
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

	@Description("User 02 - Login") //for allure
	@Story("Register to system") //for allure
	@Severity(SeverityLevel.NORMAL) //for allure
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
		verifyTrue(homePage.isMyAccountLinkDisplayed());

		log.info("Login Step 06: Navigate to 'My Account' page");
		customerInforPage = homePage.openMyAccountPage();

		log.info("Login Step 06: Verify 'Customer Infor' page is displayed");
		verifyTrue(customerInforPage.isCustomerInforPageDisplayed());
	}

	@Description("User 03 - My Account") //for allure
	@Story("Register to system") //for allure
	@Severity(SeverityLevel.NORMAL) //for allure
	@Test
	public void User_03_My_Account() {
		log.info("My Account Step 01: Navigate to 'My Account' page");
		customerInforPage = homePage.openMyAccountPage();

		log.info("My Account Step 02: Verify 'Customer Infor' page is displayed");
		verifyTrue(customerInforPage.isCustomerInforPageDisplayed());

		log.info("My Account Step 03: Verify 'First Name' value is correctly");
		verifyEquals(customerInforPage.getTextboxValueByID("FirstName"), firstName);

		log.info("My Account Step 04: Verify 'Last Name' value is correctly");
		verifyEquals(customerInforPage.getTextboxValueByID("LastName"), lastName);

		log.info("My Account Step 05: Verify 'Email' value is correctly");
		verifyEquals(customerInforPage.getTextboxValueByID("Email"), emailAddress);
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

	WebDriver driver; //Neu ko dung Thread local thì comment dong nay lai cung duoc, con neu co Thread local thì phai dung toi no
	private DataHelper dataFaker;
	private UserHomePageObject homePage;
	private UserRegisterPageObject registerPage;
	private UserLoginPageObject loginPage;
	private UserCustomerInforPageObject customerInforPage;
	String firstName, lastName, emailAddress, validPassword;
	private String date, month, year;
}
