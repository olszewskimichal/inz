package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UsersPage {

    private final By activationMsg = By.id("activationMsg");
    private final WebDriver webDriver;

    public UsersPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    private By activationUserLink(Integer id) {
        return By.id("user" + id);
    }

    public void activateUser(Integer id) {
        webDriver.findElement(activationUserLink(id)).click();
    }

    public String getActivationMsg() {
        return webDriver.findElement(activationMsg).getText();
    }
}
