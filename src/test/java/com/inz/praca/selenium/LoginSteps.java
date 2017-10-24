package com.inz.praca.selenium;

import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.LoginPage;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class LoginSteps extends SeleniumTestBase {

    private LoginPage loginPage;

    private void loginToApp(String login, String pass) throws Exception {
        prepareBeforeTest();
        SeleniumTestBase.driver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPage(SeleniumTestBase.driver);
        loginPage.typeUserName(login);
        loginPage.typePassword(pass);
    }

    private void clickOnLogin() {
        loginPage.clickOnLoginButton();
    }


    private void shouldRedirectToMainPage() {
        assertThat(SeleniumTestBase.driver.getPageSource()).contains("Witamy w Naszym sklepie");
        assertThat(SeleniumTestBase.driver.getTitle()).isEqualTo("Strona główna");
    }


    private void shouldGetErrorMsg(String error) {
        Assertions.assertThat(SeleniumTestBase.driver.getPageSource()).contains(error);
        Assertions.assertThat(SeleniumTestBase.driver.getTitle()).isEqualTo("Logowanie");
    }

    @Test
    public void shouldCorrectLoginToSystem() throws Exception {
        loginToApp("admin@email.pl","zaq1@WSX");
        clickOnLogin();
        shouldRedirectToMainPage();
    }

    @Test
    public void shouldShowErrorWhenIncorrectPassword() throws Exception {
        loginToApp("admin@email.pl","zaq1@WSX1");
        clickOnLogin();
        shouldGetErrorMsg("Niepoprawny użytkownik lub hasło");
    }

    @Test
    public void shouldShowErrorWhenInactiveAccount() throws Exception {
        loginToApp("nieaktywny@email.pl","zaq1@WSX");
        clickOnLogin();
        shouldGetErrorMsg("Twoje konto nie jest aktywne");
    }
}
