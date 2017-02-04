package test.java.org.apache.maven.maven;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Feliks Rozenberg 
 * A test class for testing the metrics editing
 *         component by Triton Wear
 *
 */
public class AppTest {

	private static WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		/* Before running anything, init the driver and log into the web app */
		driver = new ChromeDriver();
		driver.get("http://stage.app.tritonwear.com/#/app/users/149?workout_id=4305");
		Thread.sleep(3000);
		driver.findElement(By.name("email"))
				.sendKeys("<email>");
		driver.findElement(By.name("password")).sendKeys("<password>");
		driver.findElement(By.className("btn-lg")).click();
		Thread.sleep(3000);

	}

	@Before
	public void setUpBefore() throws Exception {
		/*
		 * Before running any tests in this suite, navigate to the sample
		 * workout page, and launch the metrics editing view
		 */

		driver.navigate()
				.to("http://stage.app.tritonwear.com/#/app/users/149?workout_id=4305");
		Thread.sleep(5000);

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,400)", "");

		By metricsEditBtnlocator = By.cssSelector("a[ng-click*='editWorkout']");

		WebElement metricsBtnElement = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions
						.elementToBeClickable(metricsEditBtnlocator));
		metricsBtnElement.click();
		Thread.sleep(2000);

		/*
		 * "Simulate" hovering over with the mouse to make the editing lap
		 * button visible
		 */
		WebElement editToggleButton = driver.findElement(By
				.cssSelector("img[ng-click*='toggleActiveMetric']"));

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", editToggleButton);
		Thread.sleep(3000);

	}

	@AfterClass
	public static void oneTimeTearDown() {
		System.out.println("Testing completed.");
		driver.close();
	}

	@Test
	public void testEditAndSaveSplitTime01() throws InterruptedException {
		/*
		 * Test changing the split time to the value 1:15:33 Expected: split
		 * time field value after saving equals 1:15:33
		 */
		List<String> splitTimeParams = new ArrayList<String>(Arrays.asList("1",
				"15", "33"));
		setSplitTime(splitTimeParams);

		// There needs to be better naming eventually, works now because split
		// time is first metric
		WebElement splitTime = driver.findElement(By
				.cssSelector("div[class*='metric-value']"));
		String actualResult = splitTime.getText().trim();
		System.out.println("splitTime.getText().trim(): " + actualResult);
		assertEquals("1:15.3", splitTime.getText().trim());
	}

	@Test
	public void testEditAndSaveSplitTime02() throws InterruptedException {
		/*
		 * Test changing the split time to the value 0:0:0 Negative testing:
		 * should not pass
		 */
		List<String> splitTimeParams = new ArrayList<String>(Arrays.asList("0",
				"0", "0"));
		setSplitTime(splitTimeParams);
		// There needs to be better naming eventually, works now because split
		// time is first metric found by locator
		WebElement splitTime = driver.findElement(By
				.cssSelector("div[class*='metric-value']"));
		String actualResult = splitTime.getText().trim();
		System.out.println("splitTime.getText().trim(): " + actualResult);
		assertNotEquals("-", splitTime.getText().trim());
	}

	private void setSplitTime(List<String> splitTimeParams)
			throws InterruptedException {
		/*
		 * A helper method to pass the split time values in the lap edit view
		 */
		By minutes = By.cssSelector("input[ng-model*='minutes']");
		driver.findElement(minutes).clear();
		driver.findElement(minutes).sendKeys(splitTimeParams.get(0));
		By seconds = By.cssSelector("input[ng-model*='seconds']");
		driver.findElement(seconds).clear();
		driver.findElement(seconds).sendKeys(splitTimeParams.get(1));
		By subseconds = By.cssSelector("input[ng-model*='subseconds']");
		driver.findElement(subseconds).clear();
		driver.findElement(subseconds).sendKeys(splitTimeParams.get(2));

		Thread.sleep(2000);
		driver.findElement(By.cssSelector("button[ng-click*='save']")).click();
		Thread.sleep(2000);
	}
}
