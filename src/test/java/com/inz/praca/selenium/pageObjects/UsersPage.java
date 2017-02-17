package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UsersPage {

	By activationUserLink(Integer id) {
		return By.id("user" + id);
	}

	By activationMsg = By.id("activationMsg");

	public void activateUser(Integer id) {
		webDriver.findElement(activationUserLink(id)).click();
	}

	private WebDriver webDriver;

	public String getActivationMsg() {
		return webDriver.findElement(activationMsg).getText();
	}

	public UsersPage(WebDriver webDriver) {
		this.webDriver = webDriver;
	}
}
