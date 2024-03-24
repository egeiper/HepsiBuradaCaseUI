package org.egeiper.pages;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

import static org.testng.Assert.fail;

@Data
@Slf4j
public class BasePage {

    private static final int DEFAULT_DISPLAY_TIMEOUT = 5000;
    private static final int DEFAULT_TIMEOUT_DURATION = 20;
    private static final String ERROR_MESSAGE = "Wasn't able to wait";
    private static final String NO_SUCH_ELEMENT = "No such element";
    private static final String WEB_DRIVER_EXCEPTION = "Web driver exception";

    private WebDriver driver;
    private WebDriverWait wait;
    private final JavascriptExecutor jse;
    private Random random;

    public BasePage(final WebDriver driver) {
        setDriver(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_DISPLAY_TIMEOUT));
        jse = (JavascriptExecutor) getDriver();
        PageFactory.initElements(driver, this);
        random = new Random();
    }

    public void waitFor(final int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(ERROR_MESSAGE, e);
        }
    }

    public WebElement findElementWithText(final List<WebElement> elements, final String text) {
        try {
            for (final WebElement element : elements) {
                if (element.getText().equalsIgnoreCase(text)) {
                    return element;
                }
            }
        } catch (NoSuchElementException | WebDriverException e) {
            log.error(e.getClass().getCanonicalName().equals(NO_SUCH_ELEMENT)
                    ? NO_SUCH_ELEMENT : WEB_DRIVER_EXCEPTION);
        }
        return null;
    }

    public boolean isElementPresent(final WebElement element, final int duration) {
        try {
            waitUntil(ExpectedConditions.visibilityOf(element), duration);
            return true;
        } catch (NoSuchElementException | WebDriverException e) {
            return false;
        }
    }

    public void waitUntil(final ExpectedCondition<?> expectedCondition, final int duration) {
        getWait().withTimeout(Duration.ofSeconds(duration));
        waitUntil(expectedCondition);
        restoreTimeout();
    }

    private void restoreTimeout() {
        getWait().withTimeout(Duration.ofSeconds(DEFAULT_DISPLAY_TIMEOUT));
    }
    public void waitUntil(final ExpectedCondition<?> expectedCondition) {
        getWait().until(expectedCondition);
    }

    public void centerElement(final WebElement element) {
        final String scrollElementIntoMiddle = "var viewPortHeight = "
                + "Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

        jse.executeScript(scrollElementIntoMiddle, element);
    }

    public void waitAjaxRequestToBeFinished() {
        waitUntil((ExpectedCondition<Boolean>) driver -> jse.executeScript("return jQuery.active == 0").equals(true));
    }

    public WebElement findElementWithTextContains(final List<WebElement> elements, final String text) {
        if (elements != null) {
            for (final WebElement element : elements
            ) {
                if (element.getText().contains(text)) {
                    return element;
                }
            }
        }
        return null;
    }

    public void switchToLastOpenedWindow() {
        try {
            final String currentWindow = getDriver()
                    .getWindowHandle();
            final Set<String> windows = getDriver()
                    .getWindowHandles();
            if (windows.size() > 1) {
                for (final String window : windows) {
                    getDriver().switchTo().window(window);
                    if (!getDriver().getWindowHandle()
                            .equals(currentWindow)) {
                        getDriver().switchTo().window(window);
                    }
                }
            }
        } catch (Exception e) {
            log.error("There was an error while trying to switch to last opened window", e);
            fail("Problem switching windows " + e.getMessage());
        }
    }

    public void clickElement(final WebElement element) {
        centerElement(element);
        element.click();
    }
}
