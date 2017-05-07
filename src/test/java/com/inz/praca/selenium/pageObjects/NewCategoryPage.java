package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NewCategoryPage {
    By name = By.id("name");
    By description = By.id("description");
    By submitButton = By.id("btnAdd");

    private WebDriver webDriver;

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
