import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class QAJobsSender {

	public static void main(String[] args) throws Exception {
		// Create chrome driver
		System.setProperty("webdriver.chrome.driver",
				"/home/feliks/Downloads/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.get("http://ca.indeed.com/");
		Thread.sleep(1000);

		// Fill out the search parameters
		driver.findElement(By.id("what")).sendKeys("qa engineer");
		driver.findElement(By.id("where")).clear();
		driver.findElement(By.id("where")).sendKeys("toronto");
		// Go to the results page
		driver.findElement(By.id("fj")).click();

		// Open postings pages for each posting
		List<WebElement> jobPostings = driver.findElements(By
				.xpath("//*[contains(@id, 'sja')]"));

		for (WebElement webElement : jobPostings) {
			webElement.click();

			WebElement applyElement = 
					driver.findElement(By.xpath("//*[@class='indeed-apply-button']"));
			

			boolean isPresent = driver.findElements(
					By.className("indeed-apply-button")).size() > 0;

			System.out.println(isPresent);
			Thread.sleep(3000);

			safeJavaScriptClick(applyElement, driver);

			Thread.sleep(6000);

		}

	}

	public static void safeJavaScriptClick(WebElement element, WebDriver driver)
			throws Exception {
		try {
			if (element.isEnabled() && element.isDisplayed()) {
				System.out
						.println("Clicking on element with using java script click");

				((JavascriptExecutor) driver).executeScript(
						"arguments[0].click();", element);
			} else {
				System.out.println("Unable to click on element");
			}
		} catch (StaleElementReferenceException e) {
			System.out.println("Element is not attached to the page document "
					+ e.getStackTrace());
		} catch (NoSuchElementException e) {
			System.out.println("Element was not found in DOM "
					+ e.getStackTrace());
		} catch (Exception e) {
			System.out.println("Unable to click on element "
					+ e.getStackTrace());
		}
	}
}
