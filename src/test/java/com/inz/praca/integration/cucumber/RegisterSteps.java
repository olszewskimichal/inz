package com.inz.praca.integration.cucumber;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import com.inz.praca.builders.UserBuilder;
import com.inz.praca.integration.IntegrationTestBase;
import com.inz.praca.repository.UserRepository;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class RegisterSteps extends IntegrationTestBase {
	private String name;
	private String lastName;
	private String email;
	private String password;
	private String confirmPassword;

	ResultActions perform;

	@Autowired
	private UserRepository userRepository;


	@Given("Podajac imie= (.*) nazwisko = (.*) email = (.*) oraz hasło (.*) i potwierdzeniu (.*)")
	public void useRegisterData(String name, String lastName, String email, String password, String confirmPassword) {
		userRepository.deleteAll();
		userRepository.save(new UserBuilder().withEmail("istniejacyemail@o2.pl").withPasswordHash("zaq1@WSX").build());
		mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	@When("Przy kliknieciu zarejestruj")
	public void shouldPerformRegister() throws Exception {
		perform = mvc.perform(post("/register")
				.param("name", name)
				.param("lastName", lastName)
				.param("email", email)
				.param("password", password)
				.param("confirmPassword", confirmPassword));
	}

	@Then("Dostane (.*) komunikatów błedu")
	public void shouldGetResponseWithHttpStatusCode(int errorCount) throws Exception {
		perform.andExpect(model().errorCount(errorCount));
	}

	@Then("Otrzymamy bład że (.*)")
	public void shouldGetErrorWithExistingEmail(String error) throws Exception {
		perform.andExpect(content().string(
				allOf(
						containsString(error)
				))).andDo(print());
	}

	@Test
	public void test() {

	}
}
