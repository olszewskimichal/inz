package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {

  private final By cartTable = By.xpath("//table[@id='cartTable']/tbody/tr");
  private final By cartPrice = By.id("cartPrice");
  private final By cartClearButton = By.id("cartClear");
  private final By order = By.id("order");
  private final WebDriver webDriver;

  public CartPage(WebDriver webDriver) {
    this.webDriver = webDriver;
  }

  private By cartItemName(Integer id) {
    return By.id("cartItemName" + id);
  }

  private By cartItemPrice(Integer id) {
    return By.id("cartItemPrice" + id);
  }

  private By cartItemProductPrice(Integer id) {
    return By.id("cartItemProductPrice" + id);
  }

  private By cartItemRemove(Integer id) {
    return By.id("cartItemRemove" + id);
  }

  public String getCartItemPrice(Integer id) {
    return webDriver.findElement(cartItemPrice(id)).getText();
  }

  public String getCartItemName(Integer id) {
    return webDriver.findElement(cartItemName(id)).getText();
  }

  public String getCartPrice() {
    return webDriver.findElement(cartPrice).getText();
  }

  public Integer getCartTableSize() {
    return webDriver.findElements(cartTable).size();
  }

  public String getCartItemProductPrice(Integer id) {
    return webDriver.findElement(cartItemProductPrice(id)).getText();
  }

  public void removeItem(Integer id) {
    webDriver.findElement(cartItemRemove(id)).click();
  }

  public void clearCart() {
    webDriver.findElement(cartClearButton).click();
  }

  public void clickOrder() {
    webDriver.findElement(order).click();
  }

}
