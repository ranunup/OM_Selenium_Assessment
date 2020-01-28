package test.model;

public class TestCaseResult {
	private boolean testResult;
	private String screenshotLocation;
	
	public TestCaseResult(boolean testResult, String screenshotLocation) {
		this.testResult = testResult;
		this.screenshotLocation = screenshotLocation;
	}
	
	public String getScreenshotLocation() {
		return this.screenshotLocation;
	}
	
	public boolean getTestResult () {
		return this.testResult;
	}
}
