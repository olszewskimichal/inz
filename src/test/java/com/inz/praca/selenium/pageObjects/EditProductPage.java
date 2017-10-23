package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EditProductPage {
    private final By name = By.id("name");
    private final By description = By.id("description");
    private final By price = By.id("unitPrice");
    private final By imageURL = By.id("imageURL");
    private final By submitButton = By.id("btnAdd");

    private final WebDriver webDriver;

    public EditProductPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void typeName(String name) {
        this.webDriver.findElement(this.name).sendKeys(name);
    }

    public void typeDesctiption(String description) {
        this.webDriver.findElement(this.description).sendKeys(description);
    }

    public void typePrice(String price) {
        this.webDriver.findElement(this.price).clear();
        this.webDriver.findElement(this.price).sendKeys(price);
    }

    public void typeUrl(String url) {
        this.webDriver.findElement(imageURL).sendKeys(url);
    }

    public void clickOnEditProductButton() {
        this.webDriver.findElement(this.submitButton).click();
    }

}
