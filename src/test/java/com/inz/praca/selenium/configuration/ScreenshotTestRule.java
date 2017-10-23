package com.inz.praca.selenium.configuration;

import org.apache.commons.io.FileUtils;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

class ScreenshotTestRule implements MethodRule {
    private final WebDriver driver;

    public ScreenshotTestRule(WebDriver driver) {
        this.driver = driver;
    }

    public Statement apply(Statement statement, FrameworkMethod frameworkMethod, Object o) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    statement.evaluate();
                } catch (Throwable t) {
                    this.captureScreenShot(frameworkMethod.getName());

                    throw t;
                }
            }

            void captureScreenShot(String fileName) throws IOException {
                new File("target/seleniumTest-reports/").mkdirs();
                File scrFile = ((TakesScreenshot) ScreenshotTestRule.this.driver).getScreenshotAs(OutputType.FILE);
                fileName += UUID.randomUUID().toString();
                File targetFile = new File("target/seleniumTest-reports/screenshot-" + fileName + ".png");
                FileUtils.copyFile(scrFile, targetFile);
            }
        };
    }
}
