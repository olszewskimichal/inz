package com.inz.praca.selenium;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.math.BigDecimal;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.domain.Product;
import com.inz.praca.domain.Role;
import com.inz.praca.domain.User;
import com.inz.praca.builders.UserBuilder;
import com.inz.praca.repository.ProductRepository;
import com.inz.praca.repository.UserRepository;
import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.EditProductPage;
import com.inz.praca.selenium.pageObjects.LoginPage;
import com.inz.praca.selenium.pageObjects.ProductPage;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("development")
public class EditProductSeleniumTest extends SeleniumTestBase {

	@Autowired
	ProductRepository repository;

	@Autowired
	UserRepository userRepository;

	@Test
	public void shouldEditProduct() {
		driver.manage().deleteAllCookies();
		userRepository.deleteAll();
		User admin = new UserBuilder().withEmail("admin@email.pl").withPasswordHash("zaq1@WSX").build();
		admin.setRole(Role.ADMIN);
		userRepository.save(admin);

		repository.deleteAll();
		Product product = repository.save(new ProductBuilder().withName("test").withDescription("test2").withPrice(BigDecimal.valueOf(3)).createProduct());
		driver.get("http://localhost:" + port + "/products/product/" + product.getId());
		LoginPage loginPage = new LoginPage(driver);
		loginPage.logInToApp("admin@email.pl", "zaq1@WSX");
		ProductPage productPage = new ProductPage(driver);
		productPage.clickOnEditProductButton();
		EditProductPage page = new EditProductPage(driver);
		page.typePrice(65 + "");
		page.clickOnEditProductButton();
		productPage = new ProductPage(driver);
		assertThat(productPage.getPrice()).isEqualTo("65.00 PLN");
	}

	@Test
	public void shouldGetErrorWhenTryEditProductAsUser() {
		driver.manage().deleteAllCookies();
		userRepository.deleteAll();
		User admin = new UserBuilder().withEmail("user@email.pl").withPasswordHash("zaq1@WSX").build();
		userRepository.save(admin);
		repository.deleteAll();
		Product product = repository.save(new ProductBuilder().withName("test").withDescription("test2").withPrice(BigDecimal.valueOf(3)).createProduct());
		driver.get("http://localhost:" + port + "/products/product/" + product.getId());
		LoginPage loginPage = new LoginPage(driver);
		loginPage.logInToApp("user@email.pl", "zaq1@WSX");
		driver.get("http://localhost:" + port + "/products/product/edit/" + product.getId());
		assertThat(driver.getPageSource().contains("zabroniony")).isTrue();
	}
}
