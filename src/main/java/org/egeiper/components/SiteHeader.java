package org.egeiper.components;

import org.egeiper.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SiteHeader extends BasePage {

    @FindBy(id = "shoppingCart")
    private WebElement shoppingCartButton;

    public SiteHeader(final WebDriver driver) {
        super(driver);
    }

    public void goToShoppingCart() {
        waitUntil(ExpectedConditions.elementToBeClickable(shoppingCartButton));
        shoppingCartButton.click();
    }
}
