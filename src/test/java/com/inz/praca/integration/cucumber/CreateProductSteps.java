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

public class CreateProductSteps extends IntegrationTestBase {
	private String name;
	private String description;
	private String price;
	private String category;

	ResultActions perform;

	@Given("Podajac  nazwe= (.*) z opisem = (.*) cena = (.*) oraz wybrana kategoria (.*)")
	public void useNewProductData(String name, String description, String price, String category) {
		mvc= MockMvcBuilders.webAppContextSetup(this.wac).build();
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
	}

	@When("Przy kliknieciu dodaj")
	public void shouldPerformNewProduct() throws Exception {
		perform = mvc.perform(post("/addProduct")
				.param("name", name)
				.param("description", description)
				.param("price", price)
				.param("category", category));
	}

	@Then("Otrzymamy (.*) komunikatów błedu, w przypadku 0 błedów - zostanie stworzony nowy produkt")
	public void shouldGetResponseWithErrorCount(int errorCount) throws Exception {
		perform.andExpect(model().errorCount(errorCount));
	}

	@Then("Otrzymamy komunikat (.*)")
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
