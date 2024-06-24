package automation.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;

import automation.PageObjects.HomePage;
import automation.UserExceptions.BrokenUrlException;
import automation.UserExceptions.InvalidBrowserException;
import automation.UserExceptions.InvalidURLException;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public WebDriver driver;
	Properties prop;

	public boolean checkUrlIsBroken(String url) throws IOException, InvalidURLException {
		try {
			boolean isBroken = true;
			URL urlLink = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlLink.openConnection();
			conn.setConnectTimeout(2000);
			conn.connect();
			int responseCode = conn.getResponseCode();
			if (responseCode < 400)
				isBroken = false;
			return isBroken;
		} catch (UnknownHostException e) {
			throw new InvalidURLException("Given URL is Invalid!");
		}

	}

	public Properties getProperties() throws IOException {
		File file = new File(
				System.getProperty("user.dir") + "/src/main/java/automation/resources/GlobalData.properties");
		FileInputStream inputStream = new FileInputStream(file);
		prop = new Properties();
		prop.load(inputStream);

		return prop;
	}

	public WebDriver initializeDriver() throws IOException, InvalidBrowserException {
		String browser;
		String userInput;
		String propInput;
		try {
			userInput = System.getProperty("browser");
			browser = userInput.toLowerCase();
		} catch (NullPointerException e) {
			propInput = getProperties().getProperty("browser");
			browser = propInput.toLowerCase();
		}

		if (browser.contains("chrome")) {
			if (browser.contains("headless")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("headless");
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver(options);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				driver.manage().window().setSize(new Dimension(1440, 900));
			} else {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				driver.manage().window().maximize();
			}
		} else if (browser.contains("firefox")) {
			if (browser.contains("headless")) {
				FirefoxOptions options = new FirefoxOptions();
				options.addArguments("headless");
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver(options);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				driver.manage().window().setSize(new Dimension(1440, 900));
			} else {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				driver.manage().window().maximize();
			}
		} else if (browser.contains("edge")) {
			if (browser.contains("headless")) {
				EdgeOptions options = new EdgeOptions();
				options.addArguments("headless");
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver(options);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				driver.manage().window().setSize(new Dimension(1440, 900));
			} else {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				driver.manage().window().maximize();
			}
		} else {
			throw new InvalidBrowserException(browser.split(" ")[0].trim() + ": Invalid Browser");
		}

		return driver;

	}

	public HomePage openUrl() throws IOException, InvalidBrowserException, BrokenUrlException, InvalidURLException {
		String url = getProperties().getProperty("url");
		boolean isBroken = checkUrlIsBroken(url);
		if (isBroken != true) {
			driver = initializeDriver();
			driver.navigate().to(url);
			return new HomePage(driver);
		} else
			throw new BrokenUrlException("This URL is broken-> " + url);
	}

	public void closeCurrentBrowserWindow() {
		driver.close();
	}

	@AfterMethod
	public void terminateDriver() {
		driver.quit();
	}
}
