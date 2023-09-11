package pageObjects.nopCommerce.user;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import io.qameta.allure.Step;
import pageUIs.nopCommerce.user.UserCustomerInforPageUI;
import pageUIs.nopCommerce.user.UserHomePageUI;

public class UserCustomerInforPageObject extends BasePage {
	
	private WebDriver driver; 
	
	public UserCustomerInforPageObject(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	@Step("Login - Check 'Customer Infor' page is displayed")
	public boolean isCustomerInforPageDisplayed() {
		waitForAllElementVisible(UserCustomerInforPageUI.CUSTOMER_INFOR_HEADER);
		return isElementDisplayed(UserCustomerInforPageUI.CUSTOMER_INFOR_HEADER);
	}

//	public AddressPageObject openAddressPage() {
//		waitForElementClickable(driver, CustomerInforPageUI.ADDRESS_LINK);
//		clickToElement(driver, CustomerInforPageUI.ADDRESS_LINK);
//		return PageGeneratorManager.getAddressPage(driver);
//	}
	
	

}
