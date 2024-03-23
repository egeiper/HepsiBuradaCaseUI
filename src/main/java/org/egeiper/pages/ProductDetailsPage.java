package org.egeiper.pages;

import org.egeiper.pages.enums.Review;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProductDetailsPage extends BasePage {

    private static final String TAB_NAMES_LOCATOR = "table[class='product-links tabs-navigation new'] a[href]";

    @FindBy(xpath = "//div[text()='Varsayılan']")
    protected WebElement sortButton;

    @FindBy(css = "div[class*='hermes-Sort']")
    protected List<WebElement> sortOptions;
    /*
    Better locator but worse readability with xpath
    div[@class='arrowDownOrange']/ancestor::div[contains(@class, "hermes-Dropdown")]//div[contains(@class, "hermes-Sort")]/div
     */

    @FindBy(xpath = "//span[text()='Teşekkür Ederiz.']")
    protected List<WebElement> thanksMessages;

    @FindBy(css = "[class*='hermes-ReviewCard']")
    protected WebElement reviewCardContainer;

    @FindBy(css = "table[class='product-links tabs-navigation new']")
    protected WebElement productNavigationBar;

    @FindBy(xpath = "//div[contains(text(), 'Beğen')]")
    protected WebElement likeButton;

    @FindBy(id = "addToCart")
    protected WebElement addToCartButton;

    @FindBy(id = "product-name")
    protected WebElement productName;


    public ProductDetailsPage(final WebDriver driver) {
        super(driver);
    }

    public void switchToTab(final String tabName) {
        waitUntil(ExpectedConditions.visibilityOf(productNavigationBar));
        final List<WebElement> tabNames = getDriver().findElements(By.cssSelector(TAB_NAMES_LOCATOR));
        final WebElement tab = findElementWithTextContains(tabNames, tabName);
        clickElement(tab);
    }

    public void sortReviewsBy(final String sortOption) {
        waitUntil(ExpectedConditions.visibilityOf(sortButton));
        clickElement(sortButton);
        findElementWithText(sortOptions, sortOption).click();
        waitAjaxRequestToBeFinished();
    }

    public void isReviewNthHelpful(final int index, final Review review) {
        if (review.equals(Review.HELPFUL)) {
            getDriver().findElements(By.cssSelector("div[class*=thumbsUp]")).get(index).click();
        } else {
            getDriver().findElements(By.cssSelector("div[class*=thumbsDown]")).get(index).click();
        }
        waitUntil(ExpectedConditions.visibilityOf(thanksMessages.get(index)));
        centerElement(thanksMessages.get(index));
    }

    public boolean isThanksForReviewTextVisible(final int index) {
         return isElementPresent(thanksMessages.get(index), 1);
    }

    public boolean isThereAnyReview() {
        return isElementPresent(reviewCardContainer, 1);
    }

    public void clickLikeButton() {
        waitUntil(ExpectedConditions.visibilityOf(likeButton));
        clickElement(likeButton);
    }

    public boolean isProductLiked() {
        waitUntil(ExpectedConditions.visibilityOf(likeButton));
        return likeButton.getText().equals("Beğendin");
    }

    public void clickAddToCart() {
        waitUntil(ExpectedConditions.elementToBeClickable(addToCartButton));
        addToCartButton.click();
    }

    public double getPriceOfProduct() {
        List<WebElement> prices = getDriver().findElements(By.cssSelector("[id='offering-price'] span"));
        return Double.parseDouble(prices.get(0).getText().replace(".", "")
                + "." + prices.get(1).getText());
    }

    public String getProductName() {
        waitUntil(ExpectedConditions.visibilityOf(productName));
        return productName.getText();
    }
}
