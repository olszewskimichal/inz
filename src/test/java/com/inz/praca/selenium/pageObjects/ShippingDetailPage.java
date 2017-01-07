package com.inz.praca.selenium.pageObjects;

import com.inz.praca.selenium.configuration.SeleniumTestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ShippingDetailPage extends SeleniumTestBase {
	private WebDriver webDriver;
	By houseNum = By.id("houseNum");
	By street = By.id("streetName");
	By city = By.id("areaName");
	By postCode = By.id("zipCode");
	By phoneNumber = By.id("phoneNumber");
	By submitButton = By.id("btnAdd");

	public ShippingDetailPage(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public void typeHouseNum(String number) {
		webDriver.findElement(houseNum).sendKeys(number);
	}

	public void typeStreet(String string) {
		webDriver.findElement(street).sendKeys(string);
	}

	public void typeCity(String city) {
		webDriver.findElement(this.city).sendKeys(city);
	}

	public void typePostCode(String postCode) {
		webDriver.findElement(this.postCode).sendKeys(postCode);
	}

	public void typePhoneNumber(String phoneNumber) {
		webDriver.findElement(this.phoneNumber).sendKeys(phoneNumber);
	}

	public void clickOnSubmitButton() {
		webDriver.findElement(submitButton).click();
	}

}
