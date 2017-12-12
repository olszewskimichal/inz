package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

class NonAuthenticatedNavigation extends Navigation {

  private final By login = By.id("loginPage");

  public NonAuthenticatedNavigation(WebDriver webDriver) {
    super(webDriver);
  }

  public void clickOnLoginPage() {
    webDriver.findElement(login).click();
  }
}
