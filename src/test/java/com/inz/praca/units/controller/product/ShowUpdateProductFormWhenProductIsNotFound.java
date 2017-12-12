package com.inz.praca.units.controller.product;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.inz.praca.WebTestConstants.ErrorView;
import com.inz.praca.products.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;

public class ShowUpdateProductFormWhenProductIsNotFound extends ProductControllerTestBase {

  @Before
  public void throwProductNotFoundException() {
    given(productService.getProductDTOById(ProductControllerTestBase.PRODUCT_ID)).willThrow(new ProductNotFoundException(ProductControllerTestBase.PRODUCT_ID));
  }


  @Test
  public void shouldReturnHttpStatusCodeNotFound() throws Exception {
    mockMvc.perform(get("/products/product/edit/{id}", ProductControllerTestBase.PRODUCT_ID))
        .andExpect(status().isNotFound());
  }

  @Test
  public void shouldRenderNotFoundView() throws Exception {
    mockMvc.perform(get("/products/product/edit/{id}", ProductControllerTestBase.PRODUCT_ID))
        .andExpect(view().name(ErrorView.NOT_FOUND));
  }

}
