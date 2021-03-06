package com.inz.praca.selenium;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.inz.praca.category.Category;
import com.inz.praca.category.CategoryRepository;
import com.inz.praca.products.Product;
import com.inz.praca.products.ProductBuilder;
import com.inz.praca.products.ProductRepository;
import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.EditProductPage;
import com.inz.praca.selenium.pageObjects.LoginPage;
import com.inz.praca.selenium.pageObjects.ProductPage;
import java.math.BigDecimal;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("development")
public class EditProductSeleniumTest extends SeleniumTestBase {

  @Autowired
  ProductRepository repository;

  @Autowired
  CategoryRepository categoryRepository;

  @Test
  public void shouldEditProduct() throws Exception {
    prepareBeforeTest();
    SeleniumTestBase.driver.manage().deleteAllCookies();

    repository.deleteAll();
    categoryRepository.deleteAll();
    Product product = repository.save(new ProductBuilder().withName("test")
        .withDescription("test2")
        .withPrice(BigDecimal.valueOf(3))
        .createProduct());
    categoryRepository.save(new Category("test", "opis"));
    SeleniumTestBase.driver.get("http://localhost:" + port + "/products/product/" + product.getId());
    LoginPage loginPage = new LoginPage(SeleniumTestBase.driver);
    loginPage.logInToApp("admin@email.pl", "zaq1@WSX");
    ProductPage productPage = new ProductPage(SeleniumTestBase.driver);
    productPage.clickOnEditProductButton();
    EditProductPage page = new EditProductPage(SeleniumTestBase.driver);
    page.typePrice(65 + "");
    page.clickOnEditProductButton();
    productPage = new ProductPage(SeleniumTestBase.driver);
    assertThat(productPage.getPrice()).isEqualTo("65.00 PLN");
  }

  @Test
  public void shouldGetErrorWhenTryEditProductAsUser() throws Exception {
    prepareBeforeTest();
    SeleniumTestBase.driver.manage().deleteAllCookies();
    repository.deleteAll();
    categoryRepository.deleteAll();
    Product product = repository.save(new ProductBuilder().withName("test")
        .withDescription("test2")
        .withPrice(BigDecimal.valueOf(3))
        .createProduct());
    categoryRepository.save(new Category("test", "opis"));
    SeleniumTestBase.driver.get("http://localhost:" + port + "/products/product/" + product.getId());
    LoginPage loginPage = new LoginPage(SeleniumTestBase.driver);
    loginPage.logInToApp("aktywny@email.pl", "zaq1@WSX");
    SeleniumTestBase.driver.get("http://localhost:" + port + "/products/product/edit/" + product.getId());
    assertThat(SeleniumTestBase.driver.getPageSource().contains("zabroniony")).isTrue();
  }
}
