package com.inz.praca.integration.cucumber;

import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.RegisterPage;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class RegisterSteps extends SeleniumTestBase {
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;

    private void useRegisterData(String name, String lastName, String email, String password, String confirmPassword) throws Exception {
        prepareBeforeTest();
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    private void whenPerformRegister() {
        SeleniumTestBase.driver.get("http://localhost:" + port + "/register");
        RegisterPage registerPage = new RegisterPage(SeleniumTestBase.driver);
        registerPage.typeName(name);
        registerPage.typeLastName(lastName);
        registerPage.typeEmail(email);
        registerPage.typePassword(password);
        registerPage.typeConfirmPassword(confirmPassword);
        registerPage.clickOnRegisterButton();
    }

    private void shouldGetResponseWithErrorCount(int errorCount) {
        assertThat(SeleniumTestBase.driver.findElements(By.className("error")).size()).isEqualTo(errorCount);
    }

    private void shouldGetErrorMessage(String error) {
        SeleniumTestBase.driver.getPageSource().contains(error);
    }

    @Test
    public void shouldCorrectRegister() throws Exception {
        useRegisterData("imie","nazwisko","email@o2.pl","zaq1@WSX","zaq1@WSX");
        whenPerformRegister();
        shouldGetResponseWithErrorCount(0);
    }

    @Test
    public void shouldReturnErrorWhenRegisterAsExistingUser() throws Exception {
        useRegisterData("imie","nazwisko","istniejacyemail@o2.pl","zaq1@WSX","zaq1@WSX");
        whenPerformRegister();
        shouldGetErrorMessage("Podany uzytkownik istnieje");
    }

    @Test
    public void shouldReturnErrorWhenPasswordIsTooEasy() throws Exception {
        useRegisterData("imie","nazwisko","istniejacyemail@o2.pl","aaaaa","aaaaa");
        whenPerformRegister();
        shouldGetErrorMessage("Podane hasło jest zbyt proste");
    }

    @Test
    public void shouldReturnErrorWhenTooShortName() throws Exception {
        useRegisterData("im","nazwisko","nowy@o2.pl","zaq1@WSX","zaq1@WSX");
        whenPerformRegister();
        shouldGetErrorMessage("Podane imie jest zbyt krótkie");
    }

    @Test
    public void shouldReturnErrorWhenEmptyName() throws Exception {
        useRegisterData("","nazwisko","nowy@o2.pl","zaq1@WSX","zaq1@WSX");
        whenPerformRegister();
        shouldGetErrorMessage("Imie nie moze byc puste");
    }

    @Test
    public void shouldReturnErrorWhenIncorrectMail() throws Exception {
        useRegisterData("imie","nazwisko","nowy.pl","zaq1@WSX","zaq1@WSX");
        whenPerformRegister();
        shouldGetErrorMessage("Nieprawidłowy adres email");
    }

    @Test
    public void shouldReturnErrorWhenConfPasswordIsWrong() throws Exception {
        useRegisterData("imie","nazwisko","nowy@o2.pl","zaq1@WSX","zaq12@WSX");
        whenPerformRegister();
        shouldGetErrorMessage("Nieprawidłowy adres email");
    }
}
