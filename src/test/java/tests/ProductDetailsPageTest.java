package tests;

import org.egeiper.components.CookieFooter;
import org.egeiper.pages.MainPage;
import org.egeiper.pages.ProductDetailsPage;
import org.egeiper.pages.SearchResultsPage;
import org.egeiper.pages.enums.Review;
import org.egeiper.util.DriverHelper;
import org.egeiper.util.SiteNavigator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

import static org.testng.AssertJUnit.assertEquals;

public class ProductDetailsPageTest extends DriverHelper {

    private static WebDriver driver;
    private static MainPage mainPage;
    private static ProductDetailsPage productDetailsPage;
    private static SearchResultsPage searchResultsPage;
    private static SiteNavigator siteNavigatorUtil;

    @BeforeClass
    public static void beforeClass() throws MalformedURLException {
        driver = new DriverHelper().initializeDriver();
        mainPage = new MainPage(driver);
        productDetailsPage = new ProductDetailsPage(driver);
        searchResultsPage = new SearchResultsPage(driver);
        siteNavigatorUtil = new SiteNavigator(driver);
        siteNavigatorUtil.goToMainPage();
        new CookieFooter(driver).acceptCookies();
        /*
        if(!mainPage.isUserLoggedIn()) {
            SiteNavigatorUtil.goToLoginPage();
            loginPage.clickLoginWithFacebookButton();
            facebookLoginOverlay.loginWithFacebook("egeiper", "231296.Ege");
        }

         */
    }

    @Test
    public void giveFeedbackOnReviewTest() {
        mainPage.searchProduct("iphone");
        searchResultsPage.clickOnRandomProduct();
        productDetailsPage.switchToTab("Değerlendirmeler");
        if(productDetailsPage.isThereAnyReview()) {
            productDetailsPage.sortReviewsBy("En yeni değerlendirme");
            productDetailsPage.isReviewNthHelpful(0, Review.HELPFUL);
            assertEquals("Teşekkür Ederiz.", productDetailsPage.getTextOfReviewNth(0),
                    "Review text is not as expected");
        }
    }
/*
    @Test
    public void giveFeedBackOnProductTest() {
        mainPage.searchProduct("iphone");
        searchResultsPage.clickOnRandomProduct();
        if(!productDetailsPage.isProductLiked()) {
            productDetailsPage.clickLikeButton();
        }
        assertTrue(productDetailsPage.isProductLiked());
    }


 */
    @AfterMethod
    public void afterTest() {
        siteNavigatorUtil.goToMainPage();
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
