package org.egeiper.util;

import org.openqa.selenium.WebDriver;

public class SiteNavigator {

    private WebDriver driver;

    public SiteNavigator(final WebDriver driver) {
        this.driver = driver;
    }

    public void goToMainPage() {
        driver.navigate().to("https://hepsiburada.com");
    }

    public void goToLoginPage() {
        driver.navigate().to("https://giris.hepsiburada.com");
    }
}
