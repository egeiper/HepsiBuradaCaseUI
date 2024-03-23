package org.egeiper.pages;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
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
@SuppressWarnings("PMD.GodClass")
public class BasePage {

    private static final int DEFAULT_DISPLAY_TIMEOUT = 5000;
    private static final int DEFAULT_TIMEOUT_DURATION = 20;
    private static final String ERROR_MESSAGE = "Wasn't able to wait";
    private static final String NO_SUCH_ELEMENT = "No such element";
    private static final String WEB_DRIVER_EXCEPTION = "Web driver exception";
    private static final String STALE_ELEMENT_EXCEPTION = "Element is no longer present in page";
    private static final String JS_CLICK = "arguments[0].click();";
    private static final String CLASS = "class";
    private static final String DOCUMENT_READY_STATE_COMPLETE = "return document.readyState == 'complete' ";

    private WebDriver driver;
    private WebDriverWait wait;
    private final JavascriptExecutor jse;
    private final Actions actions;
    private Random random;

    public BasePage(final WebDriver driver) {
        setDriver(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_DISPLAY_TIMEOUT));
        jse = (JavascriptExecutor) getDriver();
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
        random = new Random();
    }


    public void navigateToURL(final String url) {
        getDriver().get(url);
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

    public boolean isElementPresent(final WebElement element) {
        try {
            waitUntil(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (NoSuchElementException | WebDriverException e) {
            return false;
        }
    }

    public boolean isElementPresent(final WebElement element, final int duration) {
        try {
            waitUntil(ExpectedConditions.visibilityOf(element), duration);
            return true;
        } catch (NoSuchElementException | WebDriverException e) {
            return false;
        }
    }

    public boolean isElementPresent(final By locator) {
        try {
            waitUntil(ExpectedConditions.visibilityOf(getDriver().findElement(locator)));
            return true;
        } catch (NoSuchElementException | WebDriverException e) {
            return false;
        }
    }

    public boolean isElementPresent(final By locator, final int duration) {
        try {
            waitUntil(ExpectedConditions.visibilityOf(getDriver().findElement(locator)), duration);
            return true;
        } catch (NoSuchElementException | WebDriverException e) {
            return false;
        }
    }

    public boolean areElementsPresent(final List<WebElement> elements) {
        try {
            waitUntil(ExpectedConditions.visibilityOfAllElements(elements));
            return true;
        } catch (WebDriverException | NoSuchElementException e) {
            return false;
        }
    }

    public boolean areElementsPresent(final By locator) {
        try {
            waitUntil(ExpectedConditions.visibilityOfAllElements(getDriver().findElements(locator)));
            return true;
        } catch (WebDriverException | NoSuchElementException e) {
            return false;
        }
    }

    public void waitUntil(final ExpectedCondition<?> expectedCondition, final int duration) {
        waitCondition(duration).until(expectedCondition);
    }

    public void waitUntil(final ExpectedCondition<?> expectedCondition) {
        waitCondition(DEFAULT_TIMEOUT_DURATION).until(expectedCondition);
    }

    public Wait<WebDriver> waitCondition(final int duration) {
        return new FluentWait<>(getDriver())
                .pollingEvery(Duration.ofMillis(250))
                .withTimeout(Duration.ofSeconds(duration))
                .ignoring(NoSuchElementException.class, WebDriverException.class);
    }

    public WebElement centerElement(final WebElement element) {
        final String scrollElementIntoMiddle = "var viewPortHeight = "
                + "Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

        jse.executeScript(scrollElementIntoMiddle, element);
        return element;
    }


    public void clickWithJS(final WebElement element) {
        jse.executeScript(JS_CLICK, element);
    }

    public void clickWithJS(final String cssSelector) {
        try {
            final WebElement element = getDriver().findElement(By.cssSelector(cssSelector));
            jse.executeScript(JS_CLICK, element);
        } catch (NoSuchElementException e) {
            log.error("No such element", e);
        }
    }

    public void waitAjaxRequestToBeFinished() {
        waitUntil((ExpectedCondition<Boolean>) driver -> jse.executeScript("return jQuery.active == 0").equals(true));
    }

    public void scrollPage(final Integer y) {
        final String script = String.format("windows.scrollBy(0,%s)", y);
        jse.executeScript(script);
    }

    public void scrollDownToPage() {
        jse.executeScript("windows.scrollBy(0,1000)");
    }

    public void acceptAlert() {
        final Alert alert = getDriver().switchTo().alert();
        alert.accept();
    }

    public void scrollToElement(final WebElement element) {
        jse.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void selectByValue(final WebElement element, final String text) {
        new Select(element).selectByValue(text);
    }

    public void switchToTheFrame(final String frameName) {
        waitUntil(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
    }

    public void scrollToTheTop() {
        jse.executeScript("window.scrollTo(0,0)");
    }

    public void scrollToTheBottom() {
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public String getAttribute(final WebElement element, final String attributeName) {
        final String attribute;
        try {
            attribute = element.getAttribute(attributeName);
            return attribute;

        } catch (WebDriverException e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }

    public boolean isAttributePresentForElement(final String attribute, final WebElement element) {
        try {
            return !element.getAttribute(attribute).isEmpty();
        } catch (WebDriverException e) {
            return false;
        }
    }

    public void clearField(final WebElement element) {
        element.sendKeys(Keys.CONTROL, "a");
        element.sendKeys(Keys.BACK_SPACE);
    }

    public void sendKeysByJS(final WebElement element, final String text) {
        executeJSOnWebElement("arguments[0].value='" + text + "';", element);
    }

    public void executeJSOnWebElement(final String jsScript, final WebElement we) {
        jse.executeScript(jsScript, we);
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


    public void openNewTabWithUrl(final String url) {
        jse.executeScript("window.open('" + url + "','_blank');");
    }

    public void switchToMainWindow() {
        try {
            final String currentWindow = getDriver()
                    .getWindowHandle();
            final Set<String> windows = getDriver()
                    .getWindowHandles();
            String mainWindow = null;
            final int oneOpenedWindow = 1;
            if (windows.size() > oneOpenedWindow) {
                for (final String window : windows) {
                    getDriver().switchTo().window(window);
                    if (getDriver().getWindowHandle().equals(currentWindow)) {
                        getDriver().close();
                    } else {
                        mainWindow = getDriver().getWindowHandle();
                    }
                    getDriver().switchTo().window(mainWindow);
                }
            }
        } catch (Exception e) {
            fail("Problem switching back to main window " + e.getMessage());
        }
    }

    public void mouseHover(final WebElement element) {
        actions.moveToElement(element).perform();
    }

    public void pressKey(final Keys key) {
        actions.sendKeys(key);
    }


    public void setValueAttributeWithJS(final WebElement element, final String value) {
        jse.executeScript("arguments[0].value=arguments[1].toString()", element, value);
    }

    public void switchToDefaultContent() {
        getDriver().switchTo().defaultContent();
    }


    public String createRandomString(final int length) {
        return RandomStringUtils.randomAlphanumeric(length);
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
