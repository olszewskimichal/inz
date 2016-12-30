package com.inz.praca.selenium.configuration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotTestRule implements MethodRule {
    private WebDriver driver;

    public ScreenshotTestRule(WebDriver driver) {
        this.driver = driver;
    }

    public Statement apply(final Statement statement, final FrameworkMethod frameworkMethod, final Object o) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    statement.evaluate();
                } catch (Throwable t) {
                    captureScreenShot(frameworkMethod.getName());

                    throw t;
                }
            }

            public void captureScreenShot(String fileName) throws IOException {
                new File("target/seleniumTest-reports/").mkdirs();
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                fileName += UUID.randomUUID().toString();
                File targetFile = new File("target/seleniumTest-reports/screenshot-" + fileName + ".png");
                FileUtils.copyFile(scrFile, targetFile);
            }
        };
    }
}
