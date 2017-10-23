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
        webDriver.findElement(logout).click();
    }

    public String getLoginName() {
        return webDriver.findElement(loginName).getText();
    }

    public void clickOnLoginName() {
        webDriver.findElement(loginName).click();
    }

}
