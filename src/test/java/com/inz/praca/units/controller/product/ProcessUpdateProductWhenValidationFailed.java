package com.inz.praca.units.controller.product;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.inz.praca.WebTestConstants.ModelAttributeName;
import com.inz.praca.WebTestConstants.ModelAttributeProperty.PRODUCT;
import com.inz.praca.WebTestConstants.ValidationErrorCode;
import com.inz.praca.WebTestConstants.View;
import com.inz.praca.products.ProductDTO;
import org.hamcrest.Matchers;
import org.junit.Test;

public class ProcessUpdateProductWhenValidationFailed extends ProductControllerTestBase {

  @Test
  public void shouldReturnHttpStatusCodeOk() throws Exception {
    mockMvc.perform(post("/products/product/edit/{id}", ProductControllerTestBase.PRODUCT_ID)
        .param(PRODUCT.NAME, "")
        .param(PRODUCT.PRICE, "")
        .param(PRODUCT.CATEGORY, ""))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldRenderCreateProductView() throws Exception {
    mockMvc.perform(post("/products/product/edit/{id}", ProductControllerTestBase.PRODUCT_ID)
        .param(PRODUCT.NAME, "")
        .param(PRODUCT.PRICE, "")
        .param(PRODUCT.CATEGORY, ""))
        .andExpect(view().name(View.EDIT_PRODUCT));
  }

  @Test
  public void shouldShowValidationErrorForEmptyName() throws Exception {
    mockMvc.perform(post("/products/product/edit/{id}", ProductControllerTestBase.PRODUCT_ID)
        .param(PRODUCT.NAME, "")
        .param(PRODUCT.PRICE, "")
        .param(PRODUCT.CATEGORY, ""))
        .andExpect(model().attributeHasFieldErrorCode(ModelAttributeName.PRODUCT_CREATE_FORM,
            PRODUCT.NAME, is(ValidationErrorCode.SIZE)
        ));
  }

  @Test
  public void shouldShowValidationErrorForEmptyCategory() throws Exception {
    mockMvc.perform(post("/products/product/edit/{id}", ProductControllerTestBase.PRODUCT_ID)
        .param(PRODUCT.NAME, "")
        .param(PRODUCT.PRICE, "")
        .param(PRODUCT.CATEGORY, ""))
        .andExpect(model().attributeHasFieldErrorCode(ModelAttributeName.PRODUCT_CREATE_FORM,
            PRODUCT.CATEGORY, is(ValidationErrorCode.EMPTY_FIELD)
        ));
  }

  @Test
  public void shouldNotModifyIdField() throws Exception {
    mockMvc.perform(post("/products/product/edit/{id}", ProductControllerTestBase.PRODUCT_ID)
        .param(PRODUCT.NAME, "")
        .param(PRODUCT.PRICE, "")
        .param(PRODUCT.CATEGORY, ""))
        .andExpect(model().attribute(ModelAttributeName.PRODUCT_CREATE_FORM,
            hasProperty(PRODUCT.ID, Matchers.nullValue())
        ));
  }

  @Test
  public void shouldNotUpdateProduct() throws Exception {
    mockMvc.perform(post("/products/product/edit/{id}", ProductControllerTestBase.PRODUCT_ID)
        .param(PRODUCT.NAME, "")
        .param(PRODUCT.PRICE, "")
        .param(PRODUCT.CATEGORY, ""));

    verify(productService, never()).createProductFromDTO(isA(ProductDTO.class));
  }

}
