package com.inz.praca.selenium;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.io.IOException;

import com.inz.praca.repository.ProductRepository;
import com.inz.praca.selenium.configuration.ScreenshotTestRule;
import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.NewProductPage;
import com.inz.praca.selenium.pageObjects.ProductPage;
import org.assertj.core.api.Assertions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.springframework.beans.factory.annotation.Autowired;

public class CartSeleniumTest extends SeleniumTestBase {

	@Autowired
	ProductRepository repository;

	public static WebDriver driver;

	@Rule
	public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

	@BeforeClass
	public static void setBrowser() throws IOException {
		driver = browserConfiguration.firefox();
		System.out.println(driver);
		driver.manage().window().maximize();
	}

	@AfterClass
	public static void tearDown() {
		driver.quit();
	}

	@Test
	public void test(){
		repository.deleteAll();
		driver.get("http://localhost:" + port + "/addProduct");
		NewProductPage productPage = new NewProductPage(driver);
		productPage.typeName("test");
		productPage.typeDesctiption("test2");
		productPage.typePrice("3.0");
		productPage.typeUrl("url");
		productPage.clickOnCreateProductButton();
		assertThat(driver.getPageSource()).contains("Witamy w Naszym sklepie");
		assertThat(driver.getTitle()).isEqualTo("Strona główna");

		driver.get("http://localhost:" + port + "/addProduct");
		productPage = new NewProductPage(driver);
		productPage.typeName("test2");
		productPage.typeDesctiption("test2");
		productPage.typePrice("35.0");
		productPage.typeUrl("url");
		productPage.clickOnCreateProductButton();
		assertThat(driver.getPageSource()).contains("Witamy w Naszym sklepie");
		assertThat(driver.getTitle()).isEqualTo("Strona główna");

		driver.get("http://localhost:" + port + "/products");
		WebElement product0 = driver.findElement(By.id("product0"));
		product0.click();
		ProductPage page = new ProductPage(driver);
		Assertions.assertThat(page.getName()).isEqualTo("test");
		Assertions.assertThat(page.getDescription()).isEqualTo("test2");
		Assertions.assertThat(page.getPrice()).isEqualTo("3.00 PLN");
		page.clickOrderButton();

		driver.get("http://localhost:" + port + "/products");
		WebElement product1 = driver.findElement(By.id("product1"));
		product1.click();
		page = new ProductPage(driver);
		Assertions.assertThat(page.getName()).isEqualTo("test2");
		Assertions.assertThat(page.getDescription()).isEqualTo("test2");
		Assertions.assertThat(page.getPrice()).isEqualTo("35.00 PLN");
		page.clickOrderButton();

		driver.get("http://localhost:" + port + "/products");
		product1 = driver.findElement(By.id("product1"));
		product1.click();
		page = new ProductPage(driver);
		Assertions.assertThat(page.getName()).isEqualTo("test2");
		Assertions.assertThat(page.getDescription()).isEqualTo("test2");
		Assertions.assertThat(page.getPrice()).isEqualTo("35.00 PLN");
		page.clickOrderButton();

		driver.get("http://localhost:" + port + "/cart");
		int rowCount=driver.findElements(By.xpath("//table[@id='cartTable']/tbody/tr")).size();
		assertThat(rowCount).isEqualTo(4);
		WebElement cartItem0 = driver.findElement(By.id("cartItem0"));

		assertThat(cartItem0).isNotNull();
		WebElement cartItemName0 = driver.findElement(By.id("cartItemName0"));
		WebElement cartItemName1 = driver.findElement(By.id("cartItemName1"));
		assertThat(cartItemName0.getText()).isEqualTo("test");
		assertThat(cartItemName1.getText()).isEqualTo("test2");

		WebElement cartItemProductPrice0 = driver.findElement(By.id("cartItemProductPrice0"));
		WebElement cartItemProductPrice1 = driver.findElement(By.id("cartItemProductPrice1"));
		assertThat(cartItemProductPrice0.getText()).isEqualTo("3.00");
		assertThat(cartItemProductPrice1.getText()).isEqualTo("35.00");

		WebElement cartItemPrice0 = driver.findElement(By.id("cartItemPrice0"));
		WebElement cartItemPrice1 = driver.findElement(By.id("cartItemPrice1"));
		assertThat(cartItemPrice0.getText()).isEqualTo("3.00");
		assertThat(cartItemPrice1.getText()).isEqualTo("70.00");

		WebElement cartPrice = driver.findElement(By.id("cartPrice"));
		assertThat(cartPrice.getText()).isEqualTo("73.00 PLN");

		WebElement cartItemRemove0 = driver.findElement(By.id("cartItemRemove0"));
		cartItemRemove0.click();
		rowCount=driver.findElements(By.xpath("//table[@id='cartTable']/tbody/tr")).size();
		assertThat(rowCount).isEqualTo(3);

		cartPrice = driver.findElement(By.id("cartPrice"));
		assertThat(cartPrice.getText()).isEqualTo("70.00 PLN");
	}
}
