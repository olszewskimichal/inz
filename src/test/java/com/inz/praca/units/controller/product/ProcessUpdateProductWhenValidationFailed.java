package com.inz.praca.units.controller.product;

import com.inz.praca.WebTestConstants;
import com.inz.praca.products.ProductDTO;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProcessUpdateProductWhenValidationFailed extends ProductControllerTestBase{

    @Test
    public void shouldReturnHttpStatusCodeOk() throws Exception {
        mockMvc.perform(post("/products/product/edit/{id}", PRODUCT_ID)
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRenderCreateProductView() throws Exception {
        mockMvc.perform(post("/products/product/edit/{id}", PRODUCT_ID)
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                .andExpect(view().name(WebTestConstants.View.EDIT_PRODUCT));
    }

    @Test
    public void shouldShowValidationErrorForEmptyName() throws Exception {
        mockMvc.perform(post("/products/product/edit/{id}", PRODUCT_ID)
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                .andExpect(model().attributeHasFieldErrorCode(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                        WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, is(WebTestConstants.ValidationErrorCode.SIZE)
                ));
    }

    @Test
    public void shouldShowValidationErrorForEmptyCategory() throws Exception {
        mockMvc.perform(post("/products/product/edit/{id}", PRODUCT_ID)
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                .andExpect(model().attributeHasFieldErrorCode(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                        WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, is(WebTestConstants.ValidationErrorCode.EMPTY_FIELD)
                ));
    }

    @Test
    public void shouldNotModifyIdField() throws Exception {
        mockMvc.perform(post("/products/product/edit/{id}", PRODUCT_ID)
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.ID, Matchers.nullValue())
                ));
    }

    @Test
    public void shouldNotUpdateProduct() throws Exception {
        mockMvc.perform(post("/products/product/edit/{id}", PRODUCT_ID)
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""));

        verify(productService, never()).createProductFromDTO(isA(ProductDTO.class));
    }

}
