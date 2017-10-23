package com.inz.praca.integration.cucumber;

import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.LoginPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class LoginSteps extends SeleniumTestBase {

    private LoginPage loginPage;

    @Given("Logujac sie na login (.*) z hasłem (.*)")
    public void loginToApp(String login, String pass) throws Exception {
        this.prepareBeforeTest();
        SeleniumTestBase.driver.get("http://localhost:" + this.port + "/login");
        this.loginPage = new LoginPage(SeleniumTestBase.driver);
        this.loginPage.typeUserName(login);
        this.loginPage.typePassword(pass);
    }

    @When("Przy kliknieciu zaloguj")
    public void clickOnLogin() {
        this.loginPage.clickOnLoginButton();
    }


    @Then("Zostanie przekierowany na strone głowna sklepu")
    public void shouldRedirectToMainPage() {
        assertThat(SeleniumTestBase.driver.getPageSource()).contains("Witamy w Naszym sklepie");
        assertThat(SeleniumTestBase.driver.getTitle()).isEqualTo("Strona główna");
    }


    @Then("Zostanie wyświetlony bład (.*)")
    public void shouldGetErrorMsg(String error) {
        Assertions.assertThat(SeleniumTestBase.driver.getPageSource()).contains(error);
        Assertions.assertThat(SeleniumTestBase.driver.getTitle()).isEqualTo("Logowanie");
    }

    @Test
    public void test() {

    }
}
