package automation.TestComponents;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import automation.resources.ExtentReportNG;

public class Listeners implements ITestListener {
	ExtentTest test;
	ExtentReports report = ExtentReportNG.getReportObject();
	ThreadLocal<ExtentTest> testThread = new ThreadLocal<ExtentTest>();

	@Override
	public void onTestStart(ITestResult result) {
		String testCaseName = result.getMethod().getMethodName();
		test = report.createTest(testCaseName);
		testThread.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String testCaseName = result.getMethod().getMethodName();
		testThread.get().log(Status.PASS, testCaseName);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testCaseName = result.getMethod().getMethodName();
		String screenshot = null;
		WebDriver driver = null;
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}

		try {
			screenshot = captureScreenshot(driver, testCaseName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		testThread.get().fail(result.getThrowable());
		testThread.get().addScreenCaptureFromPath(screenshot, testCaseName);

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish(ITestContext context) {
		report.flush();

	}

	public String captureScreenshot(WebDriver driver, String testCaseName) throws IOException {
		TakesScreenshot screen = (TakesScreenshot) driver;
		File source = screen.getScreenshotAs(OutputType.FILE);
		File destination = new File(System.getProperty("user.dir") + "/screenshots/" + testCaseName + ".png");
		FileUtils.copyFile(source, destination);

		return destination.getPath();
	}

}
