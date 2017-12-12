package com.inz.praca.selenium;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.LoginPage;
import com.inz.praca.selenium.pageObjects.NewProductPage;
import org.junit.Test;
import org.openqa.selenium.By;

public class CreateProductSteps extends SeleniumTestBase {

  private String name;
  private String description;
  private String price;

  private void useNewProductData(String name, String description, String price, String category) throws Exception {
    prepareBeforeTest();
    SeleniumTestBase.driver.get("http://localhost:" + port + "/login");
    loginPage = new LoginPage(SeleniumTestBase.driver);
    loginPage.logInToApp("admin@email.pl", "zaq1@WSX");

    this.name = name;
    this.description = description;
    this.price = price;
  }

  private void whenPerformNewProduct() {
    SeleniumTestBase.driver.get("http://localhost:" + port + "/addProduct");
    NewProductPage productPage = new NewProductPage(SeleniumTestBase.driver);
    productPage.fillCreateProductForm(name, description, price, "");
    productPage.clickOnCreateProductButton();
  }

  private void shouldGetResponseWithErrorCount(int errorCount) {
    assertThat(SeleniumTestBase.driver.findElements(By.className("error")).size()).isEqualTo(errorCount);
  }

  private void shouldContainsErrorMessage(String error) {
    SeleniumTestBase.driver.getPageSource().contains(error);
  }

  @Test
  public void shouldAddNewProduct() throws Exception {
    useNewProductData("nazwa", "opis", "3.0", "inne");
    whenPerformNewProduct();
    shouldGetResponseWithErrorCount(0);
  }

  @Test
  public void shouldReturnErrorWhenNameIsTooShort() throws Exception {
    useNewProductData("na", "opis", "4.0", "inne");
    whenPerformNewProduct();
    shouldContainsErrorMessage("Podana nazwa jest zbyt krótka");

  }

  @Test
  public void shouldReturnErrorWhenIncorrectPrice() throws Exception {
    useNewProductData("nazwa", "opis", "-4.0", "inne");
    whenPerformNewProduct();
    shouldContainsErrorMessage("Nieprawidłowo podana cena");
  }

}
