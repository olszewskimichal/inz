package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final By username = By.id("username");
    private final By password = By.id("password");
    private final By loginButton = By.id("loginSubmit");
    private final By registerLink = By.id("register");
    private final By errorMsg = By.id("errorMsg");
    private final WebDriver webDriver;

    public LoginPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void logInToApp(String login, String password) {
        this.typeUserName(login);
        this.typePassword(password);
        this.clickOnLoginButton();
    }

    public void typeUserName(String login) {
        this.webDriver.findElement(this.username).sendKeys(login);

    }

    public void typePassword(String pass) {
        this.webDriver.findElement(this.password).sendKeys(pass);
    }

    public String getErrorMsg() {
        return this.webDriver.findElement(this.errorMsg).getText();
    }

    public void clickOnLoginButton() {
        this.webDriver.findElement(this.loginButton).click();
    }

    public void clickOnRegisterLink() {
        this.webDriver.findElement(this.registerLink).click();
    }
}
