package com.inz.praca.selenium;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.inz.praca.domain.Category;
import com.inz.praca.domain.Role;
import com.inz.praca.domain.User;
import com.inz.praca.builders.UserBuilder;
import com.inz.praca.repository.CategoryRepository;
import com.inz.praca.repository.ProductRepository;
import com.inz.praca.repository.UserRepository;
import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.CartPage;
import com.inz.praca.selenium.pageObjects.LoginPage;
import com.inz.praca.selenium.pageObjects.NewProductPage;
import com.inz.praca.selenium.pageObjects.OrderPage;
import com.inz.praca.selenium.pageObjects.ProductListPage;
import com.inz.praca.selenium.pageObjects.ProductPage;
import com.inz.praca.selenium.pageObjects.ShippingDetailPage;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("development")
public class OrderSeleniumTest extends SeleniumTestBase {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void shouldCreateOrder() throws InterruptedException {
		driver.manage().deleteAllCookies();
		userRepository.deleteAll();
		User admin = new UserBuilder().withEmail("admin@email.pl").withPasswordHash("zaq1@WSX").build();
		admin.setRole(Role.ADMIN);
		userRepository.save(admin);

		driver.get("http://localhost:" + port + "/cart");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.logInToApp("admin@email.pl", "zaq1@WSX");
		CartPage cartPage = new CartPage(driver);
		cartPage.clearCart();
		repository.deleteAll();
		categoryRepository.deleteAll();
		categoryRepository.save(new Category("a", "b"));
		driver.get("http://localhost:" + port + "/addProduct");

		NewProductPage productPage = new NewProductPage(driver);
		productPage.fillCreateProductForm("test", "test2", "3.0", "url");
		productPage.clickOnCreateProductButton();
		assertThat(driver.getPageSource()).contains("Wszystkie produkty");
		assertThat(driver.getTitle()).isEqualTo("Produkty");

		ProductListPage productListPage = new ProductListPage(driver);
		productListPage.clickOnProductInfo(0);

		ProductPage page = new ProductPage(driver);
		Assertions.assertThat(page.getName()).isEqualTo("test");
		Assertions.assertThat(page.getDescription()).isEqualTo("test2");
		Assertions.assertThat(page.getPrice()).isEqualTo("3.00 PLN");
		page.clickOrderButton();

		driver.get("http://localhost:" + port + "/cart");
		cartPage = new CartPage(driver);
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

		driver.get("http://localhost:" + port + "/cart");
		cartPage = new CartPage(driver);
		assertThat(cartPage.getCartTableSize()).isEqualTo(2);
	}

}
