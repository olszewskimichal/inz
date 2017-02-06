package com.inz.praca.selenium;

import static org.assertj.core.api.Assertions.assertThat;

import com.inz.praca.domain.UserBuilder;
import com.inz.praca.repository.UserRepository;
import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.AuthenticatedNavigation;
import com.inz.praca.selenium.pageObjects.LoginPage;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("development")
public class LoginSeleniumTest extends SeleniumTestBase {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void shouldLoginWithCorrectAuthenticationAndLogoutAfterThat() {
		userRepository.save(new UserBuilder().withEmail("adminTest2@email.pl").withPasswordHash("zaq1@WSX").build());
		driver.get("http://localhost:" + port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.typeUserName("adminTest2@email.pl");
		loginPage.typePassword("zaq1@WSX");
		loginPage.clickOnLoginButton();

		assertThat(driver.getPageSource()).contains("Witamy w Naszym sklepie");
		assertThat(driver.getTitle()).isEqualTo("Strona główna");
		AuthenticatedNavigation authenticatedNavigation = new AuthenticatedNavigation(driver);
		assertThat(authenticatedNavigation.getLoginName()).isEqualTo("adminTest2@email.pl");
		authenticatedNavigation.clickOnLoginName();
		authenticatedNavigation.clickOnLogout();
		assertThat(driver.getTitle()).isEqualTo("Logowanie");
	}

	@Test
	public void shouldGetErrorWhenLoginWithIncorrectAuthentication() {
		driver.get("http://localhost:" + port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.typeUserName("admin");
		loginPage.typePassword("dupa");
		loginPage.clickOnLoginButton();
		assertThat(driver.getPageSource()).contains("Niepoprawny użytkownik lub hasło");
		assertThat(driver.getTitle()).isEqualTo("Logowanie");
	}

	@Test
	public void shouldRedirectToRegisterPage() {
		driver.get("http://localhost:" + port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.clickOnRegisterLink();
		assertThat(driver.getPageSource()).contains("Zarejestruj nowe konto");
		assertThat(driver.getTitle()).isEqualTo("Rejestracja");
	}

}
