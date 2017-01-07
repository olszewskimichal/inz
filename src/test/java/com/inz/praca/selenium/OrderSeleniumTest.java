package com.inz.praca.selenium;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.io.IOException;

import com.inz.praca.selenium.configuration.ScreenshotTestRule;
import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.CartPage;
import com.inz.praca.selenium.pageObjects.NewProductPage;
import com.inz.praca.selenium.pageObjects.OrderPage;
import com.inz.praca.selenium.pageObjects.ProductListPage;
import com.inz.praca.selenium.pageObjects.ProductPage;
import com.inz.praca.selenium.pageObjects.ShippingDetailPage;
import org.assertj.core.api.Assertions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class OrderSeleniumTest extends SeleniumTestBase {

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
	public void shouldCreateOrder() throws InterruptedException {
		driver.get("http://localhost:" + port + "/addProduct");
		NewProductPage productPage = new NewProductPage(driver);
		productPage.fillCreateProductForm("test", "test2", "3.0", "url");
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

		driver.get("http://localhost:" + port + "/cart");
		CartPage cartPage = new CartPage(driver);
		assertThat(cartPage.getCartTableSize()).isEqualTo(3);
		assertThat(cartPage.getCartItemName(0)).isEqualTo("test");
		assertThat(cartPage.getCartItemProductPrice(0)).isEqualTo("3.00");
		assertThat(cartPage.getCartItemPrice(0)).isEqualTo("3.00");
		assertThat(cartPage.getCartPrice()).isEqualTo("3.00 PLN");

		cartPage.clickOrder();
		ShippingDetailPage shippingDetailPage = new ShippingDetailPage(driver);
		shippingDetailPage.typeCity("Bydgoszcz");
		shippingDetailPage.typeStreet("Unknown Street");
		shippingDetailPage.typePhoneNumber("1111111");
		shippingDetailPage.typePostCode("75-814");
		shippingDetailPage.typeHouseNum("4");
		shippingDetailPage.clickOnSubmitButton();

		OrderPage orderPage = new OrderPage(driver);
		assertThat(orderPage.getStreet()).isEqualTo("Unknown Street 4");
		assertThat(orderPage.getCity()).isEqualTo("75-814 Bydgoszcz");
		assertThat(orderPage.getTotalPrice()).isEqualTo("3.00 PLN");
		assertThat(orderPage.getDate()).contains("Data wysyłki: ");
		assertThat(driver.getPageSource()).contains("Rachunek");
		assertThat(driver.getTitle()).isEqualTo("Potwierdzenie zamówienia");
	}

}
