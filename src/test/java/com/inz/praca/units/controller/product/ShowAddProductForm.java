package com.inz.praca.units.controller.product;

import com.inz.praca.WebTestConstants;
import org.junit.Test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.IsNull.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ShowAddProductForm extends ProductControllerTestBase{

    @Test
    public void shouldReturnHttpStatusCodeOk() throws Exception {
        mockMvc.perform(get("/addProduct"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRenderAddProductView() throws Exception {
        mockMvc.perform(get("/addProduct"))
                .andExpect(view().name(WebTestConstants.View.ADD_PRODUCT));
    }

    @Test
    public void shouldCreateAnEmptyFormObject() throws Exception {
        mockMvc.perform(get("/addProduct"))
                .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM, allOf(
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, nullValue()),
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, nullValue()),
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, nullValue()),
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.IMAGE_URL, nullValue()),
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.ID, nullValue()),
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.DESCRIPTION, nullValue())
                )));
    }
}
