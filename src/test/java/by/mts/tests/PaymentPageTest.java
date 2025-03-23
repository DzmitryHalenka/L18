package by.mts.tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.*;
import by.mts.pages.Locators;
import by.mts.pages.PaymentPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AllureJunit5.class)
@Feature("Платежи")
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
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        paymentPage.acceptCookies();
    }

    // Метод для ожидания видимости элемента
    private WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Test
    @Step("Проверка блока оплаты с кастомным заголовком")
    public void testPayBlockCustomTitle() {
        paymentPage.checkPayBlock();
        paymentPage.checkPayBlockName("Онлайн пополнение\nбез комиссии");
    }

    @Test
    @Step("Проверка логотипов платежных систем")
    public void checkPaymentLogosTest() {
        paymentPage.checkLogoVisibility(Locators.VISA_LOGO, "Visa");
        paymentPage.checkLogoVisibility(Locators.MASTERCARD_LOGO, "MasterCard");
    }

    @Test
    @Step("Проверка кнопки типа сервиса")
    public void testServiceTypeButton() {
        paymentPage.checkAndClickButton(Locators.SERVICE_TYPE_BUTTON);
    }

    @Test
    @Step("Заполнение формы оплаты")
    public void testFillPaymentForm() {
        paymentPage.checkPayBlock();
        paymentPage.selectService("Услуги связи");

        paymentPage.clearFields(Locators.PHONE_NUMBER_FIELD_CONNECTION, Locators.AMOUNT_FIELD_CONNECTION, Locators.EMAIL_FIELD_CONNECTION);
        paymentPage.enterData(Locators.PHONE_NUMBER_FIELD_CONNECTION, Locators.AMOUNT_FIELD_CONNECTION, Locators.EMAIL_FIELD_CONNECTION, "297777777", "10", "test@example.com");
        paymentPage.checkAndClickButton(Locators.CONTINUE_BUTTON);
    }

    @Test
    @Step("Проверка выбранной услуги")
    public void testSelectedService() {
        paymentPage.selectService("Услуги связи");
        paymentPage.checkSelectedService("Услуги связи");
    }

    @Test
    @Step("Проверка placeholder для пустых полей")
    public void testEmptyFieldPlaceholders() {
        paymentPage.verifyService("Услуги связи", "Номер телефона", "Сумма", "E-mail для отправки чека");
        paymentPage.verifyService("Домашний интернет", "Номер абонента", "Сумма", "E-mail для отправки чека");
        paymentPage.verifyService("Рассрочка", "Номер счета на 44", "Сумма", "E-mail для отправки чека");
        paymentPage.verifyService("Задолженность", "Номер счета на 2073", "Сумма", "E-mail для отправки чека");
    }

    @Test
    @Step("Проверка оплаты через сервис 'Услуги связи'")
    public void testConnectionServicePayment() {
        paymentPage.selectService("Услуги связи");

        paymentPage.enterData("297777777", "10", "test@example.com");

        paymentPage.click(Locators.CONTINUE_BUTTON);

        // Ждем видимость платежной формы
        WebElement paymentFrame = waitForVisibility(Locators.PAYMENT_FRAME);
        driver.switchTo().frame(paymentFrame);

        // Проверяем поля и значения
        WebElement amountElement = waitForVisibility(Locators.AMOUNT_TEXT);
        assertNotNull(amountElement.getText(), "Сумма для оплаты не отображается.");

        WebElement phoneNumberElement = waitForVisibility(Locators.PHONE_NUMBER_TEXT);
        assertEquals("Оплата: Услуги связи Номер:375297777777", phoneNumberElement.getText(), "Неверный номер телефона.");

        // Проверка надписей для полей
        assertNotNull(waitForVisibility(Locators.CARD_NUMBER_LABEL), "Надпись для поля 'Номер карты' отсутствует.");
        assertNotNull(waitForVisibility(Locators.EXPIRY_DATE_LABEL), "Надпись для поля 'Срок действия' отсутствует.");
        assertNotNull(waitForVisibility(Locators.CVV_LABEL), "Надпись для поля 'CVV' отсутствует.");
        assertNotNull(waitForVisibility(Locators.CARD_HOLDER_LABEL), "Надпись для поля 'Держатель карты' отсутствует.");

        // Проверка видимости иконок платежных систем
        assertFalse(driver.findElements(Locators.VISA_ICON).isEmpty(), "Иконка VISA не найдена.");
        assertFalse(driver.findElements(Locators.MASTERCARD_ICON).isEmpty(), "Иконка Mastercard не найдена.");
        assertFalse(driver.findElements(Locators.BELKART_ICON).isEmpty(), "Иконка Belkart не найдена.");
        assertFalse(driver.findElements(Locators.MAESTRO_ICON).isEmpty(), "Иконка Maestro не найдена.");

        driver.switchTo().defaultContent();  // Вернуться в основной контекст
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
