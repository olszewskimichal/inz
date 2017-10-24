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

    private void useNewCategoryData(String name, String description) throws Exception {
        prepareBeforeTest();
        SeleniumTestBase.driver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPage(SeleniumTestBase.driver);
        loginPage.logInToApp("admin@email.pl", "zaq1@WSX");

        this.name = name;
        this.description = description;
    }

    private void whenClickAddCategory() {
        SeleniumTestBase.driver.get("http://localhost:" + port + "/addCategory");
        NewCategoryPage newCategoryPage = new NewCategoryPage(SeleniumTestBase.driver);
        newCategoryPage.typeName(name);
        newCategoryPage.typeDesctiption(description);
        newCategoryPage.clickOnCreateCategoryButton();
    }

    private void shouldGetResponseWithErrorCount(int errorCount) {
        assertThat(SeleniumTestBase.driver.findElements(By.className("error")).size()).isEqualTo(errorCount);
    }

    private void shouldContainsErrorMessage(String error) {
        SeleniumTestBase.driver.getPageSource().contains(error);
    }

    @Test
    public void shouldAddNewCategory() throws Exception {
        useNewCategoryData("nazwa1","descpription");
        whenClickAddCategory();
        shouldGetResponseWithErrorCount(0);
    }

    @Test
    public void shouldReturnErrorWhenNameIsTooShort() throws Exception {
        useNewCategoryData("na","descpriptiopn");
        whenClickAddCategory();
        shouldContainsErrorMessage("Podana nazwa jest zbyt krótka");
    }

    @Test
    public void shouldReturnErrorWhenDescriptionIsTooShort() throws Exception {
        useNewCategoryData("nazwa","des");
        whenClickAddCategory();
        shouldContainsErrorMessage("Podany opis jest za krótki");
    }

}
