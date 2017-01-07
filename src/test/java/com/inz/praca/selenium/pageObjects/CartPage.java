package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {

	private WebDriver webDriver;

	public CartPage(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	By cartTable = By.xpath("//table[@id='cartTable']/tbody/tr");

	By cartItemName(Integer id) {
		return By.id("cartItemName" + id);
	}

	By cartItemPrice(Integer id) {
		return By.id("cartItemPrice" + id);
	}

	By cartItemProductPrice(Integer id) {
		return By.id("cartItemProductPrice" + id);
	}

	By cartItemRemove(Integer id) {
		return By.id("cartItemRemove" + id);
	}

	By cartPrice = By.id("cartPrice");

	By cartClearButton = By.id("cartClear");

	By order = By.id("order");

	public String getCartItemPrice(Integer id) {
		return webDriver.findElement(cartItemPrice(id)).getText();
	}

	public String getCartItemName(Integer id) {
		return webDriver.findElement(cartItemName(id)).getText();
	}

	public String getCartPrice() {
		return webDriver.findElement(cartPrice).getText();
	}

	public Integer getCartTableSize() {
		return webDriver.findElements(cartTable).size();
	}

	public String getCartItemProductPrice(Integer id) {
		return webDriver.findElement(cartItemProductPrice(id)).getText();
	}

	public void removeItem(Integer id) {
		webDriver.findElement(cartItemRemove(id)).click();
	}

	public void clearCart() {
		webDriver.findElement(cartClearButton).click();
	}

	public void clickOrder() {
		webDriver.findElement(order).click();
	}

}
