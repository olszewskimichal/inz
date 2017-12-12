package com.inz.praca.units.controller.product;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.inz.praca.WebTestConstants.View;
import org.junit.Test;

public class ShowProductList extends ProductControllerTestBase {

  @Test
  public void shouldReturnHttpStatusCodeOk() throws Exception {
    mockMvc.perform(get("/products"))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldRenderProductsListView() throws Exception {
    mockMvc.perform(get("/products"))
        .andExpect(view().name(View.PRODUCTS));
  }
}
