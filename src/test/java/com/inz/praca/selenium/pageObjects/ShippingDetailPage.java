package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ShippingDetailPage {
    private final By houseNum = By.id("houseNum");
    private final By street = By.id("streetName");
    private final By city = By.id("areaName");
    private final By postCode = By.id("zipCode");
    private final By phoneNumber = By.id("phoneNumber");
    private final By submitButton = By.id("btnAdd");
    private final WebDriver webDriver;

    public ShippingDetailPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void typeHouseNum(String number) {
        this.webDriver.findElement(this.houseNum).sendKeys(number);
    }

    public void typeStreet(String string) {
        this.webDriver.findElement(this.street).sendKeys(string);
    }

    public void typeCity(String city) {
        this.webDriver.findElement(this.city).sendKeys(city);
    }

    public void typePostCode(String postCode) {
        this.webDriver.findElement(this.postCode).sendKeys(postCode);
    }

    public void typePhoneNumber(String phoneNumber) {
        this.webDriver.findElement(this.phoneNumber).sendKeys(phoneNumber);
    }

    public void clickOnSubmitButton() {
        this.webDriver.findElement(this.submitButton).click();
    }

}
