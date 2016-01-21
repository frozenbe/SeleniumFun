import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GiphyOpener {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// Create chrome driver
		System.setProperty("webdriver.chrome.driver",
				"/home/feliks/Downloads/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.get("http://www.giphy.com/");
		Thread.sleep(1000);

		// Fill out the search parameters
		driver.findElement(By.id("search-box")).sendKeys(
				"happy birthday cartoon");
		// Go to the results page
		driver.findElement(By.id("search-button")).click();
		Thread.sleep(2000);

		By locator = By.cssSelector("a[href*='/gifs/']");
		clickByLocator(locator, driver);

	}

	// This is the official Selenium documention endorsed method of waiting for
	// elements.
	// This method is ineffective because it still suffers from
	// the stale element exception.
	public static void clickByLocator(By locator, WebDriver driver)
			throws InterruptedException {

		List<WebElement> giphys = (new WebDriverWait(driver, 100))
				.until(ExpectedConditions
						.presenceOfAllElementsLocatedBy(locator));

		List<String> giphysUrl = new ArrayList<String>();
		for (WebElement webElement : giphys) {
			System.out.println(webElement.getAttribute("href"));
			giphysUrl.add(webElement.getAttribute("href"));
		}

		for (String giphyUrl : giphysUrl) {
			driver.navigate().to(giphyUrl);
			Thread.sleep(2000);
		}

	}

}
