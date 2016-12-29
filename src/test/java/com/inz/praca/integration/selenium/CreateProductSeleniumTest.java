package com.inz.praca.integration.selenium;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.io.IOException;

import com.inz.praca.integration.selenium.configuration.ScreenshotTestRule;
import com.inz.praca.integration.selenium.configuration.SeleniumTestBase;
import com.inz.praca.integration.selenium.pageObjects.NewProductPage;
import com.inz.praca.integration.selenium.pageObjects.ProductPage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class CreateProductSeleniumTest extends SeleniumTestBase {

	public static WebDriver driver;

	@Rule
	public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

	@BeforeClass
	public static void setBrowser() throws IOException {
		driver = browserConfiguration.firefox();
		driver.manage().window().maximize();
	}

	@AfterClass
	public static void tearDown() {
		driver.quit();
	}

	@Test
	public void shouldCreateNewProductWithCorrectData() {
		driver.get("http://localhost:" + port + "/addProduct");
		NewProductPage productPage = new NewProductPage(driver);
		productPage.typeName("test");
		productPage.typeDesctiption("test2");
		productPage.typePrice("3.0");
		productPage.typeUrl("url");
		productPage.clickOnCreateProductButton();
		assertThat(driver.getPageSource()).contains("Witamy w Naszym sklepie");
		assertThat(driver.getTitle()).isEqualTo("Strona główna");

		driver.get("http://localhost:" + port + "/products/product/1");
		ProductPage page = new ProductPage(driver);
		assertThat(page.getName()).isEqualTo("test");
		assertThat(page.getDescription()).isEqualTo("test2");
		assertThat(page.getPrice()).isEqualTo("3.00 PLN");
		page.clickProductsButton();
	}
}
