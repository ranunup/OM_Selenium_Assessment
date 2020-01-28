package test.scripts;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import data.engine.ConfigProperties;
import data.engine.SeleniumDriver;
import test.model.TestCaseResult;

public class PersonalLoanCalculator {
	SeleniumDriver selenium;
	private ConfigProperties conf;
	private ExtentReports extentReport;
	
	public PersonalLoanCalculator() {
		this.conf = new ConfigProperties();
	}
	
	@BeforeTest
	public void startDriver() {
		try {
			
			System.out.println("[INFO] Initializing Selenium WebDriver");
			
			// initialize the WebDriver
			this.selenium = new SeleniumDriver();
			
		} catch (Exception ex) {
			System.err.println("[Personal Loan Cal Error] Failed to start driver, error - " + ex.getMessage());
		}
	}
	
	public void executeTests() {
		
		try {
			this.processTestSteps();
			
		} catch(Exception ex) {
			System.err.println("[Personal Loan Cal Error] Failed to execute tests, error - " + ex.getMessage());
		}
	}
	
	@AfterTest
	public void quitDriver() {
		try {
			this.selenium.stopDriver();
		} catch (Exception ex) {
			System.err.println("[Personal Loan Cal Error] Failed to quit driver, error - " + ex.getMessage());
		}
	}
	
	@Test
	private void processTestSteps(){
		
		this.initExtentReports();
		
		try {
			
			System.out.println("[INFO] Processing Test Steps");
			
			int counter = 1;
			boolean continue_flag;
			String testCasePrefix = "TCID_";
			
			// navigate to home url
			String tcID = testCasePrefix + counter;
			continue_flag = this.navigateToUrl(tcID, this.conf.getOldMutualFinanceURL());
			
			
			// verify page location
			counter++;
			tcID = testCasePrefix + counter;
			if (continue_flag) {
				continue_flag = this.verifyPageURL(tcID, this.conf.getOldMutualFinanceURL());
			}
			
			// navigate to personal loans page
			counter++;
			tcID = testCasePrefix + counter;
			if (continue_flag) {
				continue_flag = this.clickByLinkText(tcID, this.conf.getPersonalLoansLinkText());
			}
			
			// verify page location
			counter++;
			tcID = testCasePrefix + counter;
			if (continue_flag) {
				continue_flag = this.verifyPageURL(tcID, this.conf.getPersonalLoansURL());
			}
			
			// click R50 000
			counter++;
			tcID = testCasePrefix + counter;
			if (continue_flag) {
				continue_flag = this.clickByOptionText(tcID, this.conf.getCalc50KIndicator());
			}
			
			// click 84 MONTHS
			counter++;
			tcID = testCasePrefix + counter;
			if (continue_flag) {
				continue_flag = this.clickByOptionText(tcID, this.conf.getCalce84MonthIndicator());
			}
			
			// verify minimum loan deduction amount
			counter++;
			tcID = testCasePrefix + counter;
			if (continue_flag) {
				continue_flag = this.verifyTextOnPage(tcID, this.conf.getMinLoanDeductionAmount());
			}
			
			// verify maximum loan deduction amount
			counter++;
			tcID = testCasePrefix + counter;
			if (continue_flag) {
				continue_flag = this.verifyTextOnPage(tcID, this.conf.getMaxLoanDeductionAmount());
			}
			
			// click view breakdown
			counter++;
			tcID = testCasePrefix + counter;
			if (continue_flag) {
				continue_flag = this.clickByLinkText(tcID, this.conf.getBreakDownText());
			}
			
			// verify loan capital
			counter++;
			tcID = testCasePrefix + counter;
			if (continue_flag) {
				continue_flag = this.verifyTextOnPage(tcID, this.conf.getLoanCapitalAmount());
			}
			
			// verify loan term
			counter++;
			tcID = testCasePrefix + counter;
			if (continue_flag) {
				this.verifyTextOnPage(tcID, this.conf.getLoanTermPeriod());
			}
			
		} catch(Exception ex) {
			System.err.println("[Personal Loan Calc Error] Failed to process test steps, error - " + ex.getMessage());
		}
		
		this.flushExtentReports();
	}
	
	// initialize extent reports
	private void initExtentReports() {
		
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Date now = new Date();
			
			String dateStr = dateFormat.format(now);
			
			String reportFileName = "PersonalLoanCalcReport_" + dateStr + ".html";
			
			String testReport = System.getProperty("user.dir") + this.conf.getTestReportDir() + reportFileName;
			
			this.extentReport = new ExtentReports(testReport, false);
			
		} catch (Exception ex) {
			System.err.println("[Personal Loan Error] Failed to initialize extent reports, error - " + ex.getMessage());
		}
	}
	
	// write extent reports
	private void flushExtentReports() {
		
		try {
			this.extentReport.flush();
			
		} catch (Exception ex) {
			System.err.println("[Personal Loan Error] Failed to flush extent reports, error - " + ex.getMessage());
		}
	}
	
	// navigate to url test
	private boolean navigateToUrl(String tcID, String url) {
		
		try {
			
			System.out.println("[INFO] Navigating to URL : " + url);
			
			String testCaseDescription = "Browse to " + url + " web site URL";
			
			TestCaseResult result = selenium.goToURL(url);
			
			ExtentTest extentTest = extentReport.startTest(tcID, testCaseDescription);
			
			if (result.getTestResult()) {
				extentTest.log(LogStatus.PASS, "Successfully navigated to URL : " + url);
			} else {
				extentTest.log(LogStatus.FAIL, "Failed to navigate to URL : " + url);
			}
			
			extentReport.endTest(extentTest);
			
			return true;
			
		} catch(Exception ex) {
			System.err.println("[Personal Loan Calc Error] Failed to navigate to URL, error - " + ex.getMessage());
		}
			
//		this.quitDriver();
		return false;
	}
	
	// validate that you're on the correct screen by checking the current driver URL against the expected URL
	private boolean verifyPageURL(String tcID, String url) {
		
		try {
			
			System.out.println("[INFO] Verifying page URL : " + url);
			
			TestCaseResult result = selenium.verifyVisitedScreen(url);

			String testCaseDescription = "Validate current driver URL against expected URL : " + url;
			
			ExtentTest extentTest = extentReport.startTest(tcID, testCaseDescription);
			
			extentTest.log(LogStatus.INFO, "Screenshot : " + extentTest.addScreenCapture(result.getScreenshotLocation()));
			
			if (result.getTestResult()) {
				extentTest.log(LogStatus.PASS, "Successfully validated current page by URL : " + url);
			} else {
				extentTest.log(LogStatus.FAIL, "Failed to navigate current page by URL : " + url);
			}
			
			extentReport.endTest(extentTest);
			
			return true;
			
		} catch(Exception ex) {
			System.err.println("[Personal Loan Calc Error] Failed to verify page URL, error - " + ex.getMessage());
		}
		
//		this.quitDriver();
		return false;
	}
	
	// navigate by link text
	private boolean clickByLinkText(String tcID, String linkText) {
		
		try {
			
			System.out.println("[INFO] Attempting to click by link text : " + linkText);
			
			TestCaseResult result = selenium.selectLinkByText(linkText);
			
			String testCaseDescription = "Select link by text " + linkText;
			
			ExtentTest extentTest = extentReport.startTest(tcID, testCaseDescription);
			
			if (result.getTestResult()) {
				extentTest.log(LogStatus.PASS, "Clicked link by text : " + linkText);
			} else {
				extentTest.log(LogStatus.FAIL, "Failed to click link by text : " + linkText);
			}
			
			
			extentReport.endTest(extentTest);
			
			return true;
			
		} catch(Exception ex) {
			System.err.println("[Personal Loan Calc Error] Failed to click by link text, error - " + ex.getMessage());
		}
		
//		this.quitDriver();
		return false;
	}
	
	// click by option text
	private boolean clickByOptionText(String tcID, String optionText) {
		
		try {
			
			System.out.println("[INFO] Attempting to click by option text : " + optionText);
			
			TestCaseResult result = selenium.clickByOptionText(optionText);
			
			String testCaseDescription = "Select option with text : " + optionText;
			
			ExtentTest extentTest = extentReport.startTest(tcID, testCaseDescription);
			
			if(result.getTestResult()) {
				extentTest.log(LogStatus.PASS, "Clicked option by text : " + optionText);
			} else {
				extentTest.log(LogStatus.FAIL, "Failed to click option by text : " + optionText);
			}
			
			extentReport.endTest(extentTest);
			
			return true;
			
		} catch(Exception ex) {
			System.err.println("[Personal Loan Calc Error] Failed to click by option text, error - " + ex.getMessage());
		}
		
//		this.quitDriver();
		return false;
	}
	
	// validate that given text is displayed on the page
	private boolean verifyTextOnPage(String tcID, String pageText) {
		
		try {
			
			System.out.println("[INFO] Verifying text : " + pageText + " on page");
			
			TestCaseResult result = selenium.verifyTextOnPage(pageText);
			String testCaseDescription = "Verify text : " + pageText + " on page.";
			
			ExtentTest extentTest = extentReport.startTest(tcID, testCaseDescription);
			
			extentTest.log(LogStatus.INFO, "Screenshot : " + extentTest.addScreenCapture(result.getScreenshotLocation()));
			
			if (result.getTestResult()) {
				extentTest.log(LogStatus.PASS, "Verified text : " + pageText + " on page");
			} else {
				extentTest.log(LogStatus.FAIL, "Failed to verify text : " + pageText + " on page");
			}
			
			extentReport.endTest(extentTest);
			
			return true;
			
		} catch (Exception ex) {
			System.err.println("[Personal Loan Cal Error] Failed to verify text : " + pageText + " on page, error - " + ex.getMessage());
		}
		
//		this.quitDriver();
		
		return false;
	}
}
