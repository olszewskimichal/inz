package com.inz.praca.integration.cucumber;

import com.inz.praca.IntegrationTest;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources", plugin = {"pretty", "html:target/cucumber"})
@Category(IntegrationTest.class)
public class CucumberTests {

}
