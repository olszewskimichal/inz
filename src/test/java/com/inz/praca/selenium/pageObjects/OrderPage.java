package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderPage {

	private WebDriver webDriver;

	By street = By.id("street");
	By city = By.id("city");
	By date = By.id("date");
	By totalPrice = By.id("totalPrice");

	public OrderPage(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public String getStreet() {
		return webDriver.findElement(street).getText();
	}

	public String getCity() {
		return webDriver.findElement(city).getText();
	}

	public String getDate() {
		return webDriver.findElement(date).getText();
	}

	public String getTotalPrice() {
		return webDriver.findElement(totalPrice).getText();
	}
}
