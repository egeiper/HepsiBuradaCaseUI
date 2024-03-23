package org.egeiper.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchResultsPage extends BasePage {

    @FindBy(css = "[data-test-id='product-card-name']")
    protected List<WebElement> productsInResults;

    public SearchResultsPage(final WebDriver driver) {
        super(driver);
    }

    public void clickOnRandomProduct() {
        final WebElement randomProduct = productsInResults.get(getRandom().nextInt(productsInResults.size()));
        clickElement(randomProduct);
        waitAjaxRequestToBeFinished();
        switchToLastOpenedWindow();
    }
}
