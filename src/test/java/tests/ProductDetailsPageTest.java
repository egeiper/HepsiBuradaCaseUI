package tests;

import org.egeiper.components.CookieFooter;
import org.egeiper.components.FacebookLoginOverlay;
import org.egeiper.pages.LoginPage;
import org.egeiper.components.NavigationBar;
import org.egeiper.pages.ProductDetailsPage;
import org.egeiper.pages.SearchResultsPage;
import org.egeiper.pages.enums.Review;
import org.egeiper.util.DriverHelper;
import org.egeiper.util.S3Utils;
import org.egeiper.util.SiteNavigator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import software.amazon.awssdk.regions.Region;

import java.io.IOException;

import static org.testng.AssertJUnit.assertTrue;

public class ProductDetailsPageTest extends DriverHelper {

    private static WebDriver driver;
    private static NavigationBar navigationBar;
    private static ProductDetailsPage productDetailsPage;
    private static SearchResultsPage searchResultsPage;
    private static SiteNavigator siteNavigatorUtil;

    @BeforeClass
    public static void beforeClass() throws IOException {
        driver = new DriverHelper().initializeDriver();
        navigationBar = new NavigationBar(driver);
        productDetailsPage = new ProductDetailsPage(driver);
        searchResultsPage = new SearchResultsPage(driver);
        siteNavigatorUtil = new SiteNavigator(driver);
        CookieFooter cookieFooter = new CookieFooter(driver);
        LoginPage loginPage = new LoginPage(driver);
        FacebookLoginOverlay facebookLoginOverlay = new FacebookLoginOverlay(driver);
        siteNavigatorUtil.goToMainPage();
        if(cookieFooter.isCookieFooterVisible()) {
            cookieFooter.acceptCookies();
        }
        if(!navigationBar.isUserLoggedIn()) {
            siteNavigatorUtil.goToLoginPage();
            //loginPage.login("egeiper@hotmail.com", "###"); HepsiBurada doesn't allow to login with automated browsers.
            loginPage.clickLoginWithFacebookButton();
            // We will get password from S3 Bucket for security reasons.
            facebookLoginOverlay.loginWithFacebook("egeiper",
                    S3Utils.getObject("maven.egeiper", "FACEBOOK_PASSWORD", "egeiper", Region.EU_CENTRAL_1));
        }
    }

    @Test
    public void giveFeedbackOnReviewTest() {
        final int index = 0;
        navigationBar.searchProduct("iphone");
        searchResultsPage.clickOnRandomProduct();
        productDetailsPage.switchToTab("Değerlendirmeler");
        if(productDetailsPage.isThereAnyReview()) {
            productDetailsPage.sortReviewsBy("En yeni değerlendirme");
            productDetailsPage.isReviewNthHelpful(index, Review.HELPFUL);
            assertTrue(productDetailsPage.isThanksForReviewTextVisible(index));
        }
    }

    @Test
    public void giveFeedBackOnProductTest() {
        navigationBar.searchProduct("iphone");
        searchResultsPage.clickOnRandomProduct();
        if(!productDetailsPage.isProductLiked()) {
            productDetailsPage.clickLikeButton();
        }
        assertTrue(productDetailsPage.isProductLiked());
    }
    @AfterMethod
    public void afterTest() {
        siteNavigatorUtil.goToMainPage();
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
