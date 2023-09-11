package pageObjects.nopCommerce.user;

import org.openqa.selenium.WebDriver;

import commons.BasePage;

public class UserAddressPageObject extends BasePage {
	
	private WebDriver driver; 
	
	public UserAddressPageObject(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

}
