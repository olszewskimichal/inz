package com.inz.praca.selenium;

import com.inz.praca.orders.Order;
import com.inz.praca.products.ProductBuilder;
import com.inz.praca.products.ProductRepository;
import com.inz.praca.registration.User;
import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class OrderSteps extends SeleniumTestBase {

    @Autowired
    private ProductRepository productRepository;

    private void prepareCartToOrder(String cena) throws Exception {
        prepareBeforeTest();
        productRepository.deleteAll();
        productRepository.save(
                new ProductBuilder().withName("name2").withPrice(BigDecimal.valueOf(35)).createProduct());

        SeleniumTestBase.driver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPage(SeleniumTestBase.driver);
        loginPage.logInToApp("aktywny@email.pl", "zaq1@WSX");
        SeleniumTestBase.driver.get("http://localhost:" + port + "/products");
        ProductListPage productListPage = new ProductListPage(SeleniumTestBase.driver);
        productListPage.clickOnProductInfo(0);
        ProductPage page = new ProductPage(SeleniumTestBase.driver);
        page.clickOrderButton();

        SeleniumTestBase.driver.get("http://localhost:" + port + "/cart");
        CartPage cartPage = new CartPage(SeleniumTestBase.driver);
        assertThat(cartPage.getCartPrice()).isEqualTo(cena);
    }

    private void prepareCartToOrderAsRegularCustomer(String cena) throws Exception {
        prepareBeforeTest();
        productRepository.deleteAll();
        productRepository.save(
                new ProductBuilder().withName("name2").withPrice(BigDecimal.valueOf(35)).createProduct());
        User user = userRepository.findByEmail("aktywny@email.pl").get();
        user.addOrder(new Order());
        user.addOrder(new Order());
        user.addOrder(new Order());
        userRepository.save(user);
        System.out.println(user.getOrders().size());

        SeleniumTestBase.driver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPage(SeleniumTestBase.driver);
        loginPage.logInToApp("aktywny@email.pl", "zaq1@WSX");
        SeleniumTestBase.driver.get("http://localhost:" + port + "/products");
        ProductListPage productListPage = new ProductListPage(SeleniumTestBase.driver);
        productListPage.clickOnProductInfo(0);
        ProductPage page = new ProductPage(SeleniumTestBase.driver);
        page.clickOrderButton();

        SeleniumTestBase.driver.get("http://localhost:" + port + "/cart");
        CartPage cartPage = new CartPage(SeleniumTestBase.driver);
        assertThat(cartPage.getCartPrice()).isEqualTo(cena);
    }

    private void whenRedirectToMainPage() {
        CartPage cartPage = new CartPage(SeleniumTestBase.driver);
        cartPage.clickOrder();
        ShippingDetailPage shippingDetailPage = new ShippingDetailPage(SeleniumTestBase.driver);
        shippingDetailPage.typeCity("Bydgoszcz");
        shippingDetailPage.typeStreet("Unknown Street");
        shippingDetailPage.typePhoneNumber("1111111");
        shippingDetailPage.typePostCode("75-814");
        shippingDetailPage.typeHouseNum("4");
        shippingDetailPage.clickOnSubmitButton();
    }


    private void shouldGetOrderPage() {
        OrderPage orderPage = new OrderPage(SeleniumTestBase.driver);
        assertThat(orderPage.getStreet()).isEqualTo("Unknown Street 4");
        assertThat(orderPage.getCity()).isEqualTo("75-814 Bydgoszcz");
        assertThat(orderPage.getTotalPrice()).isEqualTo("35.00 PLN");
        assertThat(orderPage.getDate()).contains("Data wysyłki: ");
        assertThat(SeleniumTestBase.driver.getPageSource()).contains("Rachunek");
        assertThat(SeleniumTestBase.driver.getTitle()).isEqualTo("Potwierdzenie zamówienia");

        SeleniumTestBase.driver.get("http://localhost:" + port + "/cart");
        CartPage cartPage = new CartPage(SeleniumTestBase.driver);
        assertThat(cartPage.getCartTableSize()).isEqualTo(2);
    }

    private void shouldGetOrderPageWithDiscount(String price) {
        OrderPage orderPage = new OrderPage(SeleniumTestBase.driver);
        assertThat(orderPage.getTotalPrice()).isEqualTo(price);
        assertThat(SeleniumTestBase.driver.getPageSource()).contains("Rachunek");
        assertThat(SeleniumTestBase.driver.getTitle()).isEqualTo("Potwierdzenie zamówienia");

        SeleniumTestBase.driver.get("http://localhost:" + port + "/cart");
        CartPage cartPage = new CartPage(SeleniumTestBase.driver);
        assertThat(cartPage.getCartTableSize()).isEqualTo(2);
    }

    @Test
    public void shouldReturnConfirmationForOrder() throws Exception {
        prepareCartToOrder("35.00 PLN");
        whenRedirectToMainPage();
        shouldGetOrderPage();
    }

    @Test
    public void shouldReturnConfirmationForOrderWithDiscount() throws Exception {
        prepareCartToOrderAsRegularCustomer("35.00 PLN");
        whenRedirectToMainPage();
        shouldGetOrderPageWithDiscount("31.50 PLN");
    }
}
