package data.engine;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import test.model.TestCaseResult;

public class SeleniumDriver {
	private WebDriver driver;
	private ConfigProperties conf;
	
	public SeleniumDriver() {
		conf = new ConfigProperties();
		this.startDrivers();
	}
	
	private void startDrivers() {
		try {
			
			switch (conf.getWebDriver()) {
				case "chrome":
					this.launchChrome();
					break;
				case "firefox":
					this.launchFirefox();
					break;
				default:
					throw new Exception("[Selenium Error] Failed to start web driver, unknown web driver  - " + conf.getWebDriver());
			}
		} catch (Exception ex) {
			System.err.println("[Selenium Error] Failed to start web driver, error - " + ex.getMessage());
		}
	}
	
	private void launchChrome() {
		
		try {
//			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/chromedriver");
			
//			ChromeDriverService service = new ChromeDriverService.Builder()
//	                .usingDriverExecutable(new File(System.getProperty("user.dir") + "/chromedriver"))
//	                .usingAnyFreePort()
//	                .build();
//			
//			service.start();
			
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless","--disable-gpu", "--no-sandbox");
			
			DesiredCapabilities dcap = DesiredCapabilities.chrome();
			dcap.setCapability(ChromeOptions.CAPABILITY, options);
			
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dcap);
			
			
		} catch (Exception ex) {
			System.err.println("[Selenium Error] Failed to launch chrome, error - " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	private void launchFirefox() {
		
		try {
			
			
//			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/geckodriver");
//			
//			FirefoxProfile profile = new FirefoxProfile();
//			profile.setPreference("network.proxy.type", 1);
//			profile.setPreference("network.proxy.http", "localhost");
//			profile.setPreference("network.proxy.http_port", 3128);
//			
//			FirefoxOptions options = new FirefoxOptions();
//			options.setProfile(profile);
			
	          DesiredCapabilities dcap = DesiredCapabilities.firefox();
	          
	          driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),dcap);
			
			
//			driver = new FirefoxDriver(options);
			
		} catch(Exception ex) {
			System.err.println("[Selenium Error] Failed to launch firefox, error - " + ex.getMessage());
		}
	}
	
	public void stopDriver() {
		try {
			if (driver!=null) {
				driver.quit();
			} else {
				System.err.println("[Selenium Warning] Could not stop web driver as it is not running.");
			}
		} catch (Exception ex) {
			System.err.println("[Selenium Error] Failed to stop web driver, eror - " + ex.getMessage());
		}
	}
	
	public TestCaseResult goToURL(String URL) {
		
		boolean testPassed = false;
		String screenshotLocation = null;
		try {
			
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			driver.manage().window().maximize();
			
			driver.get(URL);
			
			testPassed = true;
		} catch (Exception ex) {
			System.err.println("[Selenium Error] Failed to go to URL : " + URL + " - error - " + ex.getMessage());
		}
		
		return new TestCaseResult(testPassed, screenshotLocation);
	}
	
	public TestCaseResult verifyVisitedScreen(String URL) {
		boolean testPassed = false;
		String screenshotLocation = null;
		
		try {
			String driverURL = this.driver.getCurrentUrl();
			
			testPassed = driverURL.equals(URL);
			
			screenshotLocation = this.takeScreenshot(this.conf.getScreenshotDir());
			
			
		} catch(Exception ex) {
			System.err.println("[Selenium Error] Failed to verify visited screen, error - " + ex.getMessage());
		}
		
		return new TestCaseResult(testPassed, screenshotLocation);
	}
	
	public TestCaseResult selectLinkByText(String linkText) {
		boolean testPassed = false;
		String screenshotLocation = null;
		
		try {
			
			WebElement href = this.elemByLinkText(linkText);
			
			if (href!=null) {
				testPassed = clickElem(href);
			}
			
		} catch(Exception ex) {
			System.err.println("[Selenium Error] Failed to select link by text, error - " + ex.getMessage());
		}
		
		return new TestCaseResult(testPassed, screenshotLocation);
	}
	
	public TestCaseResult clickByOptionText(String optionText) {
		boolean testPassed = false;
		String screenshotLocation = null;
		
		try {
			
			WebElement elem = this.elemByClickableOptionText(optionText);
			
			if (elem!=null) {
				testPassed = clickElem(elem);
			}
			
		} catch (Exception ex) {
			System.err.println("[Selenium Error] Failed to click by option text : " + optionText + ", error - " + ex.getMessage() );
		}
		
		return new TestCaseResult(testPassed, screenshotLocation);
	}
	
	public TestCaseResult verifyTextOnPage(String pageText) {
		boolean testPassed = false;
		String screenshotLocation = null;
		
		try {
			WebElement elem = this.elemByPageText(pageText);
			
			this.scrollElementToView(elem);
			
			testPassed = elem!=null;
			screenshotLocation = this.takeScreenshot(this.conf.getScreenshotDir());
			
		} catch(Exception ex) {
			System.err.println("[Selenium Error] Failed to verify text : " + pageText  + " on page, error - " + ex.getMessage());
		}
		
		return new TestCaseResult(testPassed, screenshotLocation);
	}
	
	private void scrollElementToView(WebElement elem) {
		
		try {
			
			JavascriptExecutor jExec = (JavascriptExecutor)driver;
			
			jExec.executeScript("arguments[0].scrollIntoView(true); window.scrollBy(0, -window.innerHeight / 4);",elem);
			
		} catch (Exception ex) {
			System.err.println("[Selenium Error] Failed to scroll into view, error - " + ex.getMessage());
		}
	}
	
	private String takeScreenshot(String filePath) {
		try {
			TakesScreenshot screenShot = ((TakesScreenshot)driver);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Date now = new Date();
			
			String dateStr = dateFormat.format(now);
			
			File srcFile = screenShot.getScreenshotAs(OutputType.FILE);
			
			String fileName = dateStr + ".png";
			
			filePath = filePath + fileName;
			
			File destinationFile = new File(filePath);
			
			FileUtils.copyFile(srcFile, destinationFile);
			
			return fileName;
			
		} catch(Exception ex) {
			System.err.println("[Selenium Error] Failed to take screenshot, error - " + ex.getMessage());
		}
		
		return null;
	}
	
	private WebElement elemByXpath(String xPathSelector) {
		try {
			By xpath = By.xpath(xPathSelector);
			return driver.findElement(xpath);
		} catch (Exception ex) {
			System.err.println("[Selenium Error] Failed to get element by xpath : " + xPathSelector + " , error - " + ex.getMessage());
		}
		return null;
	}
	
	private WebElement elemByClickableOptionText(String optionText) {
		
		try {
			
			String elemLocator = "//*[text()='" + optionText + "']/..";
			
			return this.elemByXpath(elemLocator);
			
		} catch(Exception ex) {
			System.err.println("[Selenium Error] Failed to get element by option text, error - " + ex.getMessage());
		}
		
		return null;
	}
	
	private WebElement elemByPageText(String pageText) {
		
		try {
			String elemLocator = "//*[text()='" + pageText + "']";
			
			return this.elemByXpath(elemLocator);
			
		} catch (Exception ex) {
			System.err.println("[Selemiun Error] Failed to get element by page text, error - " + ex.getMessage());
		}
		
		return null;
	}
	
	private WebElement elemByLinkText(String linkText) {
		try {
			By link = By.linkText(linkText);
			return driver.findElement(link);
		} catch (Exception ex) {
			System.err.println("[Selenium Error] Failed to get element by link text, error - " + ex.getMessage());
		}
		return null;
	}
	
	private boolean clickElem(WebElement element) {
		try {
			
			this.scrollElementToView(element);
			
			element.click();
			return true;
		} catch (Exception ex) {
			System.err.println("[Selenium Error] Failed to click elem : " + element + ", error - " + ex.getMessage());
		}
		
		return false;
	}
}
