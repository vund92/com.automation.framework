package commons;

import java.io.File;

import lombok.Getter;
import lombok.Setter;

@Getter
public class GlobalConstants {

	private static GlobalConstants globalInstance;

	private GlobalConstants() {
	}

	public static synchronized GlobalConstants getGlobalConstants() {
		if (globalInstance == null) {
			globalInstance = new GlobalConstants();
		}
		return globalInstance;
	}

	private final String portalDevUrl = "https://demo.nopcommerce.com/";
	private final String adminDevUrl = "https://admin-demo.nopcommerce.com";

	private final String portalTestingUrl = "https://demo.nopcommerce.com/";
	private final String adminTestingUrl = "https://admin-demo.nopcommerce.com";

	private final String projectPath = System.getProperty("user.dir");
	private final String osName = System.getProperty("os.name");
	private final String uploadFile = projectPath + File.separator + "uploadFiles" + File.separator;
	private final String downloadFolderPath = projectPath + File.separator + "downloadFiles";
	private final String browserLogPath = projectPath + File.separator + "browserLogs" + File.separator;
	private final String dragDropHtml5 = projectPath + File.separator + "dragDropHTML5";
	private final String autoItScript = projectPath + File.separator + "autoIT";
	private final String reportingScreenshot = projectPath + File.separator + "reportNGScreenShots"
			+ File.separator;
	private final String extentReportPath = projectPath + File.separator + "extentReportV2" + File.separator;

	private final String dbDevUrl = "32.18.252.185:9860";
	private final String dbDevUser = "automationfc";
	private final String dbDevPass = "P@ssworld1!";

	private final String dbTestUrl = "32.18.195.23:9860";
	private final String dbTestUser = "automationfc";
	private final String dbTestPass = "P@ssworld1!";

	private final long shortTimeout = 5;
	private final long longTimeout = 30;
	private final long retryTestFail = 3;

	private final String javaVersion = System.getProperty("java.version");

	private final String browserUsername = "automationfc1";
	private final String browserAutomateKey = "HzcRC4Q1fzuQhRJYSkhz";
	private final String browserStackUrl = "https://" + browserUsername + ":" + browserAutomateKey
			+ "@hub-cloud.browserstack.com/wd/hub";

	private final String sauceUsername = "automationfc";
	private final String sauceAutomateKey = "f8117ac5-9793-4f8d-89ca-6a3c1d7216a5";
	private final String sauceUrl = "https://" + sauceUsername + ":" + sauceAutomateKey
			+ "@ondemand.eu-central-1.saucelabs.com:443/wd/hub";

	private final String crossUsername = "damdm@vntesters.com".replaceAll("@", "%40");
	private final String crossAccessKey = "uad436a40401a0c2";
	private final String crossUrl = "http://" + crossUsername + ":" + crossAccessKey
			+ "@hub.crossbrowsertesting.com:80/wd/hub";

	private final String lambdaUsername = "automationfc.com";
	private final String lambdaAccessKey = "LSMxg11SndU7qjktdaOmTOtwcGnrKkN1G3eWptwWYVAZp8Ewia";
	private final String lambdaUrl = "https://" + lambdaUsername + ":" + lambdaAccessKey
			+ "@hub.lambdatest.com/wd/hub";

}
