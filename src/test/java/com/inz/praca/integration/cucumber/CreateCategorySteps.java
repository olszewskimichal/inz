package com.inz.praca.integration.cucumber;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.LoginPage;
import com.inz.praca.selenium.pageObjects.NewCategoryPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Test;
import org.openqa.selenium.By;

public class CreateCategorySteps extends SeleniumTestBase {
	private String name;
	private String description;

	@Given("Mając  nazwe kategori  (.*) z opisem  (.*)")
	public void useNewProductData(String name, String description) throws Exception {
		prepareBeforeTest();
		driver.get("http://localhost:" + port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.logInToApp("admin@email.pl", "zaq1@WSX");

		this.name = name;
		this.description = description;
	}

	@When("Po kliknieciu dodaj kategorie")
	public void shouldPerformNewCategory() throws Exception {
		driver.get("http://localhost:" + port + "/addCategory");
		NewCategoryPage newCategoryPage = new NewCategoryPage(driver);
		newCategoryPage.typeName(name);
		newCategoryPage.typeDesctiption(description);
		newCategoryPage.clickOnCreateCategoryButton();
	}

	@Then("Otrzymamy (.*) komunikatów błedu, w przypadku 0 błedów - zostanie stworzona nowa kategoria")
	public void shouldGetResponseWithErrorCount(int errorCount) throws Exception {
		assertThat(driver.findElements(By.className("error")).size()).isEqualTo(errorCount);
	}

	@Then("Dostaniemy bład = (.*)")
	public void shouldContainsErrorMessage(String error) throws Exception {
		driver.getPageSource().contains(error);
	}

	@Test
	public void test() {

	}

}
