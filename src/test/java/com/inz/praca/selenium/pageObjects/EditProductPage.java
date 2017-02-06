package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EditProductPage {
	By name = By.id("name");
	By description = By.id("description");
	By price = By.id("unitPrice");
	By imageURL = By.id("imageURL");
	By submitButton = By.id("btnAdd");

	private WebDriver webDriver;

	public EditProductPage(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public void typeName(String name) {
		webDriver.findElement(this.name).sendKeys(name);
	}

	public void typeDesctiption(String description) {
		webDriver.findElement(this.description).sendKeys(description);
	}

	public void typePrice(String price) {
		webDriver.findElement(this.price).clear();
		webDriver.findElement(this.price).sendKeys(price);
	}

	public void typeUrl(String url) {
		webDriver.findElement(this.imageURL).sendKeys(url);
	}

	public void clickOnEditProductButton() {
		webDriver.findElement(submitButton).click();
	}

}
