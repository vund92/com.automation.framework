/*
 * package reportConfig;
 * 
 * import org.openqa.selenium.OutputType; import
 * org.openqa.selenium.TakesScreenshot; import org.openqa.selenium.WebDriver;
 * import org.testng.ITestContext; import org.testng.ITestListener; import
 * org.testng.ITestResult;
 * 
 * import com.relevantcodes.extentreports.LogStatus;
 * 
 * import commons.BaseTest;
 * 
 * //Trong truong hop ko can chup hinh thi implements IReporter //Trong truong
 * hop can chup hinh thi implements ITestListener ==> class ExtentTestListener
 * danh rieng cho viec chup hinh public class ExtentTestListenerV2 implements
 * ITestListener {
 * 
 * @Override public void onStart(ITestContext context) {
 * 
 * }
 * 
 * @Override public void onFinish(ITestContext context) {
 * ExtentManagerV2.endTest(); ExtentManagerV2.getReporter().flush(); }
 * 
 * @Override public void onTestStart(ITestResult result) {
 * 
 * }
 * 
 * @Override public void onTestSuccess(ITestResult result) {
 * ExtentManagerV2.getTest().log(LogStatus.PASS, "Test Passed"); }
 * 
 * @Override public void onTestFailure(ITestResult result) { Object testClass =
 * result.getInstance(); WebDriver webDriver = ((BaseTest)
 * testClass).getDriverInstance(); String base64Screenshot =
 * "data:image/png;base64," + ((TakesScreenshot)
 * webDriver).getScreenshotAs(OutputType.BASE64);
 * ExtentManagerV2.getTest().log(LogStatus.FAIL, "Test Failed",
 * ExtentManagerV2.getTest().addBase64ScreenShot(base64Screenshot)); }
 * 
 * @Override public void onTestSkipped(ITestResult result) {
 * ExtentManagerV2.getTest().log(LogStatus.SKIP, "Test Skipped"); }
 * 
 * @Override public void onTestFailedButWithinSuccessPercentage(ITestResult
 * result) { }
 * 
 * }
 */