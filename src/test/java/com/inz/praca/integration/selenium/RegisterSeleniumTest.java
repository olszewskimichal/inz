package com.inz.praca.integration.selenium;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import com.inz.praca.integration.selenium.configuration.ScreenshotTestRule;
import com.inz.praca.integration.selenium.configuration.SeleniumTestBase;
import com.inz.praca.integration.selenium.pageObjects.RegisterPage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;


public class RegisterSeleniumTest extends SeleniumTestBase {

	public static WebDriver driver;

	@Rule
	public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

	@BeforeClass
	public static void setBrowser() throws IOException {
		driver = browserConfiguration.firefox();
		driver.manage().window().maximize();
	}

	@AfterClass
	public static void tearDown() {
		driver.quit();
	}

	@Test
	public void shouldRegisterWithCorrectData() {
		driver.get("http://localhost:" + port + "/register");
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.typeName("userTest");
		registerPage.typeLastName("userTest");
		registerPage.typeEmail("userTestXXX@poczta.pl");
		registerPage.typePassword("zaq1@WSX");
		registerPage.typeConfirmPassword("zaq1@WSX");
		registerPage.clickOnRegisterButton();
		assertThat(driver.getPageSource()).contains("Witamy w Naszym sklepie");
		assertThat(driver.getTitle()).isEqualTo("Strona główna");
	}
}
