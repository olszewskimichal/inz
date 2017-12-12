package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderPage {

  private final By street = By.id("street");
  private final By city = By.id("city");
  private final By date = By.id("date");
  private final By totalPrice = By.id("totalPrice");
  private final WebDriver webDriver;

  public OrderPage(WebDriver webDriver) {
    this.webDriver = webDriver;
  }

  public String getStreet() {
    return webDriver.findElement(street).getText();
  }

  public String getCity() {
    return webDriver.findElement(city).getText();
  }

  public String getDate() {
    return webDriver.findElement(date).getText();
  }

  public String getTotalPrice() {
    return webDriver.findElement(totalPrice).getText();
  }
}
