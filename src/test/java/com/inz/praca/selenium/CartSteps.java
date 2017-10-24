package com.inz.praca.selenium;

import com.inz.praca.products.ProductBuilder;
import com.inz.praca.products.ProductRepository;
import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.CartPage;
import com.inz.praca.selenium.pageObjects.LoginPage;
import com.inz.praca.selenium.pageObjects.ProductListPage;
import com.inz.praca.selenium.pageObjects.ProductPage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class CartSteps extends SeleniumTestBase {

    @Autowired
    ProductRepository productRepository;

    private void loggedAsUser() throws Exception {
        prepareBeforeTest();
        productRepository.deleteAll();
        productRepository.save(new ProductBuilder().withName("name1").withPrice(BigDecimal.valueOf(3)).createProduct());
        productRepository.save(
                new ProductBuilder().withName("name2").withPrice(BigDecimal.valueOf(35)).createProduct());
        productRepository.save(
                new ProductBuilder().withName("name3").withPrice(BigDecimal.valueOf(10)).createProduct());
        SeleniumTestBase.driver.get("http://localhost:" + port + "/login");
        LoginPage loginPage = new LoginPage(SeleniumTestBase.driver);
        loginPage.logInToApp("aktywny@email.pl", "zaq1@WSX");
    }

    private void whenAdd3ProductToCart(String cena) throws Exception {
        prepareBeforeTest();
        productRepository.deleteAll();
        productRepository.save(new ProductBuilder().withName("name1").withPrice(BigDecimal.valueOf(3)).createProduct());
        productRepository.save(
                new ProductBuilder().withName("name2").withPrice(BigDecimal.valueOf(35)).createProduct());
        productRepository.save(
                new ProductBuilder().withName("name3").withPrice(BigDecimal.valueOf(10)).createProduct());
        SeleniumTestBase.driver.get("http://localhost:" + port + "/login");
        LoginPage loginPage = new LoginPage(SeleniumTestBase.driver);
        loginPage.logInToApp("aktywny@email.pl", "zaq1@WSX");

        for (int i = 0; i < 3; i++) {
            SeleniumTestBase.driver.get("http://localhost:" + port + "/products");
            ProductListPage productListPage = new ProductListPage(SeleniumTestBase.driver);
            productListPage.clickOnProductInfo(i);
            ProductPage page = new ProductPage(SeleniumTestBase.driver);
            page.clickOrderButton();
        }
        SeleniumTestBase.driver.get("http://localhost:" + port + "/cart");
        CartPage cartPage = new CartPage(SeleniumTestBase.driver);
        assertThat(cartPage.getCartPrice()).isEqualTo(cena);
    }

    private void whenAdd2TheSameProductAnd1Another(String cena1, String cena2) {
        SeleniumTestBase.driver.get("http://localhost:" + port + "/products");
        ProductListPage productListPage = new ProductListPage(SeleniumTestBase.driver);
        productListPage.clickOnProductInfo(1);
        ProductPage page = new ProductPage(SeleniumTestBase.driver);
        assertThat(page.getPrice()).isEqualTo(cena1);
        page.clickOrderButton();

        SeleniumTestBase.driver.get("http://localhost:" + port + "/products");
        productListPage = new ProductListPage(SeleniumTestBase.driver);
        productListPage.clickOnProductInfo(1);
        page = new ProductPage(SeleniumTestBase.driver);
        page.clickOrderButton();

        SeleniumTestBase.driver.get("http://localhost:" + port + "/products");
        productListPage = new ProductListPage(SeleniumTestBase.driver);
        productListPage.clickOnProductInfo(0);
        page = new ProductPage(SeleniumTestBase.driver);
        assertThat(page.getPrice()).isEqualTo(cena2);
        page.clickOrderButton();
    }

    private void shouldRemove1Product(String cena) {
        SeleniumTestBase.driver.get("http://localhost:" + port + "/cart");
        CartPage cartPage = new CartPage(SeleniumTestBase.driver);
        assertThat(cartPage.getCartItemPrice(2)).isEqualTo(cena);
        cartPage.removeItem(2);
    }

    private void shouldReturnCartPrice(String cena, int ilosc) {
        SeleniumTestBase.driver.get("http://localhost:" + port + "/cart");
        CartPage cartPage = new CartPage(SeleniumTestBase.driver);
        assertThat(cartPage.getCartTableSize()).isEqualTo(ilosc + 2);
        assertThat(cartPage.getCartPrice()).isEqualTo(cena);
    }

    @Test
    public void shouldAdd2TheSameProductAndAnotherOneWithCorrectPrice() throws Exception {
        loggedAsUser();
        whenAdd2TheSameProductAnd1Another("35.00 PLN", "3.00 PLN");
        shouldReturnCartPrice("73.00 PLN", 2);
    }

    @Test
    public void shouldAdd3ProductAndOneRemoveWithCorrectPrice() throws Exception {
        whenAdd3ProductToCart("48.00 PLN");
        shouldRemove1Product("10.00");
        shouldReturnCartPrice("38.00 PLN", 2);
    }
}
