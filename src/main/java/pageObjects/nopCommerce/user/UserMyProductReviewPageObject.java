package pageObjects.nopCommerce.user;

import org.openqa.selenium.WebDriver;

import commons.BasePage;

public class UserMyProductReviewPageObject extends BasePage {
	
	private WebDriver driver; 
	
	public UserMyProductReviewPageObject(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

}
