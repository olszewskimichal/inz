package com.inz.praca.selenium;

import static org.assertj.core.api.Assertions.assertThat;

import com.inz.praca.products.Product;
import com.inz.praca.products.ProductBuilder;
import com.inz.praca.products.ProductRepository;
import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.LoginPage;
import com.inz.praca.selenium.pageObjects.ProductPage;
import java.math.BigDecimal;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ShowProductSeleniumTest extends SeleniumTestBase {

  @Autowired
  ProductRepository repository;

  @Test
  public void shouldShowProductInfo() throws Exception {
    prepareBeforeTest();
    SeleniumTestBase.driver.get("http://localhost:" + port + "/login");
    loginPage = new LoginPage(SeleniumTestBase.driver);
    loginPage.logInToApp("admin@email.pl", "zaq1@WSX");

    Product product = repository.save(new ProductBuilder().withName("nameTest123456")
        .withDescription("test2")
        .withPrice(BigDecimal.valueOf(3))
        .createProduct());
    SeleniumTestBase.driver.get("http://localhost:" + port + "/products/product/" + product.getId());
    ProductPage page = new ProductPage(SeleniumTestBase.driver);
    assertThat(page.getName()).isEqualTo("nameTest123456");
    assertThat(page.getDescription()).isEqualTo("test2");
    assertThat(page.getPrice()).isEqualTo("3.00 PLN");
    page.clickProductsButton();
  }
}
