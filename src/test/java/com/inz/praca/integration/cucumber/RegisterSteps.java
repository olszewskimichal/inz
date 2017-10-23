package com.inz.praca.integration.cucumber;

import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.RegisterPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class RegisterSteps extends SeleniumTestBase {
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;

    @Given("Podajac imie= (.*) nazwisko = (.*) email = (.*) oraz hasło (.*) i potwierdzeniu (.*)")
    public void useRegisterData(String name, String lastName, String email, String password, String confirmPassword) throws Exception {
        this.prepareBeforeTest();
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    @When("Przy kliknieciu zarejestruj")
    public void shouldPerformRegister() {
        SeleniumTestBase.driver.get("http://localhost:" + this.port + "/register");
        RegisterPage registerPage = new RegisterPage(SeleniumTestBase.driver);
        registerPage.typeName(this.name);
        registerPage.typeLastName(this.lastName);
        registerPage.typeEmail(this.email);
        registerPage.typePassword(this.password);
        registerPage.typeConfirmPassword(this.confirmPassword);
        registerPage.clickOnRegisterButton();
    }

    @Then("Dostane (.*) komunikatów błedu")
    public void shouldGetResponseWithHttpStatusCode(int errorCount) {
        assertThat(SeleniumTestBase.driver.findElements(By.className("error")).size()).isEqualTo(errorCount);
    }

    @Then("Otrzymamy bład że (.*)")
    public void shouldGetErrorWithExistingEmail(String error) {
        SeleniumTestBase.driver.getPageSource().contains(error);
    }

    @Test
    public void test() {

    }
}
