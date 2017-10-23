package com.inz.praca.integration.cucumber;

import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.LoginPage;
import com.inz.praca.selenium.pageObjects.NewCategoryPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class CreateCategorySteps extends SeleniumTestBase {
    private String name;
    private String description;

    @Given("Mając nazwe kategorii (.*) z opisem (.*)")
    public void useNewProductData(String name, String description) throws Exception {
        prepareBeforeTest();
        SeleniumTestBase.driver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPage(SeleniumTestBase.driver);
        loginPage.logInToApp("admin@email.pl", "zaq1@WSX");

        this.name = name;
        this.description = description;
    }

    @When("Po kliknieciu dodaj kategorie")
    public void shouldPerformNewCategory() {
        SeleniumTestBase.driver.get("http://localhost:" + port + "/addCategory");
        NewCategoryPage newCategoryPage = new NewCategoryPage(SeleniumTestBase.driver);
        newCategoryPage.typeName(name);
        newCategoryPage.typeDesctiption(description);
        newCategoryPage.clickOnCreateCategoryButton();
    }

    @Then("Otrzymamy (.*) komunikatów błedu, w przypadku 0 błedów - zostanie stworzona nowa kategoria")
    public void shouldGetResponseWithErrorCount(int errorCount) {
        assertThat(SeleniumTestBase.driver.findElements(By.className("error")).size()).isEqualTo(errorCount);
    }

    @Then("Dostaniemy bład = (.*)")
    public void shouldContainsErrorMessage(String error) {
        SeleniumTestBase.driver.getPageSource().contains(error);
    }

    @Test
    public void test() {

    }

}
