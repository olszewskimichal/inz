package com.inz.praca.units.controller;

import static com.inz.praca.integration.WebTestConfig.exceptionResolver;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.inz.praca.UnitTest;
import com.inz.praca.WebTestConstants.View;
import com.inz.praca.integration.WebTestConfig;
import com.inz.praca.login.LoginController;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@Category(UnitTest.class)
public class LoginControllerTest {

  private MockMvc mockMvc;

  @Before
  public void configureSystemUnderTest() {
    mockMvc = MockMvcBuilders.standaloneSetup(new LoginController())
        .setViewResolvers(WebTestConfig.viewResolver())
        .setHandlerExceptionResolvers(exceptionResolver())
        .build();
  }

  @Test
  public void shouldReturnHttpStatusCodeOkOnLoginPage() throws Exception {
    mockMvc.perform(get("/login"))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldRenderLoginPageView() throws Exception {
    mockMvc.perform(get("/login"))
        .andExpect(view().name(View.LOGIN));
  }

  @Test
  public void shouldReturnHttpStatusCodeOkOnLoginErrorPage() throws Exception {
    mockMvc.perform(get("/login-error"))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldRenderLoginErrorPageView() throws Exception {
    mockMvc.perform(get("/login-error"))
        .andExpect(view().name(View.LOGIN));
  }

  @Test
  public void shouldFillModelPropertiesOnLoginError() throws Exception {
    mockMvc.perform(get("/login-error"))
        .andExpect(model().attribute("loginError", true))
        .andExpect(model().attribute("errorMessage", nullValue()));
  }

}
