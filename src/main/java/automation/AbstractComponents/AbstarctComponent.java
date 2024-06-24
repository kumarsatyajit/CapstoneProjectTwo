package automation.AbstractComponents;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstarctComponent {

	WebDriver driver;

	public AbstarctComponent(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriverWait getWaitObject(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		return wait;
	}

	public Actions getActionsObject() {
		Actions actions = new Actions(driver);
		return actions;
	}

	public JavascriptExecutor getJavascriptExecutorObject() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js;
	}

	public void waitForWebElementToBeAppear(WebElement elemet) {
		getWaitObject(driver).until(ExpectedConditions.visibilityOf(elemet));
	}

	public void waitForWebElementsToBeAppear(List<WebElement> elemet) {
		getWaitObject(driver).until(ExpectedConditions.visibilityOfAllElements(elemet));
	}

	public void waitForWebElementToBeAppear(By locator) {
		getWaitObject(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void waitForWebElementsToBeAppear(By locator) {
		getWaitObject(driver).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	public void waitForWebElementToBePresence(By locator) {
		getWaitObject(driver).until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public void waitForWebElementsToBePresence(By locator) {
		getWaitObject(driver).until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	public Select selectFromStaticDropdown(WebElement element) {
		Select dropdown = new Select(element);
		return dropdown;
	}

	public void clickOnWebElementUsingJavascript(WebElement element) {
		getJavascriptExecutorObject().executeScript("arguments[0].click()", element);
	}

	public void scrollToTop() {
		getJavascriptExecutorObject().executeScript("window.scrollTo(0,0)");
	}

	public void scrollToButtom() {
		getJavascriptExecutorObject().executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}

	public void scrollToWebElement(WebElement element) {
		getJavascriptExecutorObject().executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public String getPageSource() {
		return driver.getPageSource();
	}

	public int getRandomNumber() {
		Random random = new Random();
		int number = random.nextInt(5);
		return number + 1;
	}

	public boolean isPresent(By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

}
