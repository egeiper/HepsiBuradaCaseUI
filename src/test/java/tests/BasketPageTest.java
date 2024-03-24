package tests;

import org.egeiper.components.CookieFooter;
import org.egeiper.pages.BasketPage;
import org.egeiper.components.NavigationBar;
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
    private static NavigationBar navigationBar;
    private static ProductDetailsPage productDetailsPage;
    private static SearchResultsPage searchResultsPage;
    private static BasketPage basketPage;

    @BeforeClass
    public static void beforeClass() throws MalformedURLException {
        driver = new DriverHelper().initializeDriver();
        navigationBar = new NavigationBar(driver);
        productDetailsPage = new ProductDetailsPage(driver);
        searchResultsPage = new SearchResultsPage(driver);
        basketPage = new BasketPage(driver);
        CookieFooter cookieFooter = new CookieFooter(driver);
        new SiteNavigator(driver).goToMainPage();
        if(cookieFooter.isCookieFooterVisible()) {
            cookieFooter.acceptCookies();
        }
    }

    @Test
    public void comparePriceWithinProductPageAndBasket() {
        navigationBar.searchProduct("iphone");
        searchResultsPage.clickOnRandomProduct();
        final String productName = productDetailsPage.getProductName();
        final double productDetailsPrice = productDetailsPage.getPriceOfProduct();
        productDetailsPage.clickAddToCart();
        productDetailsPage.closeProductAddedOverlay();
        navigationBar.goToShoppingCart();
        final double basketPrice = basketPage.getPriceOfProductOnBasket(productName);
        assertEquals(productDetailsPrice, basketPrice);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

