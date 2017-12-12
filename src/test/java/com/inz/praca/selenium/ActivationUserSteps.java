package com.inz.praca.selenium;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.AuthenticatedNavigation;
import com.inz.praca.selenium.pageObjects.LoginPage;
import com.inz.praca.selenium.pageObjects.UsersPage;
import org.junit.Test;


public class ActivationUserSteps extends SeleniumTestBase {

  private void logAsNotActivatedUser() throws Exception {
    prepareBeforeTest();
    SeleniumTestBase.driver.get("http://localhost:" + port + "/login");
    loginPage = new LoginPage(SeleniumTestBase.driver);
    loginPage.typeUserName("nieaktywny@email.pl");
    loginPage.typePassword("zaq1@WSX");
  }

  private void logAsActivatedUser() throws Exception {
    prepareBeforeTest();
    SeleniumTestBase.driver.get("http://localhost:" + port + "/login");
    loginPage = new LoginPage(SeleniumTestBase.driver);
    loginPage.typeUserName("aktywny@email.pl");
    loginPage.typePassword("zaq1@WSX");
  }

  private void logAsAdmin() throws Exception {
    prepareBeforeTest();
    SeleniumTestBase.driver.get("http://localhost:" + port + "/login");
    loginPage = new LoginPage(SeleniumTestBase.driver);
    loginPage.typeUserName("admin@email.pl");
    loginPage.typePassword("zaq1@WSX");
    loginPage.clickOnLoginButton();
  }

  private void shouldPerformRegister() {
    loginPage = new LoginPage(SeleniumTestBase.driver);
    loginPage.clickOnLoginButton();
  }

  private void canActivateUser() {
    SeleniumTestBase.driver.get("http://localhost:" + port + "/users");
    UsersPage usersPage = new UsersPage(SeleniumTestBase.driver);
    usersPage.activateUser(0);
    assertThat(usersPage.getActivationMsg()).isEqualTo("Aktywowano uzytkownika nieaktywny@email.pl");
  }

  private void cantGoToUsersPage() {
    loginPage.clickOnLoginButton();
    SeleniumTestBase.driver.get("http://localhost:" + port + "/users");
  }

  private void shouldGetErrorMsg(String msg) {
    assertThat(loginPage.getErrorMsg()).isEqualTo(msg);
  }

  private void shouldGetAccessDeniedMsg(String msg) {
    assertThat(SeleniumTestBase.driver.getPageSource()).contains(msg);
  }

  private void userCanLogToApp() {
    SeleniumTestBase.driver.get("http://localhost:" + port + "/login");
    loginPage = new LoginPage(SeleniumTestBase.driver);
    loginPage.logInToApp("nieaktywny@email.pl", "zaq1@WSX");
    AuthenticatedNavigation navigation = new AuthenticatedNavigation(SeleniumTestBase.driver);
    assertThat(navigation.getLoginName()).isEqualTo("nieaktywny@email.pl");
    assertThat(SeleniumTestBase.driver.getTitle()).isEqualTo("Strona główna");
  }

  private void shouldCorrectLoginToSystem() {
    AuthenticatedNavigation navigation = new AuthenticatedNavigation(SeleniumTestBase.driver);
    assertThat(navigation.getLoginName()).isEqualTo("aktywny@email.pl");
    assertThat(SeleniumTestBase.driver.getTitle()).isEqualTo("Strona główna");
  }


  @Test
  public void shouldReturnErrorWhenLoginOnNotActiveAccount() throws Exception {
    logAsNotActivatedUser();
    shouldPerformRegister();
    shouldGetErrorMsg("Twoje konto nie jest aktywne");
  }

  @Test
  public void shouldLoginToSystem() throws Exception {
    logAsActivatedUser();
    shouldPerformRegister();
    shouldCorrectLoginToSystem();
  }

  @Test
  public void shouldCanActiveAccountAndThenHeCanLoginToAccount() throws Exception {
    logAsAdmin();
    canActivateUser();
    userCanLogToApp();
  }

  @Test
  public void shouldReturnErrorWhenTryActivateAsNormalUser() throws Exception {
    logAsActivatedUser();
    cantGoToUsersPage();
    shouldGetAccessDeniedMsg("Dost?p zabroniony");
  }
}
