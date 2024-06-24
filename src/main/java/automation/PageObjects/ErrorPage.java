package automation.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.AbstractComponents.AbstarctComponent;

public class ErrorPage extends AbstarctComponent {

	WebDriver driver;

	public ErrorPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='wp-die-message']/p")
	WebElement duplicateReviewMessage;

	@FindBy(xpath = "//body/p/a")
	WebElement backButton;

	public String getTheWarningMessage() {
		String message = duplicateReviewMessage.getText().trim();
		return message;
	}

	public ProductPage clickOnBackButton() {
		getActionsObject().moveToElement(backButton).click().build().perform();
		return new ProductPage(driver);
	}

}
