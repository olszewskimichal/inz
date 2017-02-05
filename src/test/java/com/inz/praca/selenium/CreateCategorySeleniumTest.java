package com.inz.praca.selenium;

import static org.assertj.core.api.Assertions.assertThat;

import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.NewCategoryPage;
import org.junit.Test;

public class CreateCategorySeleniumTest extends SeleniumTestBase {

	@Test
	public void shouldCreateNewCategoryWithCorrectData() {
		driver.get("http://localhost:" + port + "/addCategory");
		NewCategoryPage categoryPage = new NewCategoryPage(driver);
		categoryPage.typeName("test");
		categoryPage.typeDesctiption("testy opis");
		categoryPage.clickOnCreateCategoryButton();
		assertThat(driver.getPageSource()).contains("Witamy w Naszym sklepie");
		assertThat(driver.getTitle()).isEqualTo("Strona główna");
	}
}
