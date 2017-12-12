package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

class Navigation {

  final WebDriver webDriver;
  private final By home = By.id("home");
  private final By pl = By.id("pl");

  Navigation(WebDriver webDriver) {
    this.webDriver = webDriver;
  }

  public void clickHome() {
    webDriver.findElement(home).click();
  }

  public void clickToPolishLang() {
    webDriver.findElement(pl).click();
  }

}
