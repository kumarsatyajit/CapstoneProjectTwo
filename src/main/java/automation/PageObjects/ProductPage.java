package automation.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.AbstractComponents.AbstarctComponent;
import automation.UserExceptions.NotDisplayException;
import automation.UserExceptions.NotPresentException;

public class ProductPage extends AbstarctComponent {
	WebDriver driver;

	public ProductPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[@type='submit']")
	WebElement addToBasketButton;

	@FindBy(css = "nav[class='woocommerce-breadcrumb'] a:first-of-type")
	WebElement homeOption;

	@FindBy(css = ".product_title.entry-title")
	WebElement productName;

	@FindBy(css = ".description_tab")
	WebElement descriptionSection;

	@FindBy(css = ".reviews_tab")
	WebElement reviewSection;

	@FindBy(xpath = "//div[@id='tab-description']//p")
	WebElement descriptionText;

	@FindBy(xpath = "//div[@id='comments']/p")
	WebElement noReviewsText;

	@FindBy(xpath = "//p[@class='stars']//a")
	List<WebElement> stars;

	@FindBy(css = "#comment")
	WebElement textArea;

	@FindBy(css = "#author")
	WebElement authorNameField;

	@FindBy(css = "#email")
	WebElement emailField;

	@FindBy(css = "#submit")
	WebElement submitButton;

	@FindBy(xpath = "//div[@class='description']/p")
	WebElement reviews;

	By reviewsBy = By.xpath("//div[@class='description']/p");

	public String clickOnAddToBasket() {
		waitForWebElementToBeAppear(addToBasketButton);
		addToBasketButton.click();

		return productName.getText().trim();
	}

	public void clickOnHomeOption() {
		homeOption.click();
	}

	public boolean checkDescriptionIsPresent() {
		scrollToWebElement(descriptionSection);
		getActionsObject().moveToElement(descriptionSection).click().build().perform();
		waitForWebElementToBeAppear(descriptionText);
		String description = descriptionText.getText().trim();
		return description.length() > 0;
	}

	public void addReview(String userReview, String authorName, String email) {
		scrollToWebElement(reviewSection);
		getActionsObject().moveToElement(reviewSection).click().build().perform();
		boolean noReviewPresent = noReviewsText.getText().trim().equals("There are no reviews yet.");
		if (noReviewsText.isDisplayed() && noReviewPresent) {
			int ratings = getRandomNumber();
			List<WebElement> starList = stars;
			for (int i = 0; i < ratings; i++) {
				WebElement star = starList.get(i);
				getActionsObject().moveToElement(star).click().build().perform();
			}
			textArea.sendKeys(userReview);
			authorNameField.sendKeys(authorName);
			emailField.sendKeys(email);
			submitButton.click();
		}
	}

	public boolean checkReviewPresence() throws NotPresentException, NotDisplayException {
		boolean isPresent = isPresent(reviewsBy);
		boolean isVisible = reviews.isDisplayed();

		if (isPresent) {
			if (isVisible) {
				return true;
			} else
				throw new NotDisplayException(
						"This WebElement '//div[@class='description']/p':review- Not display in web page");
		} else
			throw new NotPresentException("This WebElement '//div[@class='description']/p':review- Not presnt in DOM");
	}
}
