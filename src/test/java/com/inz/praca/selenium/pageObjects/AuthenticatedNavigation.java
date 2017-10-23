package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthenticatedNavigation extends Navigation {
    private final By loginName = By.id("loginName");
    private final By logout = By.id("logout");

    public AuthenticatedNavigation(WebDriver webDriver) {
        super(webDriver);
    }

    public void clickOnLogout() {
        this.webDriver.findElement(this.logout).click();
    }

    public String getLoginName() {
        return this.webDriver.findElement(this.loginName).getText();
    }

    public void clickOnLoginName() {
        this.webDriver.findElement(this.loginName).click();
    }

}
