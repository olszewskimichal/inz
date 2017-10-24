package com.inz.praca.integration;

import com.inz.praca.IntegrationTest;
import com.inz.praca.selenium.configuration.SeleniumTest;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("development")
@ContextConfiguration
@Category(IntegrationTest.class)
@SeleniumTest(driver = ChromeDriver.class)
public abstract class SeleniumProfileTestBase {

    @LocalServerPort
    public int port;

}
