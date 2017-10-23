package com.inz.praca.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductListPage {

    private final WebDriver webDriver;

    public ProductListPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    private By productInfo(Integer id) {
        return By.id("product" + id);
    }

    public void clickOnProductInfo(Integer id) {
        webDriver.findElement(productInfo(id)).click();
    }

}
