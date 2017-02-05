package com.inz.praca.selenium;

import static org.assertj.core.api.Assertions.assertThat;

import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.RegisterPage;
import org.junit.Test;


public class RegisterSeleniumTest extends SeleniumTestBase {

	@Test
	public void shouldRegisterWithCorrectData() {
		driver.get("http://localhost:" + port + "/register");
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.typeName("userTest");
		registerPage.typeLastName("userTest");
		registerPage.typeEmail("userTestXXX@poczta.pl");
		registerPage.typePassword("zaq1@WSX");
		registerPage.typeConfirmPassword("zaq1@WSX");
		registerPage.clickOnRegisterButton();
		assertThat(driver.getPageSource()).contains("Witamy w Naszym sklepie");
		assertThat(driver.getTitle()).isEqualTo("Strona główna");
	}
}
