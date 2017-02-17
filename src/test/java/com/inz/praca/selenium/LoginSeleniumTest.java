package com.inz.praca.selenium;

import static org.assertj.core.api.Assertions.assertThat;

import com.inz.praca.repository.UserRepository;
import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.AuthenticatedNavigation;
import com.inz.praca.selenium.pageObjects.LoginPage;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;

public class LoginSeleniumTest extends SeleniumTestBase {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		userRepository.deleteAll();
	}

	@Test
	public void shouldLoginWithCorrectAuthenticationAndLogoutAfterThat() throws Exception {
		prepareBeforeTest();
		driver.get("http://localhost:" + port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.typeUserName("admin@email.pl");
		loginPage.typePassword("zaq1@WSX");
		loginPage.clickOnLoginButton();

		assertThat(driver.getPageSource()).contains("Witamy w Naszym sklepie");
		assertThat(driver.getTitle()).isEqualTo("Strona główna");
		AuthenticatedNavigation authenticatedNavigation = new AuthenticatedNavigation(driver);
		assertThat(authenticatedNavigation.getLoginName()).isEqualTo("admin@email.pl");
		authenticatedNavigation.clickOnLoginName();
		authenticatedNavigation.clickOnLogout();
		assertThat(driver.getTitle()).isEqualTo("Logowanie");
	}

	@Test
	public void shouldGetErrorWhenLoginWithIncorrectAuthentication() throws Exception {
		prepareBeforeTest();
		driver.get("http://localhost:" + port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.typeUserName("admin");
		loginPage.typePassword("dupa");
		loginPage.clickOnLoginButton();
		assertThat(driver.getPageSource()).contains("Niepoprawny użytkownik lub hasło");
		assertThat(driver.getTitle()).isEqualTo("Logowanie");
	}

	@Test
	public void shouldGetErrorWhenUserIsNotActive() throws Exception {
		prepareBeforeTest();
		driver.get("http://localhost:" + port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.typeUserName("nieaktywny@email.pl");
		loginPage.typePassword("zaq1@WSX");
		loginPage.clickOnLoginButton();
		assertThat(driver.getPageSource()).contains("Twoje konto nie jest aktywne");
		assertThat(driver.getTitle()).isEqualTo("Logowanie");
	}

	@Test
	public void shouldRedirectToRegisterPage() throws Exception {
		prepareBeforeTest();
		driver.get("http://localhost:" + port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.clickOnRegisterLink();
		assertThat(driver.getPageSource()).contains("Zarejestruj nowe konto");
		assertThat(driver.getTitle()).isEqualTo("Rejestracja");
	}

}
