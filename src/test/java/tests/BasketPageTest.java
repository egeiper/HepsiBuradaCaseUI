package tests;

import org.egeiper.components.CookieFooter;
import org.egeiper.components.SiteHeader;
import org.egeiper.pages.BasketPage;
import org.egeiper.pages.MainPage;
import org.egeiper.pages.ProductDetailsPage;
import org.egeiper.pages.SearchResultsPage;
import org.egeiper.util.DriverHelper;
import org.egeiper.util.SiteNavigator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

import static org.testng.AssertJUnit.assertEquals;

public class BasketPageTest {

    private static WebDriver driver;
    private static MainPage mainPage;
    private static ProductDetailsPage productDetailsPage;
    private static SearchResultsPage searchResultsPage;
    private static BasketPage basketPage;
    private static SiteHeader siteHeader;

    @BeforeClass
    public static void beforeClass() throws MalformedURLException {
        driver = new DriverHelper().initializeDriver();
        mainPage = new MainPage(driver);
        productDetailsPage = new ProductDetailsPage(driver);
        searchResultsPage = new SearchResultsPage(driver);
        siteHeader = new SiteHeader(driver);
        basketPage = new BasketPage(driver);
        CookieFooter cookieFooter = new CookieFooter(driver);
        new SiteNavigator(driver).goToMainPage();
        if(cookieFooter.isCookieFooterVisible()) {
            cookieFooter.acceptCookies();
        }
    }

    @Test
    public void comparePriceWithinProductPageAndBasket() {
        mainPage.searchProduct("iphone");
        searchResultsPage.clickOnRandomProduct();
        final String productName = productDetailsPage.getProductName();
        final double productDetailsPrice = productDetailsPage.getPriceOfProduct();
        productDetailsPage.clickAddToCart();
        siteHeader.goToShoppingCart();
        final double basketPrice = basketPage.getPriceOfProductOnBasket(productName);
        assertEquals(productDetailsPrice, basketPrice);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

