package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NewCategoryPage {

  private final By name = By.id("name");
  private final By description = By.id("description");
  private final By submitButton = By.id("btnAdd");

  private final WebDriver webDriver;

  public NewCategoryPage(WebDriver webDriver) {
    this.webDriver = webDriver;
  }

  public void typeName(String name) {
    webDriver.findElement(this.name).sendKeys(name);
  }

  public void typeDesctiption(String description) {
    webDriver.findElement(this.description).sendKeys(description);
  }

  public void clickOnCreateCategoryButton() {
    webDriver.findElement(submitButton).click();
  }

}
