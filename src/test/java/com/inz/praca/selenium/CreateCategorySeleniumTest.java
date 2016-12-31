package com.inz.praca.selenium;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import com.inz.praca.selenium.configuration.ScreenshotTestRule;
import com.inz.praca.selenium.configuration.SeleniumTestBase;
import com.inz.praca.selenium.pageObjects.NewCategoryPage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class CreateCategorySeleniumTest extends SeleniumTestBase {

	public static WebDriver driver;

	@Rule
	public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

	@BeforeClass
	public static void setBrowser() throws IOException {
		driver = browserConfiguration.firefox();
		System.out.println(driver);
		driver.manage().window().maximize();
	}

	@AfterClass
	public static void tearDown() {
		driver.quit();
	}

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
