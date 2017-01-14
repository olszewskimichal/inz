package com.inz.praca.selenium;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import com.inz.praca.selenium.configuration.ScreenshotTestRule;
import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.NewProductPage;
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
		assertThat(driver.getPageSource()).contains("Wszystkie produkty");
		assertThat(driver.getTitle()).isEqualTo("Produkty");
	}
}
