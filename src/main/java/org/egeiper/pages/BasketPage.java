package org.egeiper.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class BasketPage extends BasePage {

    @FindBy(css = "[class*='product_name'] a")
    protected List<WebElement> productNames;

    @FindBy(css = "[class*='product_price_container'] div")
    protected List<WebElement> productPrices;

    @FindBy(css = "[class*='product_box']")
    protected WebElement productContainer;

    public BasketPage(final WebDriver driver) {
        super(driver);
    }

    public double getPriceOfProductOnBasket(final String productName) {
        waitUntil(ExpectedConditions.visibilityOf(productContainer));
        final int index = productNames.indexOf(findElementWithText(productNames, productName));
        return Double.parseDouble(productPrices.get(index).getText().replace(" TL", "")
                .replace(".", "").replace(",", "."));
    }
}
