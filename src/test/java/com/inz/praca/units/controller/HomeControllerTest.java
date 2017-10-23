package com.inz.praca.units.controller;

import com.inz.praca.UnitTest;
import com.inz.praca.WebTestConstants;
import com.inz.praca.integration.WebTestConfig;
import com.inz.praca.login.HomeController;
import com.inz.praca.products.ProductController;
import com.inz.praca.products.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.inz.praca.integration.WebTestConfig.exceptionResolver;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Category(UnitTest.class)
public class HomeControllerTest {

    MockMvc mockMvc;

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
                .andExpect(view().name(WebTestConstants.View.HOME));
    }
}
