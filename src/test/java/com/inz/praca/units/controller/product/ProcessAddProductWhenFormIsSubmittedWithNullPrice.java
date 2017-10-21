package com.inz.praca.units.controller.product;

import com.inz.praca.WebTestConstants;
import com.inz.praca.products.ProductDTO;
import com.inz.praca.units.TestStringUtil;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProcessAddProductWhenFormIsSubmittedWithNullPrice extends ProductControllerTestBase {

    @Before
    public void createFields() {
        name = TestStringUtil.createStringWithLength(MIN_LENGTH_NAME);
        category = "others";
    }

    @Test
    public void shouldReturnHttpStatusCodeOk() throws Exception {
        mockMvc.perform(post("/addProduct")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRenderCreateProductView() throws Exception {
        mockMvc.perform(post("/addProduct")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                .andExpect(view().name(WebTestConstants.View.ADD_PRODUCT));
    }

    @Test
    public void shouldShowValidationErrorForNullPrice() throws Exception {
        mockMvc.perform(post("/addProduct")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                .andExpect(model().attributeHasFieldErrorCode(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                        WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, is(WebTestConstants.ValidationErrorCode.VALID_PRICE)
                ));
    }

    @Test
    public void shouldNotModifyIdField() throws Exception {
        mockMvc.perform(post("/addProduct")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.ID, Matchers.nullValue())
                ));
    }

    @Test
    public void shouldShowEnteredValues() throws Exception {
        mockMvc.perform(post("/addProduct")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM, allOf(
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, is(name)),
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, is(price)),
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, is(category)),
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.IMAGE_URL, nullValue()),
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.ID, nullValue()),
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.DESCRIPTION, nullValue())
                )));
    }

    @Test
    public void shouldNotCreateNewProduct() throws Exception {
        mockMvc.perform(post("/addProduct")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category));

        verify(productService, never()).createProductFromDTO(isA(ProductDTO.class));
    }
}
