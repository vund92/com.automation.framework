package factoryBrowser;

import java.util.Collections;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import commons.GlobalConstants;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeDriverManager implements BrowserFactory {

	@Override
	public WebDriver getBrowserDriver() {
		WebDriverManager.chromedriver().setup();
		
		// Selenium 3.x
		ChromeOptions options = new ChromeOptions();
		//Setting Capability / Option here if you wish
		//options.addArguments("--incognito");
		//options.addArguments("--disable-infobars"); //bản cũ thì dùng cái này, bản mới dùng 2 dòng bên dưới
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		options.addArguments("--disable-notifications");
		options.addArguments("--disable-geolocation");
		
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>(); 
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", GlobalConstants.getGlobalConstants().getDownloadFolderPath()); 
		options.setExperimentalOption("prefs", chromePrefs);
		
		return new ChromeDriver(options);
	}

}
