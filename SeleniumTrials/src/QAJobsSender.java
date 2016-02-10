import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class QAJobsSender {

	private static WebDriver driver;

	public static void main(String[] args) throws Exception {
		// Create chrome driver
		System.setProperty("webdriver.chrome.driver",
				"/home/feliks/Downloads/chromedriver");
		List<String> inputList = new ArrayList<String>();
		// Read user input
		inputList = readInput(inputList);

		driver = new ChromeDriver();
		driver.get("http://ca.indeed.com/");

		// Fill out search parameters and modify results parameters, by
		// increasing number of results per page and sort by date
		fillOutSearchParams(inputList);

		// Open postings pages for each posting
		List<WebElement> jobPostings = driver.findElements(By
				.xpath("//*[contains(@data-tn-element, 'jobTitle')]"));

		for (WebElement webElement : jobPostings) {
			webElement.click();

			String parentHandle = driver.getWindowHandle(); // get the current
															// window handle

			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle); // switch focus of
														// WebDriver to the next
														// found window handle
														// (that's your newly
														// opened window)
			}

			By locator = By.linkText("Apply Now");

			if (driver.findElements(locator).size() == 0) {
				System.out.println("Skip to next job posting");
				driver.close(); // close newly opened window when done with it
				driver.switchTo().window(parentHandle); // switch back to the
														// original window
				continue;
			}

			WebElement applyElement = (new WebDriverWait(driver, 100))
					.until(ExpectedConditions.presenceOfElementLocated(locator));

			safeJavaScriptClick(applyElement, driver, inputList);

			Thread.sleep(1500);

			driver.close(); // close newly opened window when done with it
			driver.switchTo().window(parentHandle); // switch back to the
													// original window

		}

		System.out.println("Completed page 1!");
		driver.close();

	}

	private static void fillOutSearchParams(List<String> inputList) {
		// Fill out the search parameters
		driver.findElement(By.id("what")).sendKeys(inputList.get(0));
		driver.findElement(By.id("where")).clear();
		driver.findElement(By.id("where")).sendKeys(inputList.get(1));
		// Go to the results page
		driver.findElement(By.id("fj")).click();
		// Make it possible to have 50 postings per page
		String urlExpandedResults = driver.getCurrentUrl() + "&limit=50";
		driver.navigate().to(urlExpandedResults);
		// Sort by date
		driver.findElement(By.linkText("date")).click();
	}

	/*
	 * Input by index: 0 search keywords 1 search location 2 applicant user name
	 * 3 applicant email 4 applicant phone number 5 path to resume file
	 */
	public static List<String> readInput(List<String> inputList) {
		// Read in the search related input
		Scanner stdin = new Scanner(System.in);

		System.out.println("Enter the search keywords: ");
		inputList.add(stdin.nextLine());
		System.out.println("Enter the search location: ");
		inputList.add(stdin.nextLine());
		System.out.println("Enter applicant name: ");
		inputList.add(stdin.nextLine());
		System.out.println("Enter applicant email: ");
		inputList.add(stdin.nextLine());
		System.out.println("Enter applicant phone num: ");
		inputList.add(stdin.nextLine());
		System.out.println("Enter path to applicant resume file: ");
		inputList.add(stdin.nextLine());

		stdin.close();

		return inputList;

	}

	public static void fillOutFormAndSubmit(WebDriver driver,
			List<String> inputList) throws InterruptedException {

		driver.switchTo().frame(0);
		driver.switchTo().frame(0);

		driver.findElement(By.id("applicant.name")).sendKeys(inputList.get(2));
		driver.findElement(By.id("applicant.email")).sendKeys(inputList.get(3));
		driver.findElement(By.id("applicant.phoneNumber")).sendKeys(
				inputList.get(4));

		driver.findElement(By.id("resume")).sendKeys(inputList.get(5));

		driver.findElement(By.id("apply")).click();

		Thread.sleep(3500);
	}

	public static void safeJavaScriptClick(WebElement element,
			WebDriver driver, List<String> inputList) throws Exception {
		try {
			if (element.isEnabled() && element.isDisplayed()) {
				System.out
						.println("Clicking on element with using java script click");

				((JavascriptExecutor) driver).executeScript(
						"arguments[0].click();", element);
				fillOutFormAndSubmit(driver, inputList);
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
