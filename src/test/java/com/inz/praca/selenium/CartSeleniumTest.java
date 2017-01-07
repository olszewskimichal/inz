package com.inz.praca.selenium;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.io.IOException;

import com.inz.praca.domain.Category;
import com.inz.praca.repository.CategoryRepository;
import com.inz.praca.repository.ProductRepository;
import com.inz.praca.selenium.configuration.ScreenshotTestRule;
import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.CartPage;
import com.inz.praca.selenium.pageObjects.NewProductPage;
import com.inz.praca.selenium.pageObjects.ProductListPage;
import com.inz.praca.selenium.pageObjects.ProductPage;
import org.assertj.core.api.Assertions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import org.springframework.beans.factory.annotation.Autowired;

public class CartSeleniumTest extends SeleniumTestBase {

	@Autowired
	ProductRepository repository;

	@Autowired
	private CategoryRepository categoryRepository;

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
	public void shouldCreate2RroductAndAddToCartAndRemoveOneOfThem() throws InterruptedException {
		repository.deleteAll();
		categoryRepository.deleteAll();
		categoryRepository.save(new Category("a", "b"));
		driver.get("http://localhost:" + port + "/addProduct");
		NewProductPage productPage = new NewProductPage(driver);
		productPage.fillCreateProductForm("test", "test2", "3.0", "url");
		productPage.clickOnCreateProductButton();
		assertThat(driver.getPageSource()).contains("Witamy w Naszym sklepie");
		assertThat(driver.getTitle()).isEqualTo("Strona główna");

		driver.get("http://localhost:" + port + "/addProduct");
		productPage = new NewProductPage(driver);
		productPage.fillCreateProductForm("test2", "test2", "35.0", "url");
		productPage.clickOnCreateProductButton();
		assertThat(driver.getPageSource()).contains("Witamy w Naszym sklepie");
		assertThat(driver.getTitle()).isEqualTo("Strona główna");

		driver.get("http://localhost:" + port + "/products");
		ProductListPage productListPage = new ProductListPage(driver);
		productListPage.clickOnProductInfo(0);

		ProductPage page = new ProductPage(driver);
		Assertions.assertThat(page.getName()).isEqualTo("test");
		Assertions.assertThat(page.getDescription()).isEqualTo("test2");
		Assertions.assertThat(page.getPrice()).isEqualTo("3.00 PLN");
		page.clickOrderButton();

		driver.get("http://localhost:" + port + "/products");
		productListPage = new ProductListPage(driver);
		productListPage.clickOnProductInfo(1);
		page = new ProductPage(driver);
		Assertions.assertThat(page.getName()).isEqualTo("test2");
		Assertions.assertThat(page.getDescription()).isEqualTo("test2");
		Assertions.assertThat(page.getPrice()).isEqualTo("35.00 PLN");
		page.clickOrderButton();

		driver.get("http://localhost:" + port + "/products");
		productListPage = new ProductListPage(driver);
		productListPage.clickOnProductInfo(1);
		page = new ProductPage(driver);
		Assertions.assertThat(page.getName()).isEqualTo("test2");
		Assertions.assertThat(page.getDescription()).isEqualTo("test2");
		Assertions.assertThat(page.getPrice()).isEqualTo("35.00 PLN");
		page.clickOrderButton();

		driver.get("http://localhost:" + port + "/cart");
		CartPage cartPage = new CartPage(driver);
		assertThat(cartPage.getCartTableSize()).isEqualTo(4);
		assertThat(cartPage.getCartItemName(0)).isEqualTo("test");
		assertThat(cartPage.getCartItemName(1)).isEqualTo("test2");
		assertThat(cartPage.getCartItemProductPrice(0)).isEqualTo("3.00");
		assertThat(cartPage.getCartItemProductPrice(1)).isEqualTo("35.00");
		assertThat(cartPage.getCartItemPrice(0)).isEqualTo("3.00");
		assertThat(cartPage.getCartItemPrice(1)).isEqualTo("70.00");
		assertThat(cartPage.getCartPrice()).isEqualTo("73.00 PLN");

		cartPage.removeItem(0);
		assertThat(cartPage.getCartTableSize()).isEqualTo(3);
		assertThat(cartPage.getCartPrice()).isEqualTo("70.00 PLN");
		cartPage.clearCart();
	}

	@Test
	public void shouldCreate2ProductAndAddToCartAndClearCart() {
		repository.deleteAll();
		categoryRepository.deleteAll();
		categoryRepository.save(new Category("a", "b"));

		driver.get("http://localhost:" + port + "/addProduct");
		NewProductPage productPage = new NewProductPage(driver);
		productPage.fillCreateProductForm("test", "test2", "3.0", "url");
		productPage.clickOnCreateProductButton();
		assertThat(driver.getPageSource()).contains("Witamy w Naszym sklepie");
		assertThat(driver.getTitle()).isEqualTo("Strona główna");

		driver.get("http://localhost:" + port + "/addProduct");
		productPage = new NewProductPage(driver);
		productPage.fillCreateProductForm("test2", "test2", "35.0", "url");
		productPage.clickOnCreateProductButton();
		assertThat(driver.getPageSource()).contains("Witamy w Naszym sklepie");
		assertThat(driver.getTitle()).isEqualTo("Strona główna");

		driver.get("http://localhost:" + port + "/products");
		ProductListPage productListPage = new ProductListPage(driver);
		productListPage.clickOnProductInfo(0);
		ProductPage page = new ProductPage(driver);
		Assertions.assertThat(page.getName()).isEqualTo("test");
		Assertions.assertThat(page.getDescription()).isEqualTo("test2");
		Assertions.assertThat(page.getPrice()).isEqualTo("3.00 PLN");
		page.clickOrderButton();

		driver.get("http://localhost:" + port + "/products");
		productListPage = new ProductListPage(driver);
		productListPage.clickOnProductInfo(1);
		page = new ProductPage(driver);
		Assertions.assertThat(page.getName()).isEqualTo("test2");
		Assertions.assertThat(page.getDescription()).isEqualTo("test2");
		Assertions.assertThat(page.getPrice()).isEqualTo("35.00 PLN");
		page.clickOrderButton();

		driver.get("http://localhost:" + port + "/products");
		productListPage = new ProductListPage(driver);
		productListPage.clickOnProductInfo(1);
		page = new ProductPage(driver);
		Assertions.assertThat(page.getName()).isEqualTo("test2");
		Assertions.assertThat(page.getDescription()).isEqualTo("test2");
		Assertions.assertThat(page.getPrice()).isEqualTo("35.00 PLN");
		page.clickOrderButton();

		driver.get("http://localhost:" + port + "/cart");
		CartPage cartPage = new CartPage(driver);
		assertThat(cartPage.getCartTableSize()).isEqualTo(4);
		cartPage.clearCart();
		assertThat(cartPage.getCartTableSize()).isEqualTo(2);
		assertThat(cartPage.getCartPrice()).isEqualTo("0 PLN");
	}
}
