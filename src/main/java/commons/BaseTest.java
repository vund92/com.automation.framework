package commons;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;

import factoryBrowser.BrowserList;
import factoryEnvironment.BrowserstackFactory;
import factoryEnvironment.CrossbrowserFactory;
import factoryEnvironment.EnvironmentList;
import factoryEnvironment.GridFactory;
import factoryEnvironment.LambdaFactory;
import factoryEnvironment.LocalFactory;
import factoryEnvironment.SaucelabFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.PropertiesConfig;

public class BaseTest {
	//protected WebDriver driver;
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	protected final Log log;

	@BeforeSuite
	public void initBeforeSuite() {
		deleteAllureReport();
	}

	protected BaseTest() {
		log = LogFactory.getLog(getClass());
	}
	
	protected WebDriver getBrowserDriver(String envName, String serverName, String  browserName, String ipAddress, String portNumber, String osName, String osVersion) {
		switch (envName) {
		case "local":
			driver.set(new LocalFactory(browserName).createDriver());
			break;
		case "grid":
			driver.set(new GridFactory(browserName, ipAddress, portNumber).createDriver());
			break;
		case "browserStack":
			driver.set(new BrowserstackFactory(browserName, osName, osVersion).createDriver());
			break;
		case "saucelab":
			driver.set(new SaucelabFactory(browserName, osName).createDriver());
			break;
		case "crossBrowser":
			driver.set(new CrossbrowserFactory(browserName, osName).createDriver());
			break;
		case "lambda":
			driver.set(new LambdaFactory(browserName, osName).createDriver());
			break;
		default:
			driver.set(new LocalFactory(browserName).createDriver());
			break;
		}
		
		//driver.get().manage().timeouts().implicitlyWait(GlobalConstants.getGlobalConstants().getLongTimeout(), TimeUnit.SECONDS);
		driver.get().manage().timeouts().implicitlyWait(PropertiesConfig.getFileConfigReader().getLongTimeout(), TimeUnit.SECONDS);
		driver.get().manage().window().maximize();
		driver.get().get(getEnvironmentUrl(serverName));
		return driver.get();
	}

	public WebDriver getDriverInstance() {
		return driver.get();
	}
	
	protected WebDriver getBrowserDriver(String browserName) {
		if (browserName.equals("firefox")) {
			// System.setProperty("webdriver.gecko.driver", projectPath +
			// "\\browserDrivers\\geckodriver.exe");
			WebDriverManager.firefoxdriver().setup();
			driver.set(new FirefoxDriver());
		} else if (browserName.equals("h_firefox")) {
			// System.setProperty("webdriver.gecko.driver", projectPath +
			// "\\browserDrivers\\geckodriver.exe");
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("--headless");
			options.addArguments("window-size=1920x1080");
			driver.set(new FirefoxDriver(options));
		} else if (browserName.equals("chrome")) {
			// System.setProperty("webdriver.chrome.driver", projectPath +
			// "\\browserDrivers\\chromedriver.exe");
			WebDriverManager.chromedriver().setup();
			driver.set(new ChromeDriver());
		} else if (browserName.equals("h_chrome")) {
			// System.setProperty("webdriver.chrome.driver", projectPath +
			// "\\browserDrivers\\chromedriver.exe");
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			options.addArguments("window-size=1920x1080");
			driver.set(new ChromeDriver(options));
		} else if (browserName.equals("edge")) {
			// System.setProperty("webdriver.edge.driver", projectPath +
			// "\\browserDrivers\\msedgedriver.exe");
			WebDriverManager.edgedriver().setup();
			driver.set(new EdgeDriver());
		} else if (browserName.equals("opera")) {
			// System.setProperty("webdriver.opera.driver", projectPath +
			// "\\browserDrivers\\operadriver.exe");
			driver.set(WebDriverManager.operadriver().create());
		} else if (browserName.equals("coccoc")) {
			// Cốc Cốc brwoser trừ đi 5-6 version ra chromedriver
			// System.setProperty("webdriver.chrome.driver", projectPath +
			// "\\browserDrivers\\chromedriver_113.exe");
			WebDriverManager.chromedriver().driverVersion("113.0.5672.63").setup();
			ChromeOptions options = new ChromeOptions();
			options.setBinary("C:\\Program Files\\CocCoc\\Browser\\Application\\browser.exe");
			driver.set(new ChromeDriver(options));
		} else if (browserName.equals("brave")) {
			// Brave browser version nào dùng chromedriver version đó
			// System.setProperty("webdriver.chrome.driver", projectPath +
			// "\\browserDrivers\\chromedriver.exe");
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
			driver.set(new ChromeDriver(options));
		} else {
			throw new RuntimeException("Browser name invalid.");
		}

		driver.get().manage().timeouts().implicitlyWait(GlobalConstants.getGlobalConstants().getLongTimeout(), TimeUnit.SECONDS);
		driver.get().manage().window().maximize();
		driver.get().get(GlobalConstants.getGlobalConstants().getPortalDevUrl());
		return driver.get();
	}

	protected WebDriver getBrowserDriverWithEnvironmentName(String browserName, String environmentName) {
		BrowserList browserList = BrowserList.valueOf(browserName.toUpperCase());

		if (browserList == BrowserList.FIREFOX) {
			WebDriverManager.firefoxdriver().setup();
			
			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, GlobalConstants.getGlobalConstants().getProjectPath() + "//browserLogs//FirefoxLog.log");
			
			FirefoxOptions options = new FirefoxOptions();
			options.setAcceptInsecureCerts(false);
			driver.set(new FirefoxDriver());
		} else if (browserList == BrowserList.H_FIREFOX) {
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("--headless");
			options.addArguments("window-size=1920x1080");
			driver.set(new FirefoxDriver(options));
		} else if (browserList == BrowserList.CHROME) {
			WebDriverManager.chromedriver().setup();
			driver.set(new ChromeDriver());
		} else if (browserList == BrowserList.H_CHROME) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			options.addArguments("window-size=1920x1080");
			driver.set(new ChromeDriver(options));
		} else if (browserList == BrowserList.EDGE) {
			WebDriverManager.edgedriver().setup();
			driver.set(new EdgeDriver());
		} else if (browserList == BrowserList.IE) {
			WebDriverManager.iedriver().arch32().setup();
			driver.set(new InternetExplorerDriver());
		} else if (browserList == BrowserList.OPERA) {
			WebDriverManager.operadriver().setup();
			driver.set(new OperaDriver());
		} else if (browserList == BrowserList.COC_COC) {
			// Cốc Cốc brwoser trừ đi 5-6 version ra chromedriver
			WebDriverManager.chromedriver().driverVersion("93.0.4577.63").setup();
			ChromeOptions options = new ChromeOptions();
			if (GlobalConstants.getGlobalConstants().getOsName().startsWith("Windows")) {
				options.setBinary("C:\\Program Files (x86)\\ CocCoc\\Browser\\Application\\browser.exe");
			} else {
				options.setBinary("....");
			}
			driver.set(new ChromeDriver(options));

		} else if (browserList == BrowserList.BRAVE) {
			// Brave browser version nào dùng chromedriver version đó
			WebDriverManager.chromedriver().driverVersion("95.0.4638.17").setup();
			ChromeOptions options = new ChromeOptions();
			options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
			driver.set(new ChromeDriver(options));
		} else {
			// throw new BrowserNotSupport(browserName);
		}
		
		driver.get().manage().timeouts().implicitlyWait(GlobalConstants.getGlobalConstants().getLongTimeout(), TimeUnit.SECONDS);
		driver.get().manage().window().maximize();
		//driver.get(getEnvironmentUrl(environmentName));
		driver.get().get(getEnvironmentUrl(environmentName));
		return driver.get();
	}

	protected WebDriver getBrowserDriver(String browserName, String appUrl) {
		if (browserName.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			
			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, GlobalConstants.getGlobalConstants().getProjectPath() + "//browserLogs//FirefoxLog.log");
			
			FirefoxOptions options = new FirefoxOptions();
			options.setAcceptInsecureCerts(false);
			driver.set(new FirefoxDriver(options));
		} else if (browserName.equals("h_firefox")) {
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("--headless");
			options.addArguments("window-size=1920x1080");
			driver.set(new FirefoxDriver(options));
		} else if (browserName.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.setAcceptInsecureCerts(false);
			driver.set(new ChromeDriver(options));
		} else if (browserName.equals("h_chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			options.addArguments("window-size=1920x1080");
			driver.set(new ChromeDriver(options));
		} else if (browserName.equals("edge")) {
			WebDriverManager.edgedriver().setup();
			driver.set(new EdgeDriver());
		} else if (browserName.equals("ie")) {
			WebDriverManager.iedriver().arch32().setup();
			driver.set(new InternetExplorerDriver());
		} else if (browserName.equals("opera")) {
			driver.set(WebDriverManager.operadriver().create());
		} else if (browserName.equals("coccoc")) {
			// Cốc Cốc brwoser trừ đi 5-6 version ra chromedriver
			WebDriverManager.chromedriver().driverVersion("93.0.4577.63").setup();
			ChromeOptions options = new ChromeOptions();
			options.setBinary("C:\\Program Files (x86)\\CocCoc\\Browser\\Application\\browser.exe");
			driver.set(new ChromeDriver(options));
		} else if (browserName.equals("brave")) {
			// Brave browser version nào dùng chromedriver version đó
			WebDriverManager.chromedriver().driverVersion("95.0.4638.17").setup();
			ChromeOptions options = new ChromeOptions();
			options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
			driver.set(new ChromeDriver(options));
		} else {
			throw new RuntimeException("Browser name invalid.");
		}
		driver.get().manage().timeouts().implicitlyWait(GlobalConstants.getGlobalConstants().getLongTimeout(), TimeUnit.SECONDS);
		driver.get().manage().window().maximize();
		driver.get().get(appUrl);
		return driver.get();
	}

	protected WebDriver getBrowserDriverGrid(String browserName, String appUrl, String osName, String ipAddress, String portNumber) {
		DesiredCapabilities capability = null;
		Platform platform = null;

		if (osName.contains("windows")) {
			platform = Platform.WINDOWS;
		} else {
			platform = Platform.MAC;
		}

		switch (browserName) {
			case "firefox" :
				capability = DesiredCapabilities.firefox();
				capability.setBrowserName("firefox");
				capability.setPlatform(platform);

				FirefoxOptions fOptions = new FirefoxOptions();
				fOptions.merge(capability);
				break;
			case "chrome" :
				capability = DesiredCapabilities.chrome();
				capability.setBrowserName("chrome");
				capability.setPlatform(platform);

				ChromeOptions cOptions = new ChromeOptions();
				cOptions.merge(capability);
				break;
			case "edge" :
				capability = DesiredCapabilities.edge();
				capability.setBrowserName("edge");
				capability.setPlatform(platform);

				EdgeOptions eOptions = new EdgeOptions();
				eOptions.merge(capability);
				break;
			case "opera" :
				capability = DesiredCapabilities.opera(); //kệ nó nếu nó bị deprecated
				capability.setBrowserName("opera");
				capability.setPlatform(platform);

				OperaOptions oOptions = new OperaOptions();
				oOptions.merge(capability);
				break;
			default :
				throw new RuntimeException("Browser is not valid!");
		}

		try {
			driver.set(new RemoteWebDriver(new URL(String.format("http://%s:%s/wd/hub", ipAddress, portNumber)), capability));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		driver.get().manage().timeouts().implicitlyWait(GlobalConstants.getGlobalConstants().getLongTimeout(), TimeUnit.SECONDS);
		driver.get().manage().window().maximize();
		driver.get().get(appUrl);
		return driver.get();

	}

	protected String getEnvironmentUrl(String serverName) {
		String envUrl = null;
		EnvironmentList environment = EnvironmentList.valueOf(serverName.toUpperCase());

		switch (environment) {
		case DEV:
			envUrl = "https://demo.nopcommerce.com/";
			break;
		case TESTING:
			envUrl = "https://testing.nopcommerce.com/";
			break;
		case STAGING:
			envUrl = "https://staging.nopcommerce.com/";
			break;
		case PRE_PROD:
			envUrl = "https://pre-prod.nopcommerce.com/";
			break;
		case PROD:
			envUrl = "https://prod.nopcommerce.com/";
			break;
		default:
			envUrl = null;
			break;
		}
		
		return envUrl;
	}
	 
	protected boolean verifyTrue(boolean condition) {
		boolean pass = true;
		try {
			Assert.assertTrue(condition);
			log.info(" -------------------------- PASSED -------------------------- ");
		} catch (Throwable e) {
			log.info(" -------------------------- FAILED -------------------------- ");
			pass = false;

			// Add lỗi vào ReportNG
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}

	protected boolean verifyFalse(boolean condition) {
		boolean pass = true;
		try {
			Assert.assertFalse(condition);
			log.info(" -------------------------- PASSED -------------------------- ");
		} catch (Throwable e) {
			log.info(" -------------------------- FAILED -------------------------- ");
			pass = false;

			// Add lỗi vào ReportNG
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}

	protected boolean verifyEquals(Object actual, Object expected) {
		boolean pass = true;
		try {
			Assert.assertEquals(actual, expected);
			log.info(" -------------------------- PASSED -------------------------- ");
		} catch (Throwable e) {
			log.info(" -------------------------- FAILED -------------------------- ");
			pass = false;

			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}

	protected int generateFakeNumber() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}

	public void deleteAllureReport() {
		try {
			String pathFolderDownload = GlobalConstants.getGlobalConstants().getProjectPath() + "/allure-results";
			File file = new File(pathFolderDownload);
			File[] listOfFiles = file.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					// System.out.println(listOfFiles[i].getName());
					new File(listOfFiles[i].toString()).delete();
				}
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	protected void closeBrowserDriver(String envName) {
		if(envName.equals("local") || envName.equals("grid")) {
			String cmd = null;
			try {
				String osName = System.getProperty("os.name").toLowerCase();
				log.info("OS name = " + osName);

				String driverInstanceName = driver.get().toString().toLowerCase(); // thread local
				log.info("Driver instance name = " + driverInstanceName);

				String browserDriverName = null;

				if (driverInstanceName.contains("chrome")) {
					browserDriverName = "chromedriver";
				} else if (driverInstanceName.contains("internetexplorer")) {
					browserDriverName = "IEDriverServer";
				} else if (driverInstanceName.contains("firefox")) {
					browserDriverName = "geckodriver";
				} else if (driverInstanceName.contains("edge")) {
					browserDriverName = "msedgedriver";
				} else if (driverInstanceName.contains("opera")) {
					browserDriverName = "operadriver";
				} else {
					browserDriverName = "safaridriver";
				}

				if (osName.contains("window")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq " + browserDriverName + "*\"";
				} else {
					cmd = "pkill " + browserDriverName;
				}

				if (driver.get() != null) {
					driver.get().manage().deleteAllCookies();
					driver.get().quit();
					
					driver.remove(); //khi apply Threadlocal thì cần có thêm dòng này
				}
			} catch (Exception e) {
				log.info(e.getMessage());
			} finally {
				try {
					Process process = Runtime.getRuntime().exec(cmd);
					process.waitFor();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected String getCurrentDate() {
		DateTime nowUTC = new DateTime(DateTimeZone.UTC);
		int day = nowUTC.getDayOfMonth();
		if (day < 10) {
			String dayValue = "0" + day;
			return dayValue;
		}
		return String.valueOf(day);
	}

	protected String getCurrentMonth() {
	DateTime now = new DateTime (DateTimeZone.UTC);
	int month = now.getMonthOfYear();
	if (month< 10) {
	String monthValue = "0" + month;
	return monthValue;
	}
	return String.valueOf(month);
	}

	protected String getCurrentYear() {
		DateTime now = new DateTime(DateTimeZone.UTC);
		return now.getYear() + "";
	}

	protected String getCurrentDay() {
	return getCurrentDate() + "/" + getCurrentMonth() + "/" + getCurrentYear();
	}
	
	protected void showBrowserConsoleLogs(WebDriver driver) {
		if (driver.toString().contains("chrome") || driver.toString().contains("edge")) {
			LogEntries logs = driver.manage().logs().get("browser");
			List<LogEntry> logList = logs.getAll();
			for (LogEntry logging : logList) {
				if (logging.getLevel().toString().toLowerCase().contains("error")) {
					log.info("---------------" + logging.getLevel().toString() + "---------------\n"
							+ logging.getMessage());
				}
			}
		}
	}
}
