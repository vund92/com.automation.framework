package com.nopcommerce.user;

import org.testng.annotations.Test;

import commons.BaseTest;
import commons.GlobalConstants;
import commons.PageGeneratorManager;
import pageObjects.nopCommerce.admin.AdminDashboardPageObject;
import pageObjects.nopCommerce.admin.AdminLoginPageObject;
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

public class Level_08_Switch_Role extends BaseTest{
 
	private WebDriver driver;
	private UserHomePageObject userHomePage;
	private UserLoginPageObject userLoginPage;
	private AdminLoginPageObject adminLoginPage;
	private AdminDashboardPageObject adminDashboardPage;
	private UserCustomerInforPageObject userCustomerInfoPage;
	private UserRegisterPageObject registerPage;
	String userEmailAddress, userPassword, adminEmailAddress, adminPassword;

	@Parameters("browser")     
	@BeforeClass
	public void beforeClass(String browserName) {
		driver = getBrowserDriver(browserName);
		userHomePage = PageGeneratorManager.getUserHomePage(driver);
		
		userEmailAddress = "vund92@gmail.com";
		userPassword = "123456";
		adminEmailAddress = "admin@yourstore.com";
		adminPassword = "admin";

	}
  
	@Test
	public void Role_01_User_To_Admin() {
		userLoginPage = registerPage.openLoginPage();
		
		// Login as User role
		userHomePage = userLoginPage.loginAsUser(userEmailAddress, userPassword);
		Assert.assertTrue(userHomePage.isMyAccountLinkDisplayed());
		
		//Home Page -> Customer Infor
		userCustomerInfoPage = userHomePage.openCustomerInforPage(driver);
		
		//Customer Info -> Click Logout -> Home Page
		userHomePage = userCustomerInfoPage.clickToLogoutLinkAtUserPage(driver);
		
		//User Home Page -> Open Admin Page -> Login Page (Admin)
		userHomePage.openPageUrl(GlobalConstants.getGlobalConstants().getAdminDevUrl());
		adminLoginPage = PageGeneratorManager.getAdminLoginPage(driver);
		
		// Login as Admin role
		adminDashboardPage = adminLoginPage.loginAsAdmin(adminEmailAddress, adminPassword);
		Assert.assertTrue(adminDashboardPage.isDashboardHeaderDisplayed());

		//Dashboard Page -> Click Logout -> Login Page (Admin)
		adminLoginPage = adminDashboardPage.clickToLogoutLinkAtAdminPage(driver);

	}
	
	@Test
	public void Role_02_Admin_To_User() {
		//Login Page(Admin) -> Open User URL -> Home Page (User)
		adminLoginPage.openPageUrl(GlobalConstants.getGlobalConstants().getPortalDevUrl());
		userHomePage = PageGeneratorManager.getUserHomePage(driver);
		
		//Home Page -> Login Page (User)
		userLoginPage = registerPage.openLoginPage();
		
		//Login as User role
		userHomePage = userLoginPage.loginAsUser(userEmailAddress, userPassword);
		Assert.assertTrue(userHomePage.isMyAccountLinkDisplayed());

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
