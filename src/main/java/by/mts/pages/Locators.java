package by.mts.pages;

import org.openqa.selenium.By;


public class Locators {

    // Cookie Block
    public static final By COOKIE_BLOCK = By.xpath("/html/body/div[6]/main/div/div[2]");
    public static final By ACCEPT_COOKIES_BTN = By.cssSelector(".btn.btn_black.cookie__ok");

    // Pay Block
    public static final By PAY_BLOCK_NAME = By.xpath("/html/body/div[6]/main/div/div[4]/div[1]/div/div/div[2]/section/div/h2");

    // Services
    public static final By SERVICES_BUTTON = By.cssSelector("button.select__header");
    public static final By SELECTED_SERVICE_TEXT = By.cssSelector("span.select__now"); // Локатор для отображаемой выбранной услуги
    public static final By SERVICE_TYPE_BUTTON = By.linkText("Подробнее о сервисе");

    // Service-specific Fields (Phone, Amount, Email)
    // Услуги связи
    public static final By PHONE_NUMBER_FIELD_CONNECTION = By.id("connection-phone");
    public static final By AMOUNT_FIELD_CONNECTION = By.id("connection-sum");
    public static final By EMAIL_FIELD_CONNECTION = By.id("connection-email");

    // Домашний интернет
    public static final By PHONE_NUMBER_FIELD_INTERNET = By.id("internet-phone");
    public static final By AMOUNT_FIELD_INTERNET = By.id("internet-sum");
    public static final By EMAIL_FIELD_INTERNET = By.id("internet-email");

    // Рассрочка
    public static final By PHONE_NUMBER_FIELD_INSTALMENT = By.id("score-instalment");
    public static final By AMOUNT_FIELD_INSTALMENT = By.id("instalment-sum");
    public static final By EMAIL_FIELD_INSTALMENT = By.id("instalment-email");

    // Задолженность
    public static final By PHONE_NUMBER_FIELD_ARREARS = By.id("score-arrears");
    public static final By AMOUNT_FIELD_ARREARS = By.id("arrears-sum");
    public static final By EMAIL_FIELD_ARREARS = By.id("arrears-email");

    // Кнопка Продолжить
    public static final By CONTINUE_BUTTON = By.cssSelector("#pay-connection button");

    // Payment Logos
    public static final By VISA_LOGO = By.cssSelector("img[alt='Visa']");
    public static final By MASTERCARD_LOGO = By.cssSelector("img[alt='MasterCard']");

    // Иконки платежных систем
    public static final By VISA_ICON = By.cssSelector("img[src*='visa-system.svg']");
    public static final By MASTERCARD_ICON = By.cssSelector("img[src*='mastercard-system.svg']");
    public static final By BELKART_ICON = By.cssSelector("img[src*='belkart-system.svg']");
    public static final By MAESTRO_ICON = By.cssSelector("img[src*='maestro-system.svg']");


    // Локаторы для проверки полей фрэйма
    public static final By CARD_NUMBER_LABEL = By.xpath("/html/body/app-root/div/div/div/app-payment-container/section/div/app-card-page/div/div[1]/app-card-input/form/div[1]/div[1]/app-input/div/div/div[1]/label");
    public static final By EXPIRY_DATE_LABEL = By.xpath("/html/body/app-root/div/div/div/app-payment-container/section/div/app-card-page/div/div[1]/app-card-input/form/div[1]/div[2]/div[1]/app-input/div/div/div[1]/label");
    public static final By CVV_LABEL = By.xpath("/html/body/app-root/div/div/div/app-payment-container/section/div/app-card-page/div/div[1]/app-card-input/form/div[1]/div[2]/div[3]/app-input/div/div/div[1]/label");
    public static final By CARD_HOLDER_LABEL = By.xpath("/html/body/app-root/div/div/div/app-payment-container/section/div/app-card-page/div/div[1]/app-card-input/form/div[1]/div[3]/app-input/div/div/div[1]/label");

    public static final By PAYMENT_FRAME = By.xpath("/html/body/div[8]/div/iframe");
    public static final By AMOUNT_TEXT = By.xpath("/html/body/app-root/div/div/div/app-payment-container/section/div/div/div[1]/div[1]/span");
    public static final By PHONE_NUMBER_TEXT = By.xpath("/html/body/app-root/div/div/div/app-payment-container/section/div/div/div[2]/span");
}


