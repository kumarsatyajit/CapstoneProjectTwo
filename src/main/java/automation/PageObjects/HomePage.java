package automation.PageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.AbstractComponents.AbstarctComponent;

public class HomePage extends AbstarctComponent {

	WebDriver driver;

	public HomePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "ul[class='main-nav'] li a")
	List<WebElement> menuButtons;

	@FindBy(css = "div[class*='n2-ss-slide n2-ss-canvas']")
	List<WebElement> sliders;

	@FindBy(css = "div[class*='module module-text text'] h2")
	WebElement newArrivalText;

	@FindBy(css = "li[class*='product type-product status-publish has-post-thumbnail']")
	List<WebElement> newArrivals;

	By newArrivalImgBy = By.cssSelector("a img");

	By newArrivalNameBy = By.cssSelector("a h3");

	@FindBy(css = ".wpmenucart-icon-shopping-cart-0")
	WebElement cartButton;

	public ShopPage clickOnMenuOption(String option) {
		WebElement element = menuButtons.stream()
				.filter(button -> button.getText().toLowerCase().equalsIgnoreCase(option)).findFirst().orElse(null);
		element.click();

		return new ShopPage(driver);
	}

	public int getSlidersCount() {
		return sliders.size();
	}

	public int getNewArrivalsCount() {
		scrollToWebElement(newArrivalText);
		return newArrivals.size();
	}

	public List<String> addNewArrivalsToBasket() {
		List<String> addProducts = new ArrayList<String>();
		int count = getNewArrivalsCount();
		for (int i = 0; i < count; i++) {
			WebElement arrival = newArrivals.get(i);
			arrival.findElement(newArrivalImgBy).click();
			ProductPage productPage = new ProductPage(driver);
			String productName = productPage.clickOnAddToBasket();
			productPage.clickOnHomeOption();
			addProducts.add(productName);
		}

		return addProducts;
	}

	public CartPage clickOnCartIcon() {
		cartButton.click();
		return new CartPage(driver);
	}

	public List<Boolean> validateArrivalsDescription(List<String> productNames) {
		List<Boolean> descriptionPresent = new ArrayList<Boolean>();
		int newArrivalsCount = getNewArrivalsCount();
		for (int i = 0; i < newArrivalsCount; i++) {
			WebElement arrival = newArrivals.get(i);
			String title = arrival.findElement(newArrivalNameBy).getText().trim();
			if (productNames.contains(title)) {
				arrival.findElement(newArrivalImgBy).click();
				ProductPage productPage = new ProductPage(driver);
				boolean isPresent = productPage.checkDescriptionIsPresent();
				descriptionPresent.add(isPresent);
				scrollToTop();
				productPage.clickOnHomeOption();
			}
		}
		return descriptionPresent;

	}
}
