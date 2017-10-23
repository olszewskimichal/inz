package com.inz.praca.integration.cucumber;

import com.inz.praca.orders.Order;
import com.inz.praca.products.ProductBuilder;
import com.inz.praca.products.ProductRepository;
import com.inz.praca.registration.User;
import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class OrderSteps extends SeleniumTestBase {

    @Autowired
    private ProductRepository productRepository;

    @Given("Posiadajac w koszyku 1 przedmiot wart (.*)")
    public void prepareCartToOrder(String cena) throws Exception {
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

    @Given("Jako staly klient posiadajac w koszyku 1 przedmiot wart (.*)")
    public void prepareCartToOrderAsRegularCustomer(String cena) throws Exception {
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

    @When("Po kliknieciu kupuje i wypełnieniu danych do wysyłki")
    public void shouldRedirectToMainPage() {
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


    @Then("Otrzyma potwierdzenie złożenia zamowienia")
    public void shouldGetOrderPage() {
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

    @Then("Jako staly klient otrzyma potwierdzenie złożenia zamowienia na kwote (.*)")
    public void shouldGetOrderPageWithDiscount(String price) {
        OrderPage orderPage = new OrderPage(SeleniumTestBase.driver);
        assertThat(orderPage.getTotalPrice()).isEqualTo(price);
        assertThat(SeleniumTestBase.driver.getPageSource()).contains("Rachunek");
        assertThat(SeleniumTestBase.driver.getTitle()).isEqualTo("Potwierdzenie zamówienia");

        SeleniumTestBase.driver.get("http://localhost:" + port + "/cart");
        CartPage cartPage = new CartPage(SeleniumTestBase.driver);
        assertThat(cartPage.getCartTableSize()).isEqualTo(2);
    }

    @Test
    public void test() {

    }
}
