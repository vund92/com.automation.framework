package pageObjects.nopCommerce.user;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import io.qameta.allure.Step;
import pageUIs.nopCommerce.user.UserHomePageUI;
import pageUIs.nopCommerce.user.UserRegisterPageUI;

public class UserRegisterPageObject extends BasePage {
	
	private WebDriver driver;

	public UserRegisterPageObject(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	@Step("Register - Click to 'Register' button")
	public void clickToRegisterButton() {
		waitForElementClickable(UserRegisterPageUI.REGISTER_BUTTON);
		clickToElement(UserRegisterPageUI.REGISTER_BUTTON);
	}

	public String getErrorMessageAtFirstnameTextbox() {
		waitForElementVisible(UserRegisterPageUI.FIRST_NAME_ERROR_MESSAGE);
		return getElementText(UserRegisterPageUI.FIRST_NAME_ERROR_MESSAGE);
	}

	public String getErrorMessageAtLastnameTextbox() {
		waitForElementVisible(UserRegisterPageUI.LAST_NAME_ERROR_MESSAGE);
		return getElementText(UserRegisterPageUI.LAST_NAME_ERROR_MESSAGE);
	}

	public String getErrorMessageAtEmailTextbox() {
		waitForElementVisible(UserRegisterPageUI.EMAIL_ERROR_MESSAGE);
		return getElementText(UserRegisterPageUI.EMAIL_ERROR_MESSAGE);
	}

	public String getErrorMessageAtPasswordTextbox() {
		waitForElementVisible(UserRegisterPageUI.PASSWORD_ERROR_MESSAGE);
		return getElementText(UserRegisterPageUI.PASSWORD_ERROR_MESSAGE);
	}

	public String getErrorMessageAtConfirmPasswordTextbox() {
		waitForElementVisible(UserRegisterPageUI.CONFIRM_PASSWORD_ERROR_MESSAGE);
		return getElementText(UserRegisterPageUI.CONFIRM_PASSWORD_ERROR_MESSAGE);
	}

	@Step("Register - Enter to Firstname textbox with value is {0}")
	public void inputToFirstnameTextbox(String firstName) {
		waitForElementVisible(UserRegisterPageUI.FIRST_NAME_TEXTBOX);
		sendkeyToElement(UserRegisterPageUI.FIRST_NAME_TEXTBOX, firstName);
	}

	@Step("Register - Enter to Lastname textbox with value is {0}")
	public void inputToLastnameTextbox(String lastName) {
		waitForElementVisible(UserRegisterPageUI.LAST_NAME_TEXTBOX);
		sendkeyToElement(UserRegisterPageUI.LAST_NAME_TEXTBOX, lastName);
		
	}
	
	@Step("Register - Enter to Email textbox with value is {0}")
	public void inputToEmailTextbox(String emailAddress) {
		waitForElementVisible(UserRegisterPageUI.EMAIL_TEXTBOX);
		sendkeyToElement(UserRegisterPageUI.EMAIL_TEXTBOX, emailAddress);
	}

	@Step("Register - Enter to Password textbox with value is {0}")
	public void inputToPasswordTextbox(String password) {
		waitForElementVisible(UserRegisterPageUI.PASSWORD_TEXTBOX);
		sendkeyToElement(UserRegisterPageUI.PASSWORD_TEXTBOX, password);
	}

	@Step("Register - Enter to Confirm Password textbox with value is {0}")
	public void inputToConfirmPasswordTextbox(String confirmPassword) {
		waitForElementVisible(UserRegisterPageUI.CONFIRM_PASSWORD_TEXTBOX);
		sendkeyToElement(UserRegisterPageUI.CONFIRM_PASSWORD_TEXTBOX, confirmPassword);
	}

	@Step("Register - Get register success message")
	public String getRegisterSuccessMessage() {
		waitForElementVisible(UserRegisterPageUI.REGISTER_SUCCESS_MESSAGE);
		return getElementText(UserRegisterPageUI.REGISTER_SUCCESS_MESSAGE);
	}

	@Step("Login - Click to Logout Link")
	public UserHomePageObject clickToLogoutLink() {
		waitForElementClickable(UserRegisterPageUI.LOGOUT_LINK);
		clickToElement(UserRegisterPageUI.LOGOUT_LINK);
		//Cach 2
		//return new HomePageObject(driver);
		
		//Cach 3
		return PageGeneratorManager.getUserHomePage(driver);
	}

	@Step("Login - Get Error Existing Email Message")
	public String getErrorExistingEmailMessage() {
		waitForElementVisible(UserRegisterPageUI.EXISTING_EMAIL_ERROR_MESSAGE);
		return getElementText(UserRegisterPageUI.EXISTING_EMAIL_ERROR_MESSAGE);
	}
	
	@Step("Login - Check Logout Link Displayed")
	public boolean isLogoutLinkDisplayed() {
		waitForElementVisible(UserRegisterPageUI.LOGOUT_LINK);
		return isElementDisplayed(UserRegisterPageUI.LOGOUT_LINK);
	}
	
	@Step("Login - Navigate to 'Login' page")
	public UserLoginPageObject openLoginPage() {
		waitForElementClickable(UserRegisterPageUI.LOGIN_LINK);
		clickToElement(UserRegisterPageUI.LOGIN_LINK);
		return PageGeneratorManager.getUserLoginPage(driver);
	}

	
}
