package com.inz.praca.selenium;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.domain.Product;
import com.inz.praca.selenium.configuration.ScreenshotTestRule;
import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.ProductPage;
import com.inz.praca.repository.ProductRepository;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import org.springframework.beans.factory.annotation.Autowired;

public class ShowProductSeleniumTest extends SeleniumTestBase {

	public static WebDriver driver;

	@Autowired
	ProductRepository repository;

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
	public void shouldShowProductInfo() {
		repository.deleteAll();
		Product product = repository.save(new ProductBuilder().withName("test").withDescription("test2").withPrice(BigDecimal.valueOf(3)).createProduct());
		driver.get("http://localhost:" + port + "/products/product/" + product.getId());
		ProductPage page = new ProductPage(driver);
		assertThat(page.getName()).isEqualTo("test");
		assertThat(page.getDescription()).isEqualTo("test2");
		assertThat(page.getPrice()).isEqualTo("3.00 PLN");
		page.clickProductsButton();
	}
}
