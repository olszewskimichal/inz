package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NewProductPage {
    private final By name = By.id("name");
    private final By description = By.id("description");
    private final By price = By.id("unitPrice");
    private final By imageURL = By.id("imageURL");
    private final By submitButton = By.id("btnAdd");

    private final WebDriver webDriver;

    public NewProductPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    private void typeName(String name) {
        webDriver.findElement(this.name).sendKeys(name);
    }

    private void typeDescription(String description) {
        webDriver.findElement(this.description).sendKeys(description);
    }

    private void typePrice(String price) {
        webDriver.findElement(this.price).sendKeys(price);
    }

    private void typeUrl(String url) {
        webDriver.findElement(imageURL).sendKeys(url);
    }

    public void clickOnCreateProductButton() {
        webDriver.findElement(submitButton).click();
    }

    public void fillCreateProductForm(String name, String description, String price, String url) {
        typeName(name);
        typeDescription(description);
        typePrice(price);
        typeUrl(url);
    }
}
