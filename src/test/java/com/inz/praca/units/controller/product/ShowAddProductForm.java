package com.inz.praca.units.controller.product;

import com.inz.praca.WebTestConstants;
import com.inz.praca.WebTestConstants.ModelAttributeName;
import com.inz.praca.WebTestConstants.ModelAttributeProperty;
import com.inz.praca.WebTestConstants.ModelAttributeProperty.PRODUCT;
import com.inz.praca.WebTestConstants.View;
import org.junit.Test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.IsNull.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ShowAddProductForm extends ProductControllerTestBase {

    @Test
    public void shouldReturnHttpStatusCodeOk() throws Exception {
        this.mockMvc.perform(get("/addProduct"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRenderAddProductView() throws Exception {
        this.mockMvc.perform(get("/addProduct"))
                .andExpect(view().name(View.ADD_PRODUCT));
    }

    @Test
    public void shouldCreateAnEmptyFormObject() throws Exception {
        this.mockMvc.perform(get("/addProduct"))
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
