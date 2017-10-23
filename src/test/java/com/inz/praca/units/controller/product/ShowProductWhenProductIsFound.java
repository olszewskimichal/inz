package com.inz.praca.units.controller.product;

import com.inz.praca.WebTestConstants;
import com.inz.praca.WebTestConstants.ModelAttributeName;
import com.inz.praca.WebTestConstants.ModelAttributeProperty;
import com.inz.praca.WebTestConstants.ModelAttributeProperty.PRODUCT;
import com.inz.praca.WebTestConstants.View;
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
        ProductDTO productDTO = new ProductBuilder().withName(PRODUCT_NAME).withPrice(ProductControllerTestBase.PRICE).withCategory(ProductControllerTestBase.CATEGORY).withDescription(ProductControllerTestBase.PRODUCT_DESC).withUrl(ProductControllerTestBase.IMAGE_URL).createProductDTO();
        given(productService.getProductDTOById(ProductControllerTestBase.PRODUCT_ID)).willReturn(productDTO);
    }

    @Test
    public void shouldReturnHttpStatusCodeOk() throws Exception {
        mockMvc.perform(get("/products/product/{productId}", ProductControllerTestBase.PRODUCT_ID))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRenderNotFoundView() throws Exception {
        mockMvc.perform(get("/products/product/{productId}", ProductControllerTestBase.PRODUCT_ID))
                .andExpect(view().name(View.PRODUCT));
    }

    @Test
    public void shouldShowFoundProduct() throws Exception {
        mockMvc.perform(get("/products/product/{productId}", ProductControllerTestBase.PRODUCT_ID))
                .andDo(print())
                .andExpect(model().attribute(ModelAttributeName.PRODUCT_DTO, allOf(
                        hasProperty(PRODUCT.NAME, is(PRODUCT_NAME)),
                        hasProperty(PRODUCT.DESCRIPTION, is(ProductControllerTestBase.PRODUCT_DESC)),
                        hasProperty(PRODUCT.PRICE, is(ProductControllerTestBase.PRICE)),
                        hasProperty(PRODUCT.CATEGORY, is(ProductControllerTestBase.CATEGORY)),
                        hasProperty(PRODUCT.IMAGE_URL, is(ProductControllerTestBase.IMAGE_URL)),
                        hasProperty(PRODUCT.ID, nullValue())
                )));
    }
}
