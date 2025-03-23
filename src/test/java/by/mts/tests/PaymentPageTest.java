package by.mts.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import by.mts.pages.Locators;
import by.mts.pages.PaymentPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.testng.Assert.*;

public class PaymentPageTest {
    private WebDriver driver;
    private PaymentPage paymentPage;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://mts.by");
        paymentPage = new PaymentPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Увеличен таймаут
        paymentPage.acceptCookies();
    }

    @Test
    public void testPayBlockCustomTitle() {
        paymentPage.checkPayBlock();
        paymentPage.checkPayBlockName("Онлайн пополнение\nбез комиссии");
    }

    @Test
    public void checkPaymentLogosTest() {
        paymentPage.checkLogoVisibility(Locators.VISA_LOGO, "Visa");
        paymentPage.checkLogoVisibility(Locators.MASTERCARD_LOGO, "MasterCard");
    }

    @Test
    public void testServiceTypeButton() {
        paymentPage.checkAndClickButton(Locators.SERVICE_TYPE_BUTTON);
    }

    @Test
    public void testFillPaymentForm() {
        paymentPage.checkPayBlock();
        paymentPage.selectService("Услуги связи");

        // Очистка и ввод данных
        paymentPage.clearFields(Locators.PHONE_NUMBER_FIELD_CONNECTION, Locators.AMOUNT_FIELD_CONNECTION, Locators.EMAIL_FIELD_CONNECTION);
        paymentPage.enterData(Locators.PHONE_NUMBER_FIELD_CONNECTION, Locators.AMOUNT_FIELD_CONNECTION, Locators.EMAIL_FIELD_CONNECTION, "297777777", "10", "test@example.com");
        paymentPage.checkAndClickButton(Locators.CONTINUE_BUTTON);
    }

    @Test
    public void testSelectedService() {
        paymentPage.selectService("Услуги связи");
        paymentPage.checkSelectedService("Услуги связи");
    }

    @Test
    public void testEmptyFieldPlaceholders() {
        paymentPage.verifyService("Услуги связи", "Номер телефона", "Сумма", "E-mail для отправки чека");
        paymentPage.verifyService("Домашний интернет", "Номер абонента", "Сумма", "E-mail для отправки чека");
        paymentPage.verifyService("Рассрочка", "Номер счета на 44", "Сумма", "E-mail для отправки чека");
        paymentPage.verifyService("Задолженность", "Номер счета на 2073", "Сумма", "E-mail для отправки чека");
    }

    @Test
    public void testConnectionServicePayment() {
        // Выбор услуги
        paymentPage.selectService("Услуги связи");

        // Ввод данных
        paymentPage.enterData("297777777", "10", "test@example.com");

        // Нажатие "Продолжить"
        paymentPage.click(Locators.CONTINUE_BUTTON);

        // Ожидание появления окна с полями ввода карты
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            // Используем найденный селектор для модального окна
            wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.PAYMENT_FRAME));

            // Переключаемся на фрейм
            WebElement paymentFrame = driver.findElement(Locators.PAYMENT_FRAME);
            driver.switchTo().frame(paymentFrame);

            // Ожидание появления полей ввода данных карты
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[autocomplete^='cc-']")));

            // Проверка суммы
            WebElement amountElement = driver.findElement(Locators.AMOUNT_TEXT);
            String amountText = amountElement.getText();
            assertNotNull(amountText, "Сумма для оплаты не отображается.");

            // Проверка номера телефона
            WebElement phoneNumberElement = driver.findElement(Locators.PHONE_NUMBER_TEXT);
            String phoneNumberText = phoneNumberElement.getText();
            assertEquals(phoneNumberText, "Оплата: Услуги связи Номер:375297777777", "Неверный номер телефона.");

            // Проверка надписей в незаполненных полях
            WebElement cardNumberLabel = driver.findElement(Locators.CARD_NUMBER_LABEL);
            WebElement expiryDateLabel = driver.findElement(Locators.EXPIRY_DATE_LABEL);
            WebElement cvvLabel = driver.findElement(Locators.CVV_LABEL);
            WebElement cardHolderLabel = driver.findElement(Locators.CARD_HOLDER_LABEL);

            // Проверяем, что все надписи присутствуют
            assertNotNull(cardNumberLabel, "Надпись для поля 'Номер карты' отсутствует.");
            assertNotNull(expiryDateLabel, "Надпись для поля 'Срок действия' отсутствует.");
            assertNotNull(cvvLabel, "Надпись для поля 'CVV' отсутствует.");
            assertNotNull(cardHolderLabel, "Надпись для поля 'Держатель карты' отсутствует.");

            // Проверка иконок платёжных систем
            assertFalse(driver.findElements(Locators.VISA_ICON).isEmpty(), "Иконка VISA не найдена.");
            assertFalse(driver.findElements(Locators.MASTERCARD_ICON).isEmpty(), "Иконка Mastercard не найдена.");
            assertFalse(driver.findElements(Locators.BELKART_ICON).isEmpty(), "Иконка Belkart не найдена.");
            assertFalse(driver.findElements(Locators.MAESTRO_ICON).isEmpty(), "Иконка Maestro не найдена.");

        } finally {
            // Вернуться на основной фрейм после работы с модальным окном
            driver.switchTo().defaultContent();
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
