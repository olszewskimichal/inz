package com.inz.praca.selenium;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.domain.Product;
import com.inz.praca.domain.Role;
import com.inz.praca.domain.User;
import com.inz.praca.builders.UserBuilder;
import com.inz.praca.repository.OrderRepository;
import com.inz.praca.repository.ProductRepository;
import com.inz.praca.repository.UserRepository;
import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.LoginPage;
import com.inz.praca.selenium.pageObjects.ProductPage;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("development")
public class DeleteProductSeleniumTest extends SeleniumTestBase {

	@Autowired
	ProductRepository repository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	OrderRepository orderRepository;

	@Test
	public void shouldDeleteProduct() throws InterruptedException {
		orderRepository.deleteAll();
		repository.deleteAll();
		userRepository.deleteAll();
		User admin = new UserBuilder().withEmail("admin@email.pl").withPasswordHash("zaq1@WSX").build();
		admin.setRole(Role.ADMIN);
		userRepository.save(admin);
		
		Product product = repository.save(new ProductBuilder().withName("nameTest1234567").withDescription("test2").withPrice(BigDecimal.valueOf(3)).createProduct());
		int size = repository.findAll().size();
		driver.get("http://localhost:" + port + "/products/product/" + product.getId());
		LoginPage loginPage = new LoginPage(driver);
		loginPage.logInToApp("admin@email.pl", "zaq1@WSX");
		ProductPage productPage = new ProductPage(driver);
		productPage.clickOnRemoveProductButton();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		productPage.clickOnConfirmRemoveProduct();
		assertThat(repository.findAll().size()).isEqualTo(size - 1);
	}
}
