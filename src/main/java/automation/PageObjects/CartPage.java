package automation.PageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.AbstractComponents.AbstarctComponent;

public class CartPage extends AbstarctComponent {
	WebDriver driver;

	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//tr[@class='cart_item']")
	List<WebElement> productRows;

	By productName = By.xpath(".//td[@class='product-name']/a");

	@FindBy(css = "ul[class='main-nav'] li a")
	List<WebElement> menuButtons;

	public List<String> getAddProductNames() {
		List<String> productNames = new ArrayList<String>();
		productRows.stream().forEach(row -> productNames.add(row.findElement(productName).getText().trim()));

		return productNames;
	}

	public ShopPage clickOnMenuOption(String option) {
		WebElement element = menuButtons.stream()
				.filter(button -> button.getText().toLowerCase().equalsIgnoreCase(option)).findFirst().orElse(null);
		element.click();

		return new ShopPage(driver);
	}

}
