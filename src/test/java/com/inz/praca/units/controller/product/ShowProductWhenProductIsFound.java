package com.inz.praca.units.controller.product;

import com.inz.praca.WebTestConstants;
import com.inz.praca.products.ProductBuilder;
import com.inz.praca.products.ProductDTO;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ShowProductWhenProductIsFound extends ProductControllerTestBase {
    @Before
    public void returnProduct() {
        ProductDTO productDTO = new ProductBuilder().withName(PRODUCT_NAME).withPrice(PRICE).withCategory(CATEGORY).withDescription(PRODUCT_DESC).withUrl(IMAGE_URL).createProductDTO();
        given(productService.getProductDTOById(PRODUCT_ID)).willReturn(productDTO);
    }

    @Test
    public void shouldReturnHttpStatusCodeOk() throws Exception {
        mockMvc.perform(get("/products/product/{productId}", PRODUCT_ID))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRenderNotFoundView() throws Exception {
        mockMvc.perform(get("/products/product/{productId}", PRODUCT_ID))
                .andExpect(view().name(WebTestConstants.View.PRODUCT));
    }

    @Test
    public void shouldShowFoundProduct() throws Exception {
        mockMvc.perform(get("/products/product/{productId}", PRODUCT_ID))
                .andDo(print())
                .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_DTO, allOf(
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, is(PRODUCT_NAME)),
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.DESCRIPTION, is(PRODUCT_DESC)),
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, is(PRICE)),
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, is(CATEGORY)),
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.IMAGE_URL, is(IMAGE_URL)),
                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.ID, nullValue())
                )));
    }
}
