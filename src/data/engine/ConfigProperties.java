/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.engine;

import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author phumlani
 */
public class ConfigProperties {
    
    private final String propertiesFile = "resources/config.properties";
    private Properties properties;
    
    public ConfigProperties() {
        loadProperties();
    }
    
    public String getWebDriver() {
        try {
            
            return properties.getProperty("webDriver");
            
        } catch(Exception ex) {
            System.err.println("[Conf Properties Error] Failed to get web driver, error - " + ex.getMessage());
        }
        return null;
    }
    
    public String getOldMutualFinanceURL() {
    	try {
    		
    		return properties.getProperty("omFinanceURL");
    		
    	} catch (Exception ex) {
    		System.err.println("[Conf Properties Error] Failed to get Old Mutual Finance URL, error - " + ex.getMessage());
    	}
    	
    	return null;
    }
    
    
    public String getPersonalLoansURL() {
    	try {
    		
    		return properties.getProperty("personalLoansURL");
    		
    	} catch (Exception ex) {
    		System.err.println("[Conf Properties Error] Failed to get personal loans URL, error - " + ex.getMessage());
    	}
    	
    	return null;
    }
    
    public String getPersonalLoansLinkText() {
    	try {
    		
    		return properties.getProperty("personalLoansLinkText");
    		
    	} catch (Exception ex) {
    		System.err.println("[Conf Properties Error] Failed to get personal loans link text, error - " + ex.getMessage());
    	}
    	
    	return null;
    }
    
    public String getCalculatorLinkText() {
    	try {
    		
    		return properties.getProperty("calculatorLinkText");
    		
    	} catch (Exception ex) {
    		System.err.println("[Conf Properties Error] Failed to get calculator link text, error - " + ex.getMessage());
    	}
    	
    	return null;
    }
    
    public String getBreakDownText() {
    	try {
    		
    		return properties.getProperty("breakDownText");
    		
    	} catch (Exception ex) {
    		System.err.println("[Conf Properties Error] Failed to get break down text, error - " + ex.getMessage());
    	}
    	
    	return null;
    }
    
    public String getCalc50KIndicator() {
    	try {
    		
    		return properties.getProperty("cacl50KIndicator");
    				
    	} catch(Exception ex) {
    		System.err.println("[Conf Properties Error] Failed to get calc 50k indicator, error - " + ex.getMessage());
    	}
    	
    	return null;
    }
    
    public String getCalce84MonthIndicator() {
    	try {
    		
    		return properties.getProperty("calc84MonthIndicator");
    				
    	} catch(Exception ex) {
    		System.err.println("[Conf Properties Error] Failed to get calc 84 month indicator, error - " + ex.getMessage());
    	}
    	
    	return null;
    }
    
    public String getMinLoanDeductionAmount() {
    	try {
    		
    		return properties.getProperty("minLoanDeductionAmount");
    		
    	} catch (Exception ex) {
    		System.err.println("[Conf Properties Error] Failed to get minimum loan deduction amount, error - " + ex.getMessage());
    	}
    	
    	return null;
    }
    
    public String getMaxLoanDeductionAmount() {
    	try {
    		
    		return properties.getProperty("maxLoanDeductionAmount");
    		
    	} catch (Exception ex) {
    		System.err.println("[Conf Properties Error] Failed to get maximum loan deduction amount, error - " + ex.getMessage());
    	}
    	
    	return null;
    }
    
    public String getLoanCapitalAmount() {
    	try {
    		
    		return properties.getProperty("loanCapitalAmount");
    		
    	} catch (Exception ex) {
    		System.err.println("[Conf Properties Error] Failed to get loan capital amount, error - " + ex.getMessage());
    	}
    	
    	return null;
    }
    
    public String getLoanTermPeriod() {
    	try {
    		
    		return properties.getProperty("loanTermPeriod");
    		
    	} catch (Exception ex) {
    		System.err.println("[Conf Properties Error] Failed to get loan term period, error - " + ex.getMessage());
    	}
    	
    	return null;
    }
    
    public String getScreenshotDir() {
    	try {
    		
    		return properties.getProperty("screenshotDir");
    		
    	} catch (Exception ex) {
    		System.err.println("[Conf Properties Error] Failed to get screenshot directory, error - " + ex.getMessage());
    	}
    	
    	return null;
    }
    
    public String getTestReportDir() {
    	try {
    		
    		return properties.getProperty("testReportDir");
    		
    	} catch (Exception ex) {
    		System.err.println("[Conf Properties Error] Failed to get test report directory, error - " + ex.getMessage());
    	}
    	
    	return null;
    }
    
    public String getChromeDriverLocation() {
        try {
            
            return properties.getProperty("chromeDriverLoc");
            
        } catch(Exception ex) {
            System.err.println("[Conf Properties Error] Failed to get chrome driver location, error - " + ex.getMessage());
        }
    	return null;
    }
    
    public String getTestFileLocation() {
        try {
            
            return properties.getProperty("testFileDir");
            
        } catch(Exception ex) {
            System.err.println("[Conf Properties Error] Failed to get test file location, error - " + ex.getMessage());
        }
    	return null;
    }
    
    public InputStream getInStream () {
        return getClass().getClassLoader().getResourceAsStream(propertiesFile);
    }
    
    private void loadProperties() {
        try {
            InputStream inStream = getInStream();
            if(inStream!=null) {
                properties = new Properties();
                properties.load(inStream);
            }
        } catch (Exception ex) {
            System.err.println("[Conf Properties Error] Failed to load properties, error - " + ex.getMessage());
        }
    }
}
