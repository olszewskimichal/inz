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
		driver.get("http://localhost:" + port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.typeUserName("nieaktywny@email.pl");
		loginPage.typePassword("zaq1@WSX");
	}

	@Given("Logujac sie na zwyklego uzytkownika aktywnego")
	public void logAsActivatedUser() throws Exception {
		prepareBeforeTest();
		driver.get("http://localhost:" + port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.typeUserName("aktywny@email.pl");
		loginPage.typePassword("zaq1@WSX");
	}

	@Given("Po zalogowaniu na konto administratora, ma dostęp do panelu administracji uzytkowników")
	public void logAsAdmin() throws Exception {
		prepareBeforeTest();
		driver.get("http://localhost:" + port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.typeUserName("admin@email.pl");
		loginPage.typePassword("zaq1@WSX");
		loginPage.clickOnLoginButton();
	}

	@When("Po kliknieciu zaloguj")
	public void shouldPerformRegister() throws Exception {
		loginPage = new LoginPage(driver);
		loginPage.clickOnLoginButton();
	}

	@When("Może aktywowac uzytkownika nieaktywnego")
	public void canActivateUser() throws Exception {
		driver.get("http://localhost:" + port + "/users");
		UsersPage usersPage = new UsersPage(driver);
		usersPage.activateUser(0);
		assertThat(usersPage.getActivationMsg()).isEqualTo("Aktywowano uzytkownika nieaktywny@email.pl");
	}

	@When("Próbujac wejsc na panel administracji uzytkownikami")
	public void cantGoToUsersPage() throws Exception {
		loginPage.clickOnLoginButton();
		driver.get("http://localhost:" + port + "/users");
	}

	@Then("Zostanie zwrocony komunikat (.*)")
	public void shouldGetErrorMsg(String msg) throws Exception {
		assertThat(loginPage.getErrorMsg()).isEqualTo(msg);
	}

	@Then("Nie bedzie mial uprawnien, otrzyma komunikat (.*)")
	public void shouldGetAccessDeniedMsg(String msg) throws Exception {
		assertThat(driver.getPageSource()).contains(msg);
	}

	@Then("Uzytkownik bedzie mogl sie zalagowac do systemu")
	public void userCanLogToApp() throws Exception {
		driver.get("http://localhost:" + port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.logInToApp("nieaktywny@email.pl", "zaq1@WSX");
		AuthenticatedNavigation navigation = new AuthenticatedNavigation(driver);
		assertThat(navigation.getLoginName()).isEqualTo("nieaktywny@email.pl");
		assertThat(driver.getTitle()).isEqualTo("Strona główna");
	}

	@Then("Poprawnie zaloguje się do systemu")
	public void shouldGetErrorMsg() throws Exception {
		AuthenticatedNavigation navigation = new AuthenticatedNavigation(driver);
		assertThat(navigation.getLoginName()).isEqualTo("aktywny@email.pl");
		assertThat(driver.getTitle()).isEqualTo("Strona główna");
	}


	@Test
	public void test() {

	}
}
