package com.inz.praca.units.controller.product;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;
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
import com.inz.praca.units.TestStringUtil;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class ProcessAddProductWhenFormIsSubmittedWithNullPrice extends ProductControllerTestBase {

  @Before
  public void createFields() {
    name = TestStringUtil.createStringWithLength(ProductControllerTestBase.MIN_LENGTH_NAME);
    category = "others";
  }

  @Test
  public void shouldReturnHttpStatusCodeOk() throws Exception {
    mockMvc.perform(post("/addProduct")
        .param(PRODUCT.NAME, name)
        .param(PRODUCT.PRICE, "")
        .param(PRODUCT.CATEGORY, category))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldRenderCreateProductView() throws Exception {
    mockMvc.perform(post("/addProduct")
        .param(PRODUCT.NAME, name)
        .param(PRODUCT.PRICE, "")
        .param(PRODUCT.CATEGORY, category))
        .andExpect(view().name(View.ADD_PRODUCT));
  }

  @Test
  public void shouldShowValidationErrorForNullPrice() throws Exception {
    mockMvc.perform(post("/addProduct")
        .param(PRODUCT.NAME, name)
        .param(PRODUCT.PRICE, "")
        .param(PRODUCT.CATEGORY, category))
        .andExpect(model().attributeHasFieldErrorCode(ModelAttributeName.PRODUCT_CREATE_FORM,
            PRODUCT.PRICE, is(ValidationErrorCode.VALID_PRICE)
        ));
  }

  @Test
  public void shouldNotModifyIdField() throws Exception {
    mockMvc.perform(post("/addProduct")
        .param(PRODUCT.NAME, name)
        .param(PRODUCT.PRICE, "")
        .param(PRODUCT.CATEGORY, category))
        .andExpect(model().attribute(ModelAttributeName.PRODUCT_CREATE_FORM,
            hasProperty(PRODUCT.ID, Matchers.nullValue())
        ));
  }

  @Test
  public void shouldShowEnteredValues() throws Exception {
    mockMvc.perform(post("/addProduct")
        .param(PRODUCT.NAME, name)
        .param(PRODUCT.PRICE, "")
        .param(PRODUCT.CATEGORY, category))
        .andExpect(model().attribute(ModelAttributeName.PRODUCT_CREATE_FORM, allOf(
            hasProperty(PRODUCT.NAME, is(name)),
            hasProperty(PRODUCT.PRICE, is(price)),
            hasProperty(PRODUCT.CATEGORY, is(category)),
            hasProperty(PRODUCT.IMAGE_URL, nullValue()),
            hasProperty(PRODUCT.ID, nullValue()),
            hasProperty(PRODUCT.DESCRIPTION, nullValue())
        )));
  }

  @Test
  public void shouldNotCreateNewProduct() throws Exception {
    mockMvc.perform(post("/addProduct")
        .param(PRODUCT.NAME, name)
        .param(PRODUCT.PRICE, "")
        .param(PRODUCT.CATEGORY, category));

    verify(productService, never()).createProductFromDTO(isA(ProductDTO.class));
  }
}
