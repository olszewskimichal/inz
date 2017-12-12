package com.inz.praca.units.controller.product;

import static com.inz.praca.WebTestConstants.ModelAttributeName.PAGER;
import static com.inz.praca.WebTestConstants.ModelAttributeName.PRODUCT_LIST;
import static com.inz.praca.WebTestConstants.ModelAttributeName.SELECTED_PAGE_SIZE;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.ArrayList;
import java.util.Optional;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.PageImpl;

public class ShowProductListWhenProductsIsNotFound extends ProductControllerTestBase {

  @Before
  public void returnEmptyProductsList() {
    given(productService.getProducts(1, 6, null, Optional.empty())).willReturn(new PageImpl<>(new ArrayList<>()));
  }

  @Test
  public void shouldShowEmptyProductsList() throws Exception {
    mockMvc.perform(get("/products"))
        .andDo(print())
        .andExpect(model().attribute(PRODUCT_LIST, hasSize(0)))
        .andExpect(model().attribute(SELECTED_PAGE_SIZE, Matchers.equalTo(6)))
        .andExpect(model().attribute(PAGER, Matchers.notNullValue()));
  }
}
