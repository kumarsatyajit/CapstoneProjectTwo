package automation.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import automation.PageObjects.HomePage;
import automation.TestComponents.BaseTest;
import automation.UserExceptions.BrokenUrlException;
import automation.UserExceptions.InvalidBrowserException;
import automation.UserExceptions.InvalidURLException;

public class SampleTest extends BaseTest {

	@Test
	public void landOnHomePage() throws IOException, InvalidBrowserException, BrokenUrlException, InvalidURLException {
		HomePage homePage = openUrl();
		String title = homePage.getTitle();
		Assert.assertTrue(title.equalsIgnoreCase("Automation Practice Site"));
	}

	@Test
	public void openGoogle() throws IOException, InvalidBrowserException {
		driver = initializeDriver();
		driver.get("https://www.google.com/");
	}
}
