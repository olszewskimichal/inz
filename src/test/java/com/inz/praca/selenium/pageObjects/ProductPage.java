package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ProductPage {
    public By submitDelete = By.id("submitdelete");
    By name = By.id("name");
    By description = By.id("description");
    By price = By.id("price");
    By orderButton = By.id("order");
    By cartButton = By.id("cart");
    By productsButton = By.id("products");
    By category = By.id("category");
    By editButton = By.id("editProduct");
    By removeButton = By.id("removeProduct");
    private WebDriver webDriver;

    public ProductPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void clickOrderButton() {
        webDriver.findElement(this.orderButton).click();
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
        webDriver.findElement(this.cartButton).click();
    }

    public void clickOnEditProductButton() {
        webDriver.findElement(this.editButton).click();
    }

    public void clickOnRemoveProductButton() {
        webDriver.findElement(this.removeButton).click();
    }

    public void clickOnConfirmRemoveProduct() {
        webDriver.findElement(this.submitDelete).click();
    }


    public void clickProductsButton() {
        webDriver.findElement(this.productsButton).click();
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
