package com.inz.praca.units.controller.product;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.IsNull.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.inz.praca.WebTestConstants.ModelAttributeName;
import com.inz.praca.WebTestConstants.ModelAttributeProperty.PRODUCT;
import com.inz.praca.WebTestConstants.View;
import org.junit.Test;

public class ShowAddProductForm extends ProductControllerTestBase {

  @Test
  public void shouldReturnHttpStatusCodeOk() throws Exception {
    mockMvc.perform(get("/addProduct"))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldRenderAddProductView() throws Exception {
    mockMvc.perform(get("/addProduct"))
        .andExpect(view().name(View.ADD_PRODUCT));
  }

  @Test
  public void shouldCreateAnEmptyFormObject() throws Exception {
    mockMvc.perform(get("/addProduct"))
        .andExpect(model().attribute(ModelAttributeName.PRODUCT_CREATE_FORM, allOf(
            hasProperty(PRODUCT.NAME, nullValue()),
            hasProperty(PRODUCT.PRICE, nullValue()),
            hasProperty(PRODUCT.CATEGORY, nullValue()),
            hasProperty(PRODUCT.IMAGE_URL, nullValue()),
            hasProperty(PRODUCT.ID, nullValue()),
            hasProperty(PRODUCT.DESCRIPTION, nullValue())
        )));
  }
}
