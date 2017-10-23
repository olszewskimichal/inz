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
        typeUserName(login);
        typePassword(password);
        clickOnLoginButton();
    }

    public void typeUserName(String login) {
        webDriver.findElement(username).sendKeys(login);

    }

    public void typePassword(String pass) {
        webDriver.findElement(password).sendKeys(pass);
    }

    public String getErrorMsg() {
        return webDriver.findElement(errorMsg).getText();
    }

    public void clickOnLoginButton() {
        webDriver.findElement(loginButton).click();
    }

    public void clickOnRegisterLink() {
        webDriver.findElement(registerLink).click();
    }
}
