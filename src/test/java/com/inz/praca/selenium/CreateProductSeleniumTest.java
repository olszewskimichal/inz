package com.inz.praca.selenium;

import static org.assertj.core.api.Assertions.assertThat;

import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.NewProductPage;
import org.junit.Test;

public class CreateProductSeleniumTest extends SeleniumTestBase {

	@Test
	public void shouldCreateNewProductWithCorrectData() {
		driver.get("http://localhost:" + port + "/addProduct");
		NewProductPage productPage = new NewProductPage(driver);
		productPage.typeName("test");
		productPage.typeDesctiption("test2");
		productPage.typePrice("3.0");
		productPage.typeUrl("url");
		productPage.clickOnCreateProductButton();
		assertThat(driver.getPageSource()).contains("Wszystkie produkty");
		assertThat(driver.getTitle()).isEqualTo("Produkty");
	}
}
