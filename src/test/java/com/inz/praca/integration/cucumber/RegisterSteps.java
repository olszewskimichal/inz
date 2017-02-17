package com.inz.praca.integration.cucumber;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.RegisterPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Test;
import org.openqa.selenium.By;

public class RegisterSteps extends SeleniumTestBase {
	private String name;
	private String lastName;
	private String email;
	private String password;
	private String confirmPassword;

	@Given("Podajac imie= (.*) nazwisko = (.*) email = (.*) oraz hasło (.*) i potwierdzeniu (.*)")
	public void useRegisterData(String name, String lastName, String email, String password, String confirmPassword) throws Exception {
		prepareBeforeTest();
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	@When("Przy kliknieciu zarejestruj")
	public void shouldPerformRegister() throws Exception {
		driver.get("http://localhost:" + port + "/register");
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.typeName(name);
		registerPage.typeLastName(lastName);
		registerPage.typeEmail(email);
		registerPage.typePassword(password);
		registerPage.typeConfirmPassword(confirmPassword);
		registerPage.clickOnRegisterButton();
	}

	@Then("Dostane (.*) komunikatów błedu")
	public void shouldGetResponseWithHttpStatusCode(int errorCount) throws Exception {
		assertThat(driver.findElements(By.className("error")).size()).isEqualTo(errorCount);
	}

	@Then("Otrzymamy bład że (.*)")
	public void shouldGetErrorWithExistingEmail(String error) throws Exception {
		driver.getPageSource().contains(error);
	}

	@Test
	public void test() {

	}
}
