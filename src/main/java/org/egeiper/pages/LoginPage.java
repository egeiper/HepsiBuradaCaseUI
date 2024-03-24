package org.egeiper.pages;

import org.egeiper.pages.enums.LoginCredentials;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    @FindBy(id = "txtUserName")
    protected WebElement usernameInput;

    @FindBy(id = "btnLogin")
    protected WebElement emailLoginButton;

    @FindBy(id = "btnEmailSelect")
    protected WebElement passwordLoginButton;

    @FindBy(id = "txtPassword")
    protected WebElement passwordInput;

    @FindBy(id = "btnFacebook")
    protected WebElement loginWithFacebookButton;

    public LoginPage(final WebDriver driver) {
        super(driver);
    }

    private void clickLoginButton(final LoginCredentials credential) {
        if (credential.equals(LoginCredentials.USERNAME)) {
            waitUntil(ExpectedConditions.elementToBeClickable(emailLoginButton));
            emailLoginButton.click();
        }
        else {
            waitUntil(ExpectedConditions.elementToBeClickable(passwordLoginButton));
            passwordLoginButton.click();
        }
    }

    public void login(final String username, final String password) {
        fillUsernameInput(username);
        clickLoginButton(LoginCredentials.USERNAME);
        fillPasswordInput(password);
        clickLoginButton(LoginCredentials.PASSWORD);
    }


    private void fillUsernameInput(final String username) {
        waitUntil(ExpectedConditions.visibilityOf(usernameInput));
        usernameInput.sendKeys(username);
    }

    private void fillPasswordInput(final String password) {
        waitUntil(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.sendKeys(password);
    }
    public void clickLoginWithFacebookButton() {
        waitUntil(ExpectedConditions.elementToBeClickable(loginWithFacebookButton));
        loginWithFacebookButton.click();
    }
}
