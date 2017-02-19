package com.inz.praca.integration.cucumber;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.LoginPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class LoginSteps extends SeleniumTestBase {

	LoginPage loginPage;

	@Given("Logujac sie na login (.*) z hasłem (.*)")
	public void loginToApp(String login, String pass) throws Exception {
		prepareBeforeTest();
		driver.get("http://localhost:" + port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.typeUserName(login);
		loginPage.typePassword(pass);
	}

	@When("Przy kliknieciu zaloguj")
	public void clickOnLogin() throws Exception {
		loginPage.clickOnLoginButton();
	}


	@Then("Zostanie przekierowany na strone głowna sklepu")
	public void shouldRedirectToMainPage() throws Exception {
		assertThat(driver.getPageSource()).contains("Witamy w Naszym sklepie");
		assertThat(driver.getTitle()).isEqualTo("Strona główna");
	}


	@Then("Zostanie wyświetlony bład (.*)")
	public void shouldGetErrorMsg(String error) throws Exception {
		Assertions.assertThat(driver.getPageSource()).contains(error);
		Assertions.assertThat(driver.getTitle()).isEqualTo("Logowanie");
	}

	@Test
	public void test() {

	}
}
