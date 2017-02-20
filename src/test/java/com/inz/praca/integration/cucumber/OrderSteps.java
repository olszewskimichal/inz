package com.inz.praca.integration.cucumber;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.math.BigDecimal;

import com.inz.praca.products.ProductBuilder;
import com.inz.praca.products.ProductRepository;
import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.CartPage;
import com.inz.praca.selenium.pageObjects.LoginPage;
import com.inz.praca.selenium.pageObjects.OrderPage;
import com.inz.praca.selenium.pageObjects.ProductListPage;
import com.inz.praca.selenium.pageObjects.ProductPage;
import com.inz.praca.selenium.pageObjects.ShippingDetailPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;

public class OrderSteps extends SeleniumTestBase {

	@Autowired
	private ProductRepository productRepository;

	@Given("Posiadajac w koszyku 1 przedmiot wart (.*)")
	public void prepareCartToOrder(String cena) throws Exception {
		prepareBeforeTest();
		productRepository.deleteAll();
		productRepository.save(new ProductBuilder().withName("name2").withPrice(BigDecimal.valueOf(35)).createProduct());

		driver.get("http://localhost:" + port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.logInToApp("aktywny@email.pl", "zaq1@WSX");
		driver.get("http://localhost:" + port + "/products");
		ProductListPage productListPage = new ProductListPage(driver);
		productListPage.clickOnProductInfo(0);
		ProductPage page = new ProductPage(driver);
		page.clickOrderButton();

		driver.get("http://localhost:" + port + "/cart");
		CartPage cartPage = new CartPage(driver);
		assertThat(cartPage.getCartPrice()).isEqualTo(cena);
	}


	@When("Po kliknieciu kupuje i wypełnieniu danych do wysyłki")
	public void shouldRedirectToMainPage() throws Exception {
		CartPage cartPage = new CartPage(driver);
		cartPage.clickOrder();
		ShippingDetailPage shippingDetailPage = new ShippingDetailPage(driver);
		shippingDetailPage.typeCity("Bydgoszcz");
		shippingDetailPage.typeStreet("Unknown Street");
		shippingDetailPage.typePhoneNumber("1111111");
		shippingDetailPage.typePostCode("75-814");
		shippingDetailPage.typeHouseNum("4");
		shippingDetailPage.clickOnSubmitButton();
	}


	@Then("Otrzyma potwierdzenie złożenia zamowienia")
	public void shouldGetOrderPage() throws Exception {
		OrderPage orderPage = new OrderPage(driver);
		assertThat(orderPage.getStreet()).isEqualTo("Unknown Street 4");
		assertThat(orderPage.getCity()).isEqualTo("75-814 Bydgoszcz");
		assertThat(orderPage.getTotalPrice()).isEqualTo("35.00 PLN");
		assertThat(orderPage.getDate()).contains("Data wysyłki: ");
		assertThat(driver.getPageSource()).contains("Rachunek");
		assertThat(driver.getTitle()).isEqualTo("Potwierdzenie zamówienia");

		driver.get("http://localhost:" + port + "/cart");
		CartPage cartPage = new CartPage(driver);
		assertThat(cartPage.getCartTableSize()).isEqualTo(2);
	}

	@Test
	public void test() {

	}
}
