package com.inz.praca.integration.cucumber;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import com.inz.praca.integration.IntegrationTestBase;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Test;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class CreateCategorySteps extends IntegrationTestBase {
	private String name;
	private String description;

	ResultActions perform;

	@Given("Mając  nazwe kategori  (.*) z opisem  (.*)")
	public void useNewProductData(String name, String description) {
		mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		this.name = name;
		this.description = description;
	}

	@When("Po kliknieciu dodaj kategorie")
	public void shouldPerformNewCategory() throws Exception {
		perform = mvc.perform(post("/addCategory")
				.param("name", name)
				.param("description", description));
	}

	@Then("Otrzymamy (.*) komunikatów błedu, w przypadku 0 błedów - zostanie stworzona nowa kategoria")
	public void shouldGetResponseWithErrorCount(int errorCount) throws Exception {
		perform.andExpect(model().errorCount(errorCount));
	}

	@Then("Dostaniemy bład = (.*)")
	public void shouldContainsErrorMessage(String error) throws Exception {
		perform.andExpect(content().string(
				allOf(
						containsString(error)
				))).andDo(print());
	}

	@Test
	public void test() {

	}

}
