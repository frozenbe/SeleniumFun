import java.io.File;
import java.net.URL;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

public class PofMessageSender {

	public static void main(String[] args) throws Exception {
//		System.setProperty("webdriver.chrome.driver",
//				"/home/feliks/Downloads/chromedriver");
		
		URL url = PofMessageSender.class.getClassLoader().getResource("chromedriver");
		System.out.println("url: " + url);
		File file = new File(url.getFile()); // Strangely, URL.getFile does not return a File
		ChromeDriverService.Builder bldr = (new ChromeDriverService.Builder())
		                                   .usingDriverExecutable(file)
		                                   .usingAnyFreePort();
		
		//WebDriver driver = new ChromeDriver();
		
		Scanner stdin = new Scanner(System.in);
		System.out.println("username: ");
		String username = stdin.nextLine();
		System.out.println("password: ");
		String password = stdin.nextLine();
		stdin.close();

		WebDriver driver = new ChromeDriver(bldr.build());

		//WebDriver driver = new ChromeDriver();
		driver.get("http://www.pof.com/lastonlinemycity.aspx");
		Thread.sleep(2000);
		driver.findElement(By.name("username")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("login")).click();

		for (int i = 0; i < 55; i++) {
			By locator = By.cssSelector("img[src*='thumbnails']");
			
			while (driver.findElements(locator).size() == 0) {
				driver.navigate().refresh();
			}
			
			driver.findElement(locator).click();
			Thread.sleep(3000);

			String message = "May I perhaps interest you in my profile?"; 
			driver.findElement(By.name("message")).sendKeys(message);
			Thread.sleep(3000);
			driver.findElement(By.name("sendmessage")).submit();
			Thread.sleep(3000);
			driver.navigate().to("http://www.pof.com/lastonlinemycity.aspx");
		}
		driver.close();
	}
}
