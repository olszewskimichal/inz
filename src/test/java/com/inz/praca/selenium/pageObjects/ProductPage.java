package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage {
	By name = By.id("name");
	By description = By.id("description");
	By price = By.id("price");
	By orderButton = By.id("order");
	By cartButton = By.id("cart");
	By productsButton = By.id("products");

	private WebDriver webDriver;

	public ProductPage(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public void clickOrderButton() {
		webDriver.findElement(this.orderButton).click();
	}

	public void clickCartButton() {
		webDriver.findElement(this.cartButton).click();
	}

	public void clickProductsButton() {
		webDriver.findElement(this.productsButton).click();
	}

	public String getName() {
		return webDriver.findElement(name).getText();
	}

	public String getDescription() {
		return webDriver.findElement(description).getText();
	}

	public String getPrice() {
		return webDriver.findElement(price).getText();
	}
}
