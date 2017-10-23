package com.inz.praca.selenium;

import com.inz.praca.products.Product;
import com.inz.praca.products.ProductBuilder;
import com.inz.praca.products.ProductRepository;
import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.LoginPage;
import com.inz.praca.selenium.pageObjects.ProductPage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ShowProductSeleniumTest extends SeleniumTestBase {

    @Autowired
    ProductRepository repository;

    @Test
    public void shouldShowProductInfo() throws Exception {
        this.prepareBeforeTest();
        SeleniumTestBase.driver.get("http://localhost:" + this.port + "/login");
        this.loginPage = new LoginPage(SeleniumTestBase.driver);
        this.loginPage.logInToApp("admin@email.pl", "zaq1@WSX");

        Product product = this.repository.save(new ProductBuilder().withName("nameTest123456")
                .withDescription("test2")
                .withPrice(BigDecimal.valueOf(3))
                .createProduct());
        SeleniumTestBase.driver.get("http://localhost:" + this.port + "/products/product/" + product.getId());
        ProductPage page = new ProductPage(SeleniumTestBase.driver);
        assertThat(page.getName()).isEqualTo("nameTest123456");
        assertThat(page.getDescription()).isEqualTo("test2");
        assertThat(page.getPrice()).isEqualTo("3.00 PLN");
        page.clickProductsButton();
    }
}
