package org.egeiper.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class DriverHelper {

    private ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private static final String GRID_URL = PropertyUtils.getProperty("test.properties", "gridURL");

    public WebDriver initializeDriver() throws MalformedURLException {
        final String serverProperty = System.getProperty("SERVER");
        if ("local".equals(serverProperty) || serverProperty == null) {
            setDriver(new ChromeDriver(getChromeOptions()));
        } else {
            setDriver(createRemoteDriver());
        }
        getDriver().manage().window().maximize();
        return getDriver();
    }

    private WebDriver createLocalDriver() {
        return new ChromeDriver(getChromeOptions());
    }

    private static ChromeOptions getChromeOptions() {
        final Map<String, Object> prefs = new HashMap<>();
        final ChromeOptions chromeOpts = new ChromeOptions();
        chromeOpts.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        chromeOpts.setExperimentalOption("prefs", prefs);
        if(System.getProperty("SERVER").equals("grid")) {
            chromeOpts.addArguments("--no-sandbox");
            chromeOpts.addArguments("--disable-dev-shm-usage");
            chromeOpts.addArguments("--headless");
        }
        return chromeOpts;
    }

    private WebDriver createRemoteDriver() throws MalformedURLException {
        return new RemoteWebDriver(new URL(GRID_URL), getChromeOptions());
    }

    protected WebDriver getDriver() {
        return DRIVER.get();
    }

    protected void setDriver(final WebDriver driver) {
        DRIVER.set(driver);
    }
}
