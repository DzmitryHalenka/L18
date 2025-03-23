package by.mts.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {
    protected static WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        BasePage.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    // Метод для принятия cookies, если они присутствуют
    public void acceptCookiesIfPresent(By cookieBlock, By acceptCookiesBtn) {
        try {
            // Ожидаем появления блока с cookies
            WebElement cookieBlockElement = wait.until(ExpectedConditions.visibilityOfElementLocated(cookieBlock));

            // Ожидаем, что кнопка "Принять cookies" будет кликабельной
            WebElement acceptCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesBtn));

            // Нажимаем кнопку "Принять cookies"
            acceptCookiesButton.click();

            // Ожидаем, что блок с cookies исчезнет
            wait.until(ExpectedConditions.invisibilityOf(cookieBlockElement));

            System.out.println("Cookies успешно приняты.");

        } catch (TimeoutException e) {
            System.out.println("Баннер с cookies не найден, пропускаем...");
        }
    }

    // Общий метод для поиска элемента
    protected WebElement find(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Метод для ввода текста в поле
    protected void setText(By locator, String text) {
        WebElement element = find(locator);
        element.clear();
        element.sendKeys(text);
    }

    // Метод для клика по элементу
    public void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    // Проверка видимости элемента
    public boolean isElementVisible(By locator) {
        try {
            WebElement element = find(locator);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
