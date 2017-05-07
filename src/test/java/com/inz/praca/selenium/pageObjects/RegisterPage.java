package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage {
    private By email = By.id("email");
    private By name = By.id("name");
    private By lastName = By.id("lastName");
    private By password = By.id("password");
    private By confirmPassword = By.id("confirmPassword");
    private By registerButton = By.id("submit");
    private WebDriver webDriver;

    public RegisterPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void typeEmail(String email) {
        webDriver.findElement(this.email).sendKeys(email);
    }

    public void typeName(String name) {
        webDriver.findElement(this.name).sendKeys(name);
    }

    public void typeLastName(String lastName) {
        webDriver.findElement(this.lastName).sendKeys(lastName);
    }

    public void typePassword(String pass) {
        webDriver.findElement(password).sendKeys(pass);
    }

    public void typeConfirmPassword(String confirmPassword) {
        webDriver.findElement(this.confirmPassword).sendKeys(confirmPassword);
    }

    public void clickOnRegisterButton() {
        webDriver.findElement(registerButton).click();
    }


}
