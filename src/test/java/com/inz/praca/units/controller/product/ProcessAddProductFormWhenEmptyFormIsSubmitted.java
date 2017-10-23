package com.inz.praca.units.controller.product;

import com.inz.praca.WebTestConstants;
import com.inz.praca.WebTestConstants.ModelAttributeName;
import com.inz.praca.WebTestConstants.ModelAttributeProperty;
import com.inz.praca.WebTestConstants.ModelAttributeProperty.PRODUCT;
import com.inz.praca.WebTestConstants.ValidationErrorCode;
import com.inz.praca.WebTestConstants.View;
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
        this.mockMvc.perform(post("/addProduct")
                .param(PRODUCT.NAME, "")
                .param(PRODUCT.PRICE, "")
                .param(PRODUCT.CATEGORY, ""))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRenderCreateProductView() throws Exception {
        this.mockMvc.perform(post("/addProduct")
                .param(PRODUCT.NAME, "")
                .param(PRODUCT.PRICE, "")
                .param(PRODUCT.CATEGORY, ""))
                .andExpect(view().name(View.ADD_PRODUCT));
    }

    @Test
    public void shouldShowValidationErrorForEmptyName() throws Exception {
        this.mockMvc.perform(post("/addProduct")
                .param(PRODUCT.NAME, "")
                .param(PRODUCT.PRICE, "")
                .param(PRODUCT.CATEGORY, ""))
                .andExpect(model().attributeHasFieldErrorCode(ModelAttributeName.PRODUCT_CREATE_FORM,
                        PRODUCT.NAME, is(ValidationErrorCode.SIZE)
                ));
    }

    @Test
    public void shouldShowValidationErrorForEmptyCategory() throws Exception {
        this.mockMvc.perform(post("/addProduct")
                .param(PRODUCT.NAME, "")
                .param(PRODUCT.PRICE, "")
                .param(PRODUCT.CATEGORY, ""))
                .andExpect(model().attributeHasFieldErrorCode(ModelAttributeName.PRODUCT_CREATE_FORM,
                        PRODUCT.CATEGORY, is(ValidationErrorCode.EMPTY_FIELD)
                ));
    }

    @Test
    public void shouldNotModifyIdField() throws Exception {
        this.mockMvc.perform(post("/addProduct")
                .param(PRODUCT.NAME, "")
                .param(PRODUCT.PRICE, "")
                .param(PRODUCT.CATEGORY, ""))
                .andExpect(model().attribute(ModelAttributeName.PRODUCT_CREATE_FORM,
                        hasProperty(PRODUCT.ID, Matchers.nullValue())
                ));
    }

    @Test
    public void shouldNotCreateNewProduct() throws Exception {
        this.mockMvc.perform(post("/addProduct")
                .param(PRODUCT.NAME, "")
                .param(PRODUCT.PRICE, "")
                .param(PRODUCT.CATEGORY, ""));

        verify(this.productService, never()).createProductFromDTO(isA(ProductDTO.class));
    }

    @Test
    public void shouldShowEmptyForm() throws Exception {
        this.mockMvc.perform(post("/addProduct")
                .param(PRODUCT.NAME, "")
                .param(PRODUCT.PRICE, "")
                .param(PRODUCT.CATEGORY, ""))
                .andExpect(model().attribute(ModelAttributeName.PRODUCT_CREATE_FORM, allOf(
                        hasProperty(PRODUCT.NAME, is("")),
                        hasProperty(PRODUCT.PRICE, nullValue()),
                        hasProperty(PRODUCT.CATEGORY, is("")),
                        hasProperty(PRODUCT.IMAGE_URL, nullValue()),
                        hasProperty(PRODUCT.ID, nullValue()),
                        hasProperty(PRODUCT.DESCRIPTION, nullValue())
                )));
    }
}
