package org.egeiper.components;

import org.egeiper.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FacebookLoginOverlay extends BasePage {

    @FindBy(id = "email")
    protected WebElement emailInput;

    @FindBy(id = "pass")
    protected WebElement passwordInput;

    @FindBy(id = "loginbutton")
    protected WebElement loginButton;

    public FacebookLoginOverlay(final WebDriver driver) {
        super(driver);
    }

    private void fillMailInput(final String email) {
        waitUntil(ExpectedConditions.visibilityOf(emailInput));
        emailInput.sendKeys(email);
    }

    private void fillPasswordInput(final String password) {
        waitUntil(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.sendKeys(password);
    }

    private void clickLoginButton() {
        waitUntil(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

    public void loginWithFacebook(final String email, final String password) {
        fillMailInput(email);
        fillPasswordInput(password);
        waitFor(1000);
        clickLoginButton();
    }
}
