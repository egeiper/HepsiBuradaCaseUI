package org.egeiper.components;

import org.egeiper.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CookieFooter extends BasePage {

    @FindBy(id = "onetrust-accept-btn-handler")
    protected WebElement acceptCookiesButton;

    public CookieFooter(final WebDriver driver) {
        super(driver);
    }

    public boolean isCookieFooterVisible() {
        return isElementPresent(acceptCookiesButton, 1);
    }

    public void acceptCookies() {
        waitUntil(ExpectedConditions.visibilityOf(acceptCookiesButton));
        acceptCookiesButton.click();
        waitUntil(ExpectedConditions.invisibilityOf(acceptCookiesButton));
    }
}
