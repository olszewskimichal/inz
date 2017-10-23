package com.inz.praca.units.controller.product;

import com.inz.praca.WebTestConstants;
import com.inz.praca.WebTestConstants.ModelAttributeName;
import com.inz.praca.WebTestConstants.ModelAttributeProperty;
import com.inz.praca.WebTestConstants.ModelAttributeProperty.PRODUCT;
import com.inz.praca.WebTestConstants.ValidationErrorCode;
import com.inz.praca.WebTestConstants.View;
import com.inz.praca.products.ProductDTO;
import com.inz.praca.units.TestStringUtil;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProcessAddProductWhenFormIsSubmittedWithTooShortName extends ProductControllerTestBase {
    private String name;
    private BigDecimal price;
    private String category;

    @Before
    public void createFields() {
        this.name = TestStringUtil.createStringWithLength(ProductControllerTestBase.MIN_LENGTH_NAME - 1);
        this.price = BigDecimal.TEN;
        this.category = "others";
    }

    @Test
    public void shouldReturnHttpStatusCodeOk() throws Exception {
        this.mockMvc.perform(post("/addProduct")
                .param(PRODUCT.NAME, this.name)
                .param(PRODUCT.PRICE, this.price.toString())
                .param(PRODUCT.CATEGORY, this.category))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRenderCreateProductView() throws Exception {
        this.mockMvc.perform(post("/addProduct")
                .param(PRODUCT.NAME, this.name)
                .param(PRODUCT.PRICE, this.price.toString())
                .param(PRODUCT.CATEGORY, this.category))
                .andExpect(view().name(View.ADD_PRODUCT));
    }

    @Test
    public void shouldShowValidationErrorForToLongName() throws Exception {
        this.mockMvc.perform(post("/addProduct")
                .param(PRODUCT.NAME, this.name)
                .param(PRODUCT.PRICE, this.price.toString())
                .param(PRODUCT.CATEGORY, this.category))
                .andExpect(model().attributeHasFieldErrorCode(ModelAttributeName.PRODUCT_CREATE_FORM,
                        PRODUCT.NAME, is(ValidationErrorCode.SIZE)
                ));
    }

    @Test
    public void shouldNotModifyIdField() throws Exception {
        this.mockMvc.perform(post("/addProduct")
                .param(PRODUCT.NAME, this.name)
                .param(PRODUCT.PRICE, this.price.toString())
                .param(PRODUCT.CATEGORY, this.category))
                .andExpect(model().attribute(ModelAttributeName.PRODUCT_CREATE_FORM,
                        hasProperty(PRODUCT.ID, Matchers.nullValue())
                ));
    }

    @Test
    public void shouldShowEnteredValues() throws Exception {
        this.mockMvc.perform(post("/addProduct")
                .param(PRODUCT.NAME, this.name)
                .param(PRODUCT.PRICE, this.price.toString())
                .param(PRODUCT.CATEGORY, this.category))
                .andExpect(model().attribute(ModelAttributeName.PRODUCT_CREATE_FORM, allOf(
                        hasProperty(PRODUCT.NAME, is(this.name)),
                        hasProperty(PRODUCT.PRICE, is(this.price)),
                        hasProperty(PRODUCT.CATEGORY, is(this.category)),
                        hasProperty(PRODUCT.IMAGE_URL, nullValue()),
                        hasProperty(PRODUCT.ID, nullValue()),
                        hasProperty(PRODUCT.DESCRIPTION, nullValue())
                )));
    }

    @Test
    public void shouldNotCreateNewProduct() throws Exception {
        this.mockMvc.perform(post("/addProduct")
                .param(PRODUCT.NAME, this.name)
                .param(PRODUCT.PRICE, this.price.toString())
                .param(PRODUCT.CATEGORY, this.category));

        verify(this.productService, never()).createProductFromDTO(isA(ProductDTO.class));
    }

}
