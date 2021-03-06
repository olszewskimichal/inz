package com.inz.praca.units.controller;

import static com.inz.praca.integration.WebTestConfig.exceptionResolver;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.inz.praca.UnitTest;
import com.inz.praca.WebTestConstants.View;
import com.inz.praca.integration.WebTestConfig;
import com.inz.praca.login.HomeController;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@Category(UnitTest.class)
public class HomeControllerTest {

  private MockMvc mockMvc;

  @Before
  public void configureSystemUnderTest() {
    mockMvc = MockMvcBuilders.standaloneSetup(new HomeController())
        .setViewResolvers(WebTestConfig.viewResolver())
        .setHandlerExceptionResolvers(exceptionResolver())
        .build();
  }

  @Test
  public void shouldReturnHttpStatusCodeOk() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldRenderHomePageView() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(view().name(View.HOME));
  }
}
