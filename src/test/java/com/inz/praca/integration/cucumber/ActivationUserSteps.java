package com.inz.praca.integration.cucumber;

import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.AuthenticatedNavigation;
import com.inz.praca.selenium.pageObjects.LoginPage;
import com.inz.praca.selenium.pageObjects.UsersPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class ActivationUserSteps extends SeleniumTestBase {

    @Given("Logujac sie na uzytkownika nieaktywnego")
    public void logAsNotActivatedUser() throws Exception {
        prepareBeforeTest();
        SeleniumTestBase.driver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPage(SeleniumTestBase.driver);
        loginPage.typeUserName("nieaktywny@email.pl");
        loginPage.typePassword("zaq1@WSX");
    }

    @Given("Logujac sie na zwyklego uzytkownika aktywnego")
    public void logAsActivatedUser() throws Exception {
        prepareBeforeTest();
        SeleniumTestBase.driver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPage(SeleniumTestBase.driver);
        loginPage.typeUserName("aktywny@email.pl");
        loginPage.typePassword("zaq1@WSX");
    }

    @Given("Po zalogowaniu na konto administratora, ma dostęp do panelu administracji uzytkowników")
    public void logAsAdmin() throws Exception {
        prepareBeforeTest();
        SeleniumTestBase.driver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPage(SeleniumTestBase.driver);
        loginPage.typeUserName("admin@email.pl");
        loginPage.typePassword("zaq1@WSX");
        loginPage.clickOnLoginButton();
    }

    @When("Po kliknieciu zaloguj")
    public void shouldPerformRegister() {
        loginPage = new LoginPage(SeleniumTestBase.driver);
        loginPage.clickOnLoginButton();
    }

    @When("Może aktywowac uzytkownika nieaktywnego")
    public void canActivateUser() {
        SeleniumTestBase.driver.get("http://localhost:" + port + "/users");
        UsersPage usersPage = new UsersPage(SeleniumTestBase.driver);
        usersPage.activateUser(0);
        assertThat(usersPage.getActivationMsg()).isEqualTo("Aktywowano uzytkownika nieaktywny@email.pl");
    }

    @When("Próbujac wejsc na panel administracji uzytkownikami")
    public void cantGoToUsersPage() {
        loginPage.clickOnLoginButton();
        SeleniumTestBase.driver.get("http://localhost:" + port + "/users");
    }

    @Then("Zostanie zwrocony komunikat (.*)")
    public void shouldGetErrorMsg(String msg) {
        assertThat(loginPage.getErrorMsg()).isEqualTo(msg);
    }

    @Then("Nie bedzie mial uprawnien, otrzyma komunikat (.*)")
    public void shouldGetAccessDeniedMsg(String msg) {
        assertThat(SeleniumTestBase.driver.getPageSource()).contains(msg);
    }

    @Then("Uzytkownik bedzie mogl sie zalagowac do systemu")
    public void userCanLogToApp() {
        SeleniumTestBase.driver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPage(SeleniumTestBase.driver);
        loginPage.logInToApp("nieaktywny@email.pl", "zaq1@WSX");
        AuthenticatedNavigation navigation = new AuthenticatedNavigation(SeleniumTestBase.driver);
        assertThat(navigation.getLoginName()).isEqualTo("nieaktywny@email.pl");
        assertThat(SeleniumTestBase.driver.getTitle()).isEqualTo("Strona główna");
    }

    @Then("Poprawnie zaloguje się do systemu")
    public void shouldGetErrorMsg() {
        AuthenticatedNavigation navigation = new AuthenticatedNavigation(SeleniumTestBase.driver);
        assertThat(navigation.getLoginName()).isEqualTo("aktywny@email.pl");
        assertThat(SeleniumTestBase.driver.getTitle()).isEqualTo("Strona główna");
    }


    @Test
    public void test() {

    }
}
