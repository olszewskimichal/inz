package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage {
    private final By email = By.id("email");
    private final By name = By.id("name");
    private final By lastName = By.id("lastName");
    private final By password = By.id("password");
    private final By confirmPassword = By.id("confirmPassword");
    private final By registerButton = By.id("submit");
    private final WebDriver webDriver;

    public RegisterPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void typeEmail(String email) {
        this.webDriver.findElement(this.email).sendKeys(email);
    }

    public void typeName(String name) {
        this.webDriver.findElement(this.name).sendKeys(name);
    }

    public void typeLastName(String lastName) {
        this.webDriver.findElement(this.lastName).sendKeys(lastName);
    }

    public void typePassword(String pass) {
        this.webDriver.findElement(this.password).sendKeys(pass);
    }

    public void typeConfirmPassword(String confirmPassword) {
        this.webDriver.findElement(this.confirmPassword).sendKeys(confirmPassword);
    }

    public void clickOnRegisterButton() {
        this.webDriver.findElement(this.registerButton).click();
    }


}
