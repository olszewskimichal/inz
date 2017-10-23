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
        this.webDriver.findElement(this.name).sendKeys(name);
    }

    private void typeDescription(String description) {
        this.webDriver.findElement(this.description).sendKeys(description);
    }

    private void typePrice(String price) {
        this.webDriver.findElement(this.price).sendKeys(price);
    }

    private void typeUrl(String url) {
        this.webDriver.findElement(imageURL).sendKeys(url);
    }

    public void clickOnCreateProductButton() {
        this.webDriver.findElement(this.submitButton).click();
    }

    public void fillCreateProductForm(String name, String description, String price, String url) {
        this.typeName(name);
        this.typeDescription(description);
        this.typePrice(price);
        this.typeUrl(url);
    }
}
