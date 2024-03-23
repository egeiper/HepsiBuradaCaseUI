package org.egeiper.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;


public class DriverHelper {

    private final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private static final String GRID_URL = PropertyUtils.getProperty("test.properties", "gridURL");

    public WebDriver initializeDriver() throws MalformedURLException {
        final String serverProperty = System.getProperty("SERVER");
        if ("local".equals(serverProperty) || "actions".equals(serverProperty)|| serverProperty == null) {
            setDriver(new ChromeDriver(getChromeOptions()));
        } else {
            setDriver(createRemoteDriver());
        }
        //getDriver().manage().window().maximize();
        return getDriver();
    }

    private WebDriver createRemoteDriver() throws MalformedURLException {
        return new RemoteWebDriver(new URL(GRID_URL), getChromeOptions());
    }

    public ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        final String serverProperty = System.getProperty("SERVER");
        if ("actions".equals(serverProperty)) { // because hepsiburada doesnt allow headless mode it wont work for the case
            // but i'm putting it just for idea.
            options.addArguments("--headless");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--no-sandbox");
        }
        return options;
    }

    protected WebDriver getDriver() {
        return DRIVER.get();
    }

    protected void setDriver(final WebDriver driver) {
        DRIVER.set(driver);
    }
}
