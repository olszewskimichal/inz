package com.inz.praca.selenium.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

@Slf4j
public class BrowserConfig {

    private static FirefoxBinary getFirefoxBinaryForTravisCi() throws IOException {
        String firefoxPath = getFirefoxPath();
        log.info("Firefox path: " + firefoxPath);

        return new FirefoxBinary(new File(firefoxPath));
    }

    private static String getFirefoxPath() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("which", "firefox");
        pb.redirectErrorStream(true);
        Process process = pb.start();
        try (InputStreamReader isr = new InputStreamReader(process.getInputStream(),
                "UTF-8"); BufferedReader br = new BufferedReader(isr)) {
            return br.readLine();
        }
    }

    public WebDriver htmlUnitDriver() {
        return new HtmlUnitDriver(true);
    }

    public WebDriver firefox() throws IOException {
        System.setProperty("webdriver.gecko.driver",
                "C:\\Users\\Admin\\Downloads\\geckodriver-v0.11.1-win64\\geckodriver.exe");
        String travisCiFlag = System.getenv().get("TRAVIS");
        if (!"true".equals(travisCiFlag)) {
            return htmlUnitDriver();
        }
        FirefoxBinary firefoxBinary = "true".equals(travisCiFlag) ? getFirefoxBinaryForTravisCi() : new FirefoxBinary();
        return new FirefoxDriver(firefoxBinary, new FirefoxProfile());
    }
}
