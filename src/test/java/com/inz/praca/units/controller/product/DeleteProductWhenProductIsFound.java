package com.inz.praca.units.controller.product;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.inz.praca.WebTestConstants.RedirectView;
import org.junit.Test;

public class DeleteProductWhenProductIsFound extends ProductControllerTestBase {

  @Test
  public void shouldReturnHttpResponseStatusFound() throws Exception {
    mockMvc.perform(get("/products/product/delete/{productId}", ProductControllerTestBase.PRODUCT_ID))
        .andExpect(status().isFound());
  }

  @Test
  public void shouldRedirectProductToViewTaskListView() throws Exception {
    mockMvc.perform(get("/products/product/delete/{productId}", ProductControllerTestBase.PRODUCT_ID))
        .andExpect(view().name(RedirectView.PRODUCTS));
  }

  @Test
  public void shouldDeleteTaskWithCorrectId() throws Exception {
    mockMvc.perform(get("/products/product/delete/{productId}", ProductControllerTestBase.PRODUCT_ID));

    verify(productService, times(1)).deleteProductById(ProductControllerTestBase.PRODUCT_ID);
  }
}
