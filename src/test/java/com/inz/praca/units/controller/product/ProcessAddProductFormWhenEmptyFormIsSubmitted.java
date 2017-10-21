package com.inz.praca.units.controller.product;

import com.inz.praca.WebTestConstants;
import com.inz.praca.products.ProductDTO;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProcessAddProductFormWhenEmptyFormIsSubmitted extends ProductControllerTestBase {
    @Test
    public void shouldReturnHttpStatusCodeOk() throws Exception {
        mockMvc.perform(post("/addProduct")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRenderCreateProductView() throws Exception {
        mockMvc.perform(post("/addProduct")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                .andExpect(view().name(WebTestConstants.View.ADD_PRODUCT));
    }

    @Test
    public void shouldShowValidationErrorForEmptyName() throws Exception {
        mockMvc.perform(post("/addProduct")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                .andExpect(model().attributeHasFieldErrorCode(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                        WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, is(WebTestConstants.ValidationErrorCode.SIZE)
                ));
    }

    @Test
    public void shouldShowValidationErrorForEmptyCategory() throws Exception {
        mockMvc.perform(post("/addProduct")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                .andExpect(model().attributeHasFieldErrorCode(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                        WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, is(WebTestConstants.ValidationErrorCode.EMPTY_FIELD)
                ));
    }

    @Test
    public void shouldNotModifyIdField() throws Exception {
        mockMvc.perform(post("/addProduct")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.ID, Matchers.nullValue())
                ));
    }

    @Test
    public void shouldNotCreateNewProduct() throws Exception {
        mockMvc.perform(post("/addProduct")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""));

        verify(productService, never()).createProductFromDTO(isA(ProductDTO.class));
    }

    @Test
    public void shouldShowEmptyForm() throws Exception {
        mockMvc.perform(post("/addProduct")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM, allOf(
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, is("")),
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, nullValue()),
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, is("")),
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.IMAGE_URL, nullValue()),
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.ID, nullValue()),
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.DESCRIPTION, nullValue())
                )));
    }
}
