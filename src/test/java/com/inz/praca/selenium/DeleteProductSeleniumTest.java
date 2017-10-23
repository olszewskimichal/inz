package com.inz.praca.selenium;

import com.inz.praca.orders.OrderRepository;
import com.inz.praca.products.Product;
import com.inz.praca.products.ProductBuilder;
import com.inz.praca.products.ProductRepository;
import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.LoginPage;
import com.inz.praca.selenium.pageObjects.ProductPage;
import org.junit.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Java6Assertions.assertThat;

@ActiveProfiles("development")
public class DeleteProductSeleniumTest extends SeleniumTestBase {

    @Autowired
    ProductRepository repository;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void shouldDeleteProduct() throws Exception {
        orderRepository.deleteAll();
        repository.deleteAll();
        prepareBeforeTest();

        Product product = repository.save(new ProductBuilder().withName("nameTest1234567")
                .withDescription("test2")
                .withPrice(BigDecimal.valueOf(3))
                .createProduct());
        int size = repository.findAll().size();
        SeleniumTestBase.driver.get("http://localhost:" + port + "/products/product/" + product.getId());
        LoginPage loginPage = new LoginPage(SeleniumTestBase.driver);
        loginPage.logInToApp("admin@email.pl", "zaq1@WSX");
        ProductPage productPage = new ProductPage(SeleniumTestBase.driver);
        productPage.clickOnRemoveProductButton();
        if (SeleniumTestBase.driver instanceof HtmlUnitDriver) {
        } else {
            SeleniumTestBase.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            productPage.clickOnConfirmRemoveProduct();
            assertThat(repository.findAll().size()).isEqualTo(size - 1);
        }
    }
}
