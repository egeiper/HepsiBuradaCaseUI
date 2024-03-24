package org.egeiper.components;

import org.egeiper.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NavigationBar extends BasePage {

    @FindBy(css = "div[id*='SearchBox']")
    protected WebElement productSearchBarContainer;

    @FindBy(css = "div[id*='SearchBox'] input")
    protected WebElement productSearchBarInput;

    @FindBy(xpath = "//div[text()='ARA']")
    protected WebElement searchButton;

    @FindBy(css = "[data-test-id*='account']")
    protected WebElement accountStateButton;

    @FindBy(id = "shoppingCart")
    private WebElement shoppingCartButton;

    public NavigationBar(final WebDriver driver) {
        super(driver);
    }

    public void searchProduct(final String productName) {
        waitUntil(ExpectedConditions.visibilityOf(productSearchBarContainer));
        productSearchBarContainer.click();
        waitUntil(ExpectedConditions.elementToBeClickable(productSearchBarInput));
        productSearchBarInput.sendKeys(productName);
        searchButton.click();
        waitAjaxRequestToBeFinished();
    }

    public boolean isUserLoggedIn() {
        waitUntil(ExpectedConditions.visibilityOf(accountStateButton));
        return !accountStateButton.getText().equals("Giriş Yap");
    }

    public void goToShoppingCart() {
        waitUntil(ExpectedConditions.elementToBeClickable(shoppingCartButton));
        shoppingCartButton.click();
    }

}
