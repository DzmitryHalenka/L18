package by.mts.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;

public class PaymentPage extends BasePage {

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    public void acceptCookies() {
        acceptCookiesIfPresent(Locators.COOKIE_BLOCK, Locators.ACCEPT_COOKIES_BTN);
    }

    public void checkPayBlock() {
        WebElement payBlock = find(Locators.PAY_BLOCK_NAME);
        Assert.assertNotNull(payBlock, "Блок с платежной информацией не найден.");
    }

    public void checkPayBlockName(String expectedTitle) {
        WebElement payBlockTitle = find(Locators.PAY_BLOCK_NAME);
        String actualTitle = payBlockTitle.getText();
        Assert.assertEquals(actualTitle, expectedTitle, "Название блока не соответствует ожидаемому");
    }

    public void selectService(String serviceName) {
        try {
            click(Locators.SERVICES_BUTTON);
            By selectedServiceLocator = By.xpath("//ul[@class='select__list']//p[@class='select__option' and text()='" + serviceName + "']");
            click(selectedServiceLocator);
            System.out.println("Услуга \"" + serviceName + "\" успешно выбрана.");
        } catch (Exception e) {
            System.out.println("Ошибка при выборе услуги: " + e.getMessage());
            Assert.fail("Не удалось выбрать услугу: " + serviceName);
        }
    }

    public void checkSelectedService(String expectedServiceName) {
        WebElement selectedService = find(Locators.SELECTED_SERVICE_TEXT);
        String actualServiceName = selectedService.getText();
        System.out.println("Фактическое название выбранной услуги: " + actualServiceName);
        Assert.assertEquals(actualServiceName, expectedServiceName, "Ошибка: неверно выбрана услуга");
    }

    public void clearFields(By phoneFieldLocator, By amountFieldLocator, By emailFieldLocator) {
        try {
            setText(phoneFieldLocator, "");
            setText(amountFieldLocator, "");
            setText(emailFieldLocator, "");
            System.out.println("Поля успешно очищены.");
        } catch (Exception e) {
            System.out.println("Ошибка при очистке полей: " + e.getMessage());
        }
    }

    public void enterData(By phoneFieldLocator, By amountFieldLocator, By emailFieldLocator, String phoneNumber, String amount, String email) {
        try {
            setText(phoneFieldLocator, phoneNumber);
            setText(amountFieldLocator, amount);
            setText(emailFieldLocator, email);
            System.out.println("Данные успешно введены: номер телефона - " + phoneNumber + ", сумма - " + amount + ", email - " + email);
        } catch (Exception e) {
            System.out.println("Ошибка при вводе данных: " + e.getMessage());
        }
    }

    public void checkAndClickButton(By locator) {
        try {
            Assert.assertTrue(isElementVisible(locator), "Кнопка не видна");
            click(locator);
            System.out.println("Кнопка по локатору " + locator + " успешно кликнута.");
        } catch (Exception e) {
            System.out.println("Ошибка при клике по кнопке: " + e.getMessage());
        }
    }

    public void checkLogoVisibility(By locator, String logoName) {
        boolean isVisible = isElementVisible(locator);
        Assert.assertTrue(isVisible, logoName + " логотип не виден.");
    }

    public void verifyService(String serviceName, String phoneFieldPlaceholder, String amountFieldPlaceholder, String emailFieldPlaceholder) {
        selectService(serviceName);
        checkSelectedService(serviceName);

        // Получаем локаторы для полей в зависимости от выбранной услуги
        FieldLocators fieldLocators = getFieldLocatorsForService(serviceName);

        // Проверка placeholder для полей
        checkPlaceholder(fieldLocators.phoneField, phoneFieldPlaceholder);
        checkPlaceholder(fieldLocators.amountField, amountFieldPlaceholder);
        checkPlaceholder(fieldLocators.emailField, emailFieldPlaceholder);
    }

    // Вспомогательный метод для получения локаторов полей в зависимости от услуги
    private FieldLocators getFieldLocatorsForService(String serviceName) {
        return switch (serviceName) {
            case "Услуги связи" ->
                    new FieldLocators(Locators.PHONE_NUMBER_FIELD_CONNECTION, Locators.AMOUNT_FIELD_CONNECTION, Locators.EMAIL_FIELD_CONNECTION);
            case "Домашний интернет" ->
                    new FieldLocators(Locators.PHONE_NUMBER_FIELD_INTERNET, Locators.AMOUNT_FIELD_INTERNET, Locators.EMAIL_FIELD_INTERNET);
            case "Рассрочка" ->
                    new FieldLocators(Locators.PHONE_NUMBER_FIELD_INSTALMENT, Locators.AMOUNT_FIELD_INSTALMENT, Locators.EMAIL_FIELD_INSTALMENT);
            case "Задолженность" ->
                    new FieldLocators(Locators.PHONE_NUMBER_FIELD_ARREARS, Locators.AMOUNT_FIELD_ARREARS, Locators.EMAIL_FIELD_ARREARS);
            default -> throw new IllegalArgumentException("Unknown service: " + serviceName);
        };
    }

    // Вспомогательный класс для хранения локаторов
    private static class FieldLocators {
        By phoneField;
        By amountField;
        By emailField;

        FieldLocators(By phoneField, By amountField, By emailField) {
            this.phoneField = phoneField;
            this.amountField = amountField;
            this.emailField = emailField;
        }
    }


    private void checkPlaceholder(By locator, String expectedPlaceholder) {
        WebElement field = find(locator);
        Assert.assertNotNull(field, "Поле не найдено: " + locator);
        String actualPlaceholder = (String) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].getAttribute('placeholder');", field);
        Assert.assertNotNull(actualPlaceholder, "Плейсхолдер отсутствует у поля: " + locator);
        Assert.assertEquals(actualPlaceholder, expectedPlaceholder, "Ошибка в плейсхолдере для " + locator);
        System.out.println("Плейсхолдер для поля " + locator + " проверен успешно: " + actualPlaceholder);
    }


    public void enterData(String phone, String amount, String email) {
        setText(Locators.PHONE_NUMBER_FIELD_CONNECTION, phone);
        setText(Locators.AMOUNT_FIELD_CONNECTION, amount);
        setText(Locators.EMAIL_FIELD_CONNECTION, email);
    }
}
