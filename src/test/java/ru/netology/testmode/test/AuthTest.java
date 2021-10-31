package ru.netology.testmode.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.testmode.data.Registration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.AddUser.*;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void testPositiveWithValidUser() {
        Registration testUser = addValidUser();
        $("[data-test-id=login] input").setValue(testUser.getLogin());
        $("[data-test-id=password] input").setValue(testUser.getPassword());
        $(".button__content").click();
        $(".App_appContainer__3jRx1 h2").shouldBe(visible).shouldHave(text("Личный кабинет"));
    }

    @Test
    void testNegativeWithBlockedUser() {
        Registration testUser = addBlockedUser();
        $("[data-test-id=login] input").setValue(testUser.getLogin());
        $("[data-test-id=password] input").setValue(testUser.getPassword());
        $(".button__content").click();
        $("[data-test-id=error-notification]").shouldBe(visible).shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    void testNegativeNotValidUserLogin() {
        Registration testUser = addNotValidUserLogin();
        $("[data-test-id=login] input").setValue(testUser.getLogin());
        $("[data-test-id=password] input").setValue(testUser.getPassword());
        $(".button__content").click();
        $("[data-test-id=error-notification]").shouldBe(visible).shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void testNegativeNotValidUserPassword() {
        Registration testUser = addNotValidUserPassword();
        $("[data-test-id=login] input").setValue(testUser.getLogin());
        $("[data-test-id=password] input").setValue(testUser.getPassword());
        $(".button__content").click();
        $("[data-test-id=error-notification]").shouldBe(visible).shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void negativeNotRegisteredUser() {
        Registration testUser = addNotRegisteredUser();
        $("[data-test-id=login] input").setValue(testUser.getLogin());
        $("[data-test-id=password] input").setValue(testUser.getPassword());
        $(".button__content").click();
        $("[data-test-id=error-notification]").shouldBe(visible).shouldHave(text("Неверно указан логин или пароль"));
    }
}