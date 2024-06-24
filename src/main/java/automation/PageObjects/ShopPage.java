package automation.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.AbstractComponents.AbstarctComponent;

public class ShopPage extends AbstarctComponent {

	WebDriver driver;

	public ShopPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "nav[class='woocommerce-breadcrumb'] a")
	WebElement homeOption;

	public HomePage clickOnHomeOption() {
		homeOption.click();

		return new HomePage(driver);
	}

}
