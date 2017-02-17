package com.inz.praca.integration.cucumber;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.LoginPage;
import com.inz.praca.selenium.pageObjects.NewProductPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Test;
import org.openqa.selenium.By;

public class CreateProductSteps extends SeleniumTestBase {
	private String name;
	private String description;
	private String price;
	private String category;


	@Given("Podajac  nazwe= (.*) z opisem = (.*) cena = (.*) oraz wybrana kategoria (.*)")
	public void useNewProductData(String name, String description, String price, String category) throws Exception {
		prepareBeforeTest();
		driver.get("http://localhost:" + port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.logInToApp("admin@email.pl", "zaq1@WSX");


		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
	}

	@When("Przy kliknieciu dodaj")
	public void shouldPerformNewProduct() throws Exception {
		driver.get("http://localhost:" + port + "/addProduct");
		NewProductPage productPage = new NewProductPage(driver);
		productPage.fillCreateProductForm(name, description, price, "");
		productPage.clickOnCreateProductButton();
	}

	@Then("Otrzymamy (.*) komunikatów błedu, w przypadku 0 błedów - zostanie stworzony nowy produkt")
	public void shouldGetResponseWithErrorCount(int errorCount) throws Exception {
		assertThat(driver.findElements(By.className("error")).size()).isEqualTo(errorCount);
	}

	@Then("Otrzymamy komunikat (.*)")
	public void shouldContainsErrorMessage(String error) throws Exception {
		driver.getPageSource().contains(error);
	}

	@Test
	public void test() {

	}

}
