package automation.tests;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import automation.PageObjects.CartPage;
import automation.PageObjects.HomePage;
import automation.PageObjects.ShopPage;
import automation.TestComponents.BaseTest;
import automation.UserExceptions.BrokenUrlException;
import automation.UserExceptions.InvalidBrowserException;
import automation.UserExceptions.InvalidURLException;

public class HomePageTestCases extends BaseTest {

	@Test
	public void homePageWithThreeSlidersOnly()
			throws IOException, InvalidBrowserException, BrokenUrlException, InvalidURLException {
		HomePage homePage = openUrl();
		ShopPage shopPage = homePage.clickOnMenuOption("shop");
		homePage = shopPage.clickOnHomeOption();
		int slidersCount = homePage.getSlidersCount();
		Assert.assertTrue(slidersCount == 3, "Sliders count = " + slidersCount + " (not 3)");
	}

	@Test
	public void homePageWithArrivalsOnly()
			throws IOException, InvalidBrowserException, BrokenUrlException, InvalidURLException {
		HomePage homePage = openUrl();
		ShopPage shopPage = homePage.clickOnMenuOption("shop");
		homePage = shopPage.clickOnHomeOption();
		int newArrivalsCount = homePage.getNewArrivalsCount();
		Assert.assertTrue(newArrivalsCount == 3, "New Arrivals count = " + newArrivalsCount + " (not 3)");
	}

	@Test
	public void homePageImagesInArrivalsShouldNavigate()
			throws IOException, InvalidBrowserException, BrokenUrlException, InvalidURLException {
		HomePage homePage = openUrl();
		ShopPage shopPage = homePage.clickOnMenuOption("shop");
		homePage = shopPage.clickOnHomeOption();
		int newArrivalsCount = homePage.getNewArrivalsCount();
		Assert.assertTrue(newArrivalsCount == 3, "New Arrivals count = " + newArrivalsCount + " (not 3)");
		List<String> addProducts = homePage.addNewArrivalsToBasket();
		CartPage cartPage = homePage.clickOnCartIcon();
		List<String> addProductNames = cartPage.getAddProductNames();
		addProducts.stream().forEach(product -> Assert.assertTrue(addProductNames.contains(product)));
	}

	@Test
	public void homePageArrivalsImagesDescription()
			throws IOException, InvalidBrowserException, BrokenUrlException, InvalidURLException {
		HomePage homePage = openUrl();
		ShopPage shopPage = homePage.clickOnMenuOption("shop");
		homePage = shopPage.clickOnHomeOption();
		int newArrivalsCount = homePage.getNewArrivalsCount();
		Assert.assertTrue(newArrivalsCount == 3, "New Arrivals count = " + newArrivalsCount + " (not 3)");
		List<String> addProducts = homePage.addNewArrivalsToBasket();
		CartPage cartPage = homePage.clickOnCartIcon();
		List<String> addProductNames = cartPage.getAddProductNames();
		addProducts.stream().forEach(product -> Assert.assertTrue(addProductNames.contains(product)));
		shopPage = cartPage.clickOnMenuOption("shop");
		homePage = shopPage.clickOnHomeOption();
		List<Boolean> arrivalsDescription = homePage.validateArrivalsDescription(addProductNames);
		arrivalsDescription.stream().forEach(isPresent -> Assert.assertTrue(isPresent));
	}

	// @Test
	public void homePageArrivalsImagesReviews()
			throws IOException, InvalidBrowserException, BrokenUrlException, InvalidURLException {
		HomePage homePage = openUrl();
		ShopPage shopPage = homePage.clickOnMenuOption("shop");
		homePage = shopPage.clickOnHomeOption();
		int newArrivalsCount = homePage.getNewArrivalsCount();
		Assert.assertTrue(newArrivalsCount == 3, "New Arrivals count = " + newArrivalsCount + " (not 3)");
		List<String> addProducts = homePage.addNewArrivalsToBasket();
		CartPage cartPage = homePage.clickOnCartIcon();
		List<String> addProductNames = cartPage.getAddProductNames();
		addProducts.stream().forEach(product -> Assert.assertTrue(addProductNames.contains(product)));
		shopPage = cartPage.clickOnMenuOption("shop");
		homePage = shopPage.clickOnHomeOption();
	}
}
