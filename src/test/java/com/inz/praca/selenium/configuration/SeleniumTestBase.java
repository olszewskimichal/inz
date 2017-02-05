package com.inz.praca.selenium.configuration;

import java.io.IOException;

import com.inz.praca.integration.IntegrationTestBase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;

public abstract class SeleniumTestBase extends IntegrationTestBase {
    public static BrowserConfig browserConfiguration = new BrowserConfig();

    @Rule
    public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

    public static WebDriver driver;


    @BeforeClass
    public static void setBrowser() throws IOException {
        driver = browserConfiguration.firefox();
        driver.manage().window().maximize();
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }


}
