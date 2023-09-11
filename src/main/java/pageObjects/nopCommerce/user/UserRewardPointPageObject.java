package pageObjects.nopCommerce.user;

import org.openqa.selenium.WebDriver;

import commons.BasePage;

public class UserRewardPointPageObject extends BasePage {
	
	private WebDriver driver; 
	
	public UserRewardPointPageObject(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

}
