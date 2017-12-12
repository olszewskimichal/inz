package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ProductPage {

  private final By submitDelete = By.id("submitdelete");
  private final By name = By.id("name");
  private final By description = By.id("description");
  private final By price = By.id("price");
  private final By orderButton = By.id("order");
  private final By cartButton = By.id("cart");
  private final By productsButton = By.id("products");
  private final By category = By.id("category");
  private final By editButton = By.id("editProduct");
  private final By removeButton = By.id("removeProduct");
  private final WebDriver webDriver;

  public ProductPage(WebDriver webDriver) {
    this.webDriver = webDriver;
  }

  public void clickOrderButton() {
    webDriver.findElement(orderButton).click();
  }

  public String getSelectedCategory() {
    Select categorySelect = new Select(webDriver.findElement(category));
    return categorySelect.getFirstSelectedOption().getText();
  }

  public void chooseCategoryByText(String name) {
    Select categorySelect = new Select(webDriver.findElement(category));
    for (WebElement option : categorySelect.getOptions()) {
      if (option.getText().equals(name)) {
        option.click();
      }
    }
  }

  public void clickCartButton() {
    webDriver.findElement(cartButton).click();
  }

  public void clickOnEditProductButton() {
    webDriver.findElement(editButton).click();
  }

  public void clickOnRemoveProductButton() {
    webDriver.findElement(removeButton).click();
  }

  public void clickOnConfirmRemoveProduct() {
    webDriver.findElement(submitDelete).click();
  }


  public void clickProductsButton() {
    webDriver.findElement(productsButton).click();
  }

  public String getName() {
    return webDriver.findElement(name).getText();
  }

  public String getDescription() {
    return webDriver.findElement(description).getText();
  }

  public String getPrice() {
    return webDriver.findElement(price).getText();
  }
}
