package commons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;
import pageObjects.nopCommerce.admin.AdminLoginPageObject;
import pageObjects.nopCommerce.user.UserAddressPageObject;
import pageObjects.nopCommerce.user.UserCustomerInforPageObject;
import pageObjects.nopCommerce.user.UserHomePageObject;
import pageObjects.nopCommerce.user.UserMyProductReviewPageObject;
import pageObjects.nopCommerce.user.UserRewardPointPageObject;
//import pageObjects.wordpress.AdminDashboardPO;
//import pageObjects.wordpress.UserHomePO;
//import pageUIs.jQuery.uploadFile.BasePageUI_JQueryUpload;
import pageUIs.nopCommerce.user.BasePageUI_NopCommerce;

public class BasePage {
	
	private WebDriver driver;
	
	public BasePage() {
	}
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
	}
	
//	public static BasePage getBasePageObject() {
//		return new BasePage(driver);
//	}

	public void openPageUrl(String pageUrl) {
		driver.get(pageUrl);
	}

	public String getPageTitle() {
		return driver.getTitle();
	}

	@Step("Get page Url")
	public String getPageUrl() {
		return driver.getCurrentUrl();
	}

	public String getPageSourceCode() {
		return driver.getPageSource();
	}

	public void backToPage() {
		driver.navigate().back();
	}

	public void forwardToPage() {
		driver.navigate().forward();
	}

	public void refreshCurrentPage() {
		driver.navigate().refresh();
	}

	public Set<Cookie> getAllCookies() {
		return driver.manage().getCookies();
	}

	public void setCookies( Set<Cookie> cookies) {
		for (Cookie cookie : cookies) {
			driver.manage().addCookie(cookie);
		}
		sleepInSecond(3);
	}

	public Alert waitForAlertPresence() {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		return explicitWait.until(ExpectedConditions.alertIsPresent());
	}

	public void acceptAlert() {
		waitForAlertPresence().accept();
	}

	public void cancelAlert() {
		waitForAlertPresence().dismiss();
	}

	public String getAlertText() {
		return waitForAlertPresence().getText();
	}

	public void sendKeyToAlert(String textValue) {
		waitForAlertPresence().sendKeys(textValue);
	}

	public void switchToWindowByID(String windowID) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindow : allWindows) {
			if (!runWindow.equals(windowID)) {
				driver.switchTo().window(runWindow);
				break;
			}
		}
	}

	public void switchToWindowByTitle(String tabTitle) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			driver.switchTo().window(runWindows);
			String currentWin = driver.getTitle();
			if (currentWin.equals(tabTitle)) {
				break;
			}
		}
	}

	/*
	 * private By getByXpath(String xpathLocator) { return By.xpath(xpathLocator); }
	 */

	private By getByLocator(String locatorType) {
		By by = null;
		// System.out.println("Locator type: " + locatorType);
		if (locatorType.startsWith("id=") || locatorType.startsWith("ID=") || locatorType.startsWith("Id=")) {
			by = By.id(locatorType.substring(3));
		} else if (locatorType.startsWith("class=") || locatorType.startsWith("CLASS=")
				|| locatorType.startsWith("Class=")) {
			by = By.className(locatorType.substring(6));
		} else if (locatorType.startsWith("name=") || locatorType.startsWith("NAME=")
				|| locatorType.startsWith("Name=")) {
			by = By.name(locatorType.substring(5));
		} else if (locatorType.startsWith("css=") || locatorType.startsWith("CSS=") || locatorType.startsWith("Css=")) {
			by = By.cssSelector(locatorType.substring(4));
		} else if (locatorType.startsWith("xpath=") || locatorType.startsWith("XPATH=")
				|| locatorType.startsWith("Xpath=") || locatorType.startsWith("XPath=")) {
			by = By.xpath(locatorType.substring(6));
		} else {
			throw new RuntimeException("Locator type is not supported!");
		}
		return by;
	}

	private String getDynamicXpath(String locatorType, String... dynamicValues) {
		System.out.println("Locator type before: " + locatorType);
		if (locatorType.startsWith("xpath=") || locatorType.startsWith("XPATH=") || locatorType.startsWith("Xpath=")
				|| locatorType.startsWith("XPath=")) {
			locatorType = String.format(locatorType, (Object[]) dynamicValues);
		}

		for (String value : dynamicValues) {
			System.out.println("Values map to locator: " + value);
		}
		System.out.println("Locator type after: " + locatorType);
		return locatorType;
	}

	public void closeAllWindowsWithoutParent(String parentID) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			if (!runWindows.equals(parentID)) {
				driver.switchTo().window(runWindows);
				driver.close();
			}
		}
		driver.switchTo().window(parentID);
	}

	private WebElement getWebElement(String locatorType) {
		return driver.findElement(getByLocator(locatorType));
	}

	protected List<WebElement> getListWebElement(String locatorType) {
		return driver.findElements(getByLocator(locatorType));
	}

	public void clickToElement(String locatorType) {
		getWebElement(locatorType).click();
	}

	public void clickToElement(String locatorType, String... dynamicValues) {
		getWebElement(getDynamicXpath(locatorType, dynamicValues)).click();
	}

	public void sendkeyToElement(String locatorType, String textValue) {
		WebElement element = getWebElement(locatorType);
		element.clear();
		element.sendKeys(textValue);
	}

	public void sendkeyToElement(String locatorType, String textValue, String... dynamicValues) {
		WebElement element = getWebElement(getDynamicXpath(locatorType, dynamicValues));
		element.clear();
		element.sendKeys(textValue);
	}

	public void clearValueInElementByDeleteKey(String locatorType) {
		WebElement element = this.getWebElement(locatorType);
		element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
	}

	public String getElementText(String locatorType) {
		return getWebElement(locatorType).getText();
	}

	public String getElementText(String locatorType, String... dynamicValues) {
		return getWebElement(getDynamicXpath(locatorType, dynamicValues)).getText();
	}

	public void selectItemInDefaultDropdown(String locatorType, String textItem) {
		Select select = new Select(getWebElement(locatorType));
		// select.selectByValue(textItem);

		select.selectByVisibleText(textItem);
	}

	public void selectItemInDefaultDropdown(String locatorType, String textItem,
			String... dynamicValues) {
		Select select = new Select(getWebElement(getDynamicXpath(locatorType, dynamicValues)));
		//select.selectByValue(textItem);
		
		select.selectByVisibleText(textItem);
	}

	public String getSelectedItemDefaultDropdown(String locatorType) {
		Select select = new Select(getWebElement(locatorType));
		return select.getFirstSelectedOption().getText();
	}

	public String getSelectedItemDefaultDropdown(String locatorType, String... dynamicValues) {
		Select select = new Select(getWebElement(getDynamicXpath(locatorType, dynamicValues)));
		return select.getFirstSelectedOption().getText();
	}

	public boolean isDropdownMultiple(String locatorType) {
		Select select = new Select(getWebElement(locatorType));
		return select.isMultiple();
	}

	public void selectItemInCustomDropdown(String parentXpath, String childXpath,
			String expectedTextItem) {
		getWebElement(parentXpath).click();
		sleepInSecond(1);

		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		List<WebElement> allItems = explicitWait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByLocator(childXpath)));

		for (WebElement item : allItems) {
			if (item.getText().trim().equals(expectedTextItem)) {
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInSecond(1);

				item.click();
				sleepInSecond(1);
				break;
			}
		}
	}

	public void sleepInSecond(long time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getElementAttribute(String locatorType, String attributeName) {
		return getWebElement(locatorType).getAttribute(attributeName);
	}

	public String getElementAttribute(String locatorType, String attributeName,
			String... dynamicValues) {
		return getWebElement(getDynamicXpath(locatorType, dynamicValues)).getAttribute(attributeName);
	}

	public String getElementCssValue(String locatorType, String propertyName) {
		return getWebElement(locatorType).getAttribute(propertyName);
	}

	public String getHexaColorFromRGBA(String rgbaValue) {
		return Color.fromString(rgbaValue).asHex();
	}

	public int getElementSize(String locatorType) {
		return getListWebElement(locatorType).size();
	}

	public int getElementSize(String locatorType, String... dynamicValues) {
		return getListWebElement(getDynamicXpath(locatorType, dynamicValues)).size();
	}

	public void checkToDefaultCheckboxOrRadio(String locatorType) {
		WebElement element = getWebElement(locatorType);
		if (!element.isSelected()) {
			element.click();
		}
	}

	public void checkToDefaultCheckboxOrRadio(String locatorType, String... dynamicValues) {
		WebElement element = getWebElement(getDynamicXpath(locatorType, dynamicValues));
		if (!element.isSelected()) {
			element.click();
		}
	}

	public void uncheckToDefaultCheckbox(String locatorType) {
		WebElement element = getWebElement(locatorType);
		if (element.isSelected()) {
			element.click();
		}
	}

	public void uncheckToDefaultCheckbox(String locatorType, String... dynamicValues) {
		WebElement element = getWebElement(getDynamicXpath(locatorType, dynamicValues));
		if (element.isSelected()) {
			element.click();
		}
	}

	public boolean isElementDisplayed(String locatorType) {
		try {
			// Tìm thấy element:
			// Case 1: Displayed - trả về true
			// Case 2: Undisplayed - trả về false
			return getWebElement(locatorType).isDisplayed();
		} catch (NoSuchElementException e) {
			// Case 3: Undisplayed - trả về false
			return false;
		}
	}

	public boolean isElementDisplayed(String locatorType, String... dynamicValues) {
		return getWebElement(getDynamicXpath(locatorType, dynamicValues)).isDisplayed();
	}

	public boolean isElementUndisplayed(String locatorType) {
		// System.out.println("Start time = " + new Date().toString());
		overrideImplicitTimeout(shortTimeout);
		List<WebElement> elements = getListWebElement(locatorType);
		overrideImplicitTimeout(longTimeout);
		if (elements.size() == 0) {
			// System.out.println("Case 3 - Element not in DOM");
			// System.out.println("End time = " + new Date().toString());
			return true;
			// Nó có kích thước = 1 (Có trong DOM)
			// Ko được hiển thị
		} else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
			// System.out.println("Case 2 - Element in DOM but not visible/ displayed");
			// System.out.println("End time = " + new Date().toString());
			return true;
		} else {
			// System.out.println("Case 1 - Element in DOM and visible");
			return false;
		}
	}

	public boolean isElementUndisplayed(String locator, String... dynamicValues) {
		// System.out.println("Start time = " + new Date().toString());
		overrideImplicitTimeout(shortTimeout);
		List<WebElement> elements = getListWebElement(getDynamicXpath(locator, dynamicValues));
		overrideImplicitTimeout(longTimeout);
		if (elements.size() == 0) {
			// System.out.println("Case 3 - Element not in DOM");
			// System.out.println("End time = " + new Date().toString());
			return true;
			// Nó có kích thước = 1 (Có trong DOM)
			// Ko được hiển thị
		} else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
			// System.out.println("Case 2 - Element in DOM but not visible/ displayed");
			// System.out.println("End time = " + new Date().toString());
			return true;
		} else {
			// System.out.println("Case 1 - Element in DOM and visible");
			return false;
		}
	}

	public void overrideImplicitTimeout(long timeOut) {
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
	}

	public boolean isElementEnabled(String locatorType) {
		return getWebElement(locatorType).isEnabled();
	}

	public boolean isElementSelected(String locatorType) {
		return getWebElement(locatorType).isSelected();
	}

	public void switchToFrameIframe(String locatorType) {
		driver.switchTo().frame(getWebElement(locatorType));
	}

	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	public void hoverMouseToElement(String locatorType) {
		Actions action = new Actions(driver);
		action.moveToElement(getWebElement(locatorType)).perform();
	}

	public void pressKeyToElement(String locatorType, Keys key) {
		Actions action = new Actions(driver);
		action.sendKeys(getWebElement(locatorType), key).perform();
	}

	public void pressKeyToElement(String locatorType, Keys key, String... dynamicValues) {
		Actions action = new Actions(driver);
		action.sendKeys(getWebElement(getDynamicXpath(locatorType, dynamicValues)), key).perform();
	}

	public Object executeForBrowser(String javaScript) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText(WebDriver driver) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	public boolean areExpectedTextInInnerText(String textExpected) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		String textActual = (String) jsExecutor
				.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage(WebDriver driver) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(String url) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	public void highlightElement(String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getWebElement(locatorType);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				"border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				originalStyle);
	}

	public void highlightElement(String locatorType, String... dynamicValues) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getWebElement(getDynamicXpath(locatorType, dynamicValues));
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				"border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				originalStyle);
	}

	public void clickToElementByJS(String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", getWebElement(locatorType));
	}

	public void clickToElementByJS(String locatorType, String... dynamicValues) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();",
				getWebElement(getDynamicXpath(locatorType, dynamicValues)));
	}

	public void scrollToElement(String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getWebElement(locatorType));
	}

	public void sendkeyToElementByJS(String locator, String value) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getWebElement(locator));
	}

	public void removeAttributeInDOM(String locatorType, String attributeRemove) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');",
				getWebElement(locatorType));
	}

	public boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};

		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};

		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}

	public WebElement getShadowDOM(String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = (WebElement) jsExecutor.executeScript("return arguments[0].shadowRoot;",
				getWebElement(locatorType));
		return element;
	}

	public String getElementValidationMessage(String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;",
				getWebElement(locatorType));
	}

	public boolean isImageLoaded(String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		boolean status = (boolean) jsExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
				getWebElement(locatorType));
		if (status) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isImageLoaded(String locatorType, String... dynamicValues) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		boolean status = (boolean) jsExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
				getWebElement(getDynamicXpath(locatorType, dynamicValues)));
		if (status) {
			return true;
		} else {
			return false;
		}
	}

	public void waitForElementVisible(String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(locatorType)));
	}

	public void waitForElementVisible(String locatorType, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions
				.visibilityOfElementLocated(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}

	public void waitForAllElementVisible(String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(locatorType)));
	}

	public void waitForAllElementVisible(String locatorType, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}

	public void waitForElementInvisible(String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locatorType)));
	}

	/*
	 * Wait for element undisplayed in DOM or not in DOM and override implicit
	 * timeout
	 */
	public void waitForElementUndisplayed(String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, shortTimeout);
		overrideImplicitTimeout(shortTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locatorType)));
		overrideImplicitTimeout(longTimeout);
	}

	public void waitForElementInvisible(String locatorType, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions
				.invisibilityOfElementLocated(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}

	public void waitForAllElementInvisible(String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getListWebElement(locatorType)));
	}

	public void waitForAllElementInvisible(String locatorType, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions
				.invisibilityOfAllElements(getListWebElement(getDynamicXpath(locatorType, dynamicValues))));
	}

	public void waitForElementClickable(String locatorType) {
		// System.out.println("Driver at waitForElementClickable = " +
		// driver.toString());
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByLocator(locatorType)));
	}

	public void waitForElementClickable(String locatorType, String... dynamicValues) {
		// System.out.println("Driver at waitForElementClickable = " +
		// driver.toString());
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(
				ExpectedConditions.elementToBeClickable(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}

	// Nếu dùng cho app khac thi sua BasePageUI_JQueryLoad.UPLOAD_FILE
//	public void uploadMultipleFiles(String... fileNames) {
//		// Duong dan cua thu muc uploadFiles: Windows/ MAC/ Linux
//		// String filePath = System.getProperty("user.dir") + "\\uploadFiles\\";
//		String filePath = GlobalConstants.getGlobalConstants().getUploadFile();
//		String fullFileName = "";
//		for (String file : fileNames) {
//			fullFileName = fullFileName + filePath + file + "\n";
//		}
//		fullFileName = fullFileName.trim();
//		getWebElement(BasePageUI_JQueryUpload.UPLOAD_FILE).sendKeys(fullFileName);
//	}

//-------------- For a specific Business --------------------------

	// Tối ưu ở bài học Level_07_Switch_Page
	public UserAddressPageObject openAddressPage(WebDriver driver) {
		waitForElementClickable(BasePageUI_NopCommerce.ADDRESS_LINK);
		clickToElement(BasePageUI_NopCommerce.ADDRESS_LINK);
		return PageGeneratorManager.getUserAddressPage(driver);
	}

	public UserMyProductReviewPageObject openMyProductReviewPage(WebDriver driver) {
		waitForElementClickable(BasePageUI_NopCommerce.MY_PRODUCT_REVIEW_LINK);
		clickToElement(BasePageUI_NopCommerce.MY_PRODUCT_REVIEW_LINK);
		return PageGeneratorManager.getUserMyProductReviewPage(driver);
	}

	public UserRewardPointPageObject openRewardPointPage(WebDriver driver) {
		waitForElementClickable(BasePageUI_NopCommerce.REWARD_POINT_LINK);
		clickToElement(BasePageUI_NopCommerce.REWARD_POINT_LINK);
		return PageGeneratorManager.getUserRewardPointPage(driver);
	}

	public UserCustomerInforPageObject openCustomerInforPage(WebDriver driver) {
		waitForElementClickable(BasePageUI_NopCommerce.CUSTOMER_INFOR_LINK);
		clickToElement(BasePageUI_NopCommerce.CUSTOMER_INFOR_LINK);
		return PageGeneratorManager.getUserCustomerInforPage(driver);
	}

	// Tối ưu ở bài học Level_09_Dynamic_Locator
	public BasePage openPagesAtMyAccountByName(String pageName) {
		waitForElementClickable(BasePageUI_NopCommerce.DYNAMIC_PAGES_AT_MY_ACCOUNT_AREA, pageName);
		clickToElement(BasePageUI_NopCommerce.DYNAMIC_PAGES_AT_MY_ACCOUNT_AREA, pageName);
		switch (pageName) {
		case "Customer info":
			return PageGeneratorManager.getUserCustomerInforPage(driver);
		case "Addresses":
			return PageGeneratorManager.getUserAddressPage(driver);
		case "My product reviews":
			return PageGeneratorManager.getUserMyProductReviewPage(driver);
		case "Reward points":
			return PageGeneratorManager.getUserRewardPointPage(driver);
		default:
		}
		throw new RuntimeException("Invalid page name at My Account area.");
	}

	// Pattern Object
	public void openPagesAtMyAccountByPageName(String pageName) {
		waitForElementClickable(BasePageUI_NopCommerce.DYNAMIC_PAGES_AT_MY_ACCOUNT_AREA, pageName);
		clickToElement(BasePageUI_NopCommerce.DYNAMIC_PAGES_AT_MY_ACCOUNT_AREA, pageName);
	}

	/**
	 * Enter to dynamic Textbox by ID
	 * <ul>
	 * <li>Rest Parameter</li>
	 * <li>Textbox by ID</li>
	 * </ul>
	 *
	 * @author Dam DM
	 * @param driver
	 * @param textboxID
	 * @param value
	 */
	public void inputToTextboxByID(String textboxID, String value) {
		waitForAllElementVisible(BasePageUI_NopCommerce.DYNAMIC_TEXTBOX_BY_ID, textboxID);
		sendkeyToElement(BasePageUI_NopCommerce.DYNAMIC_TEXTBOX_BY_ID, value, textboxID);
	}

	/**
	 * Click to dynamic Button by text
	 *
	 * @author Dam DM
	 * @param driver
	 * @param buttonText
	 */
	public void clickToButtonByText(String buttonText) {
		waitForElementClickable(BasePageUI_NopCommerce.DYNAMIC_BUTTON_BY_TEXT, buttonText);
		clickToElement(BasePageUI_NopCommerce.DYNAMIC_BUTTON_BY_TEXT, buttonText);
	}

	/**
	 * Select item in dropdown by Name attribute
	 *
	 * @param driver
	 * @param dropdownAttributeName
	 * @param itemValue
	 */
	public void selectToDropdownByName(String dropdownAttributeName, String itemValue) {
		waitForElementClickable(BasePageUI_NopCommerce.DYNAMIC_DROPDOWN_BY_NAME, dropdownAttributeName);
		selectItemInDefaultDropdown(BasePageUI_NopCommerce.DYNAMIC_DROPDOWN_BY_NAME, itemValue,
				dropdownAttributeName);
	}

	/**
	 * Click to dynamic radio button by label name
	 * 
	 * @param driver
	 * @param radioButtonLabelName
	 */
	public void clickToRadioButtonByLabel(String radioButtonLabelName) {
		waitForElementClickable(BasePageUI_NopCommerce.DYNAMIC_RADIO_BUTTON_BY_LABEL, radioButtonLabelName);
		checkToDefaultCheckboxOrRadio(BasePageUI_NopCommerce.DYNAMIC_RADIO_BUTTON_BY_LABEL,
				radioButtonLabelName);
	}

	/**
	 * Click to dynamic checkbox by label name
	 *
	 * @param driver
	 * @param checkboxLabelName
	 */
	public void clickToCheckboxByLabel(String checkboxLabelName) {
		waitForElementClickable(BasePageUI_NopCommerce.DYNAMIC_CHECKBOX_BY_LABEL, checkboxLabelName);
		checkToDefaultCheckboxOrRadio(BasePageUI_NopCommerce.DYNAMIC_CHECKBOX_BY_LABEL, checkboxLabelName);
	}

	/**
	 * Get value in textbox by textboxID
	 *
	 * @param driver
	 * @param textboxID
	 * @return
	 */
	public String getTextboxValueByID(String textboxID) {
		waitForElementVisible(BasePageUI_NopCommerce.DYNAMIC_TEXTBOX_BY_ID, textboxID);
		return getElementAttribute(BasePageUI_NopCommerce.DYNAMIC_TEXTBOX_BY_ID, "value", textboxID);
	}

	// Level_08_Switch_Role
	public UserHomePageObject clickToLogoutLinkAtUserPage(WebDriver driver) {
		waitForElementClickable(BasePageUI_NopCommerce.LOGOUT_LINK_AT_USER);
		clickToElement(BasePageUI_NopCommerce.LOGOUT_LINK_AT_USER);
		return PageGeneratorManager.getUserHomePage(driver);
	}

	public AdminLoginPageObject clickToLogoutLinkAtAdminPage(WebDriver driver) {
		waitForElementClickable(BasePageUI_NopCommerce.LOGOUT_LINK_AT_ADMIN);
		clickToElement(BasePageUI_NopCommerce.LOGOUT_LINK_AT_ADMIN);
		return PageGeneratorManager.getAdminLoginPage(driver);
	}

	// wordpress
//	public UserHomePO openEndUserSite(String endUserUrl) {
//		openPageUrl(endUserUrl);
//		return pageObjects.wordpress.PageGeneratorManager.getUserHomePage(driver);
//	}
//
//	public AdminDashboardPO openAdminSite(String adminUrl) {
//		openPageUrl(adminUrl);
//		return pageObjects.wordpress.PageGeneratorManager.getAdminDashboardPage(driver);
//	}

	// saucelab
	public boolean isDataFloatSortedAscending(String locatorType) {
		// Khai báo 1 Array List
		ArrayList<Float> arrayList = new ArrayList<Float>();

		// Tìm tất cả element matching vs điều kiện (Name/ Price..)
		List<WebElement> elementList = driver.findElements(getByLocator(locatorType));

		// Lấy text của từng element add vào Array List
		for (WebElement element : elementList) {
			arrayList.add(Float.parseFloat(element.getText().replace("$", "").trim()));
		}

		System.out.println("-----Dữ liệu trên UI-----");
		for (Float name : arrayList) {
			System.out.println(name);
		}

		// Copy qua 1 array list mới để SORT trong Code
		ArrayList<Float> sortedList = new ArrayList<Float>();
		for (Float child : arrayList) {
			sortedList.add(child);
		}

		// Thực hiện SORT ASC
		Collections.sort(sortedList);

		System.out.println("-----Dữ liệu đã SORT ASC trong code-----");
		for (Float name : sortedList) {
			System.out.println(name);
		}
		// Verify 2 array bằng nhau - nếu dữ liệu sort trên UI ko chính xác thì kết quả
		// trả về sai
		return sortedList.equals(arrayList);
	}

	public boolean isDataFloatSortedDescending(String locatorType) {
		// Khai báo 1 Array List
		ArrayList<Float> arrayList = new ArrayList<Float>();

		// Tìm tất cả element matching vs điều kiện (Name/ Price..)
		List<WebElement> elementList = driver.findElements(getByLocator(locatorType));

		// Lấy text của từng element add vào Array List
		for (WebElement element : elementList) {
			arrayList.add(Float.parseFloat(element.getText().replace("$", "").trim()));
		}

		System.out.println("-----Dữ liệu trên UI-----");
		for (Float name : arrayList) {
			System.out.println(name);
		}

		// Copy qua 1 array list mới để SORT trong Code
		ArrayList<Float> sortedList = new ArrayList<Float>();
		for (Float child : arrayList) {
			sortedList.add(child);
		}

		// Thực hiện SORT ASC
		Collections.sort(sortedList);

		System.out.println("-----Dữ liệu đã SORT ASC trong code-----");
		for (Float name : sortedList) {
			System.out.println(name);
		}

		// Reverse cái productSortList
		Collections.reverse(sortedList);

		System.out.println("-----Dữ liệu đã SORT DESC trong code-----");
		for (Float name : sortedList) {
			System.out.println(name);
		}

		// Verify 2 array bằng nhau - nếu dữ liệu sort trên UI ko chính xác thì kết quả
		// trả về sai
		return sortedList.equals(arrayList);
	}

	public boolean isDataDateSortedAscending(String locator) {
		ArrayList<Date> arrayList = new ArrayList<Date>();

		List<WebElement> elementList = driver.findElements(By.xpath(locator));

		for (WebElement element : elementList) {
			arrayList.add(convertStringToDate(element.getText()));
		}

		System.out.println("-----Dữ liệu trên UI-----");

		for (Date name : arrayList) {
			System.out.println(name);
		}

		ArrayList<Date> sortedList = new ArrayList<Date>();
		for (Date child : arrayList) {
			sortedList.add(child);
		}

		Collections.sort(sortedList);

		System.out.println("-----Dữ liệu đã SORT ASC trong code-----");
		for (Date name : sortedList) {
			System.out.println(name);
		}

		return sortedList.equals(arrayList);

	}

	public Date convertStringToDate(String datelnString) {
		datelnString = datelnString.replace(",", "");
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
		Date date = null;
		try {
			date = formatter.parse(datelnString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	private long longTimeout = GlobalConstants.getGlobalConstants().getLongTimeout();
	private long shortTimeout = GlobalConstants.getGlobalConstants().getShortTimeout();
}
