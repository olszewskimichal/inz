package com.inz.praca.units.controller.product;

import com.inz.praca.WebTestConstants;
import com.inz.praca.WebTestConstants.ErrorView;
import com.inz.praca.products.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ShowProductWhenProductIsNotFound extends ProductControllerTestBase {

    @Before
    public void throwTaskNotFoundException() {
        given(productService.getProductDTOById(ProductControllerTestBase.PRODUCT_ID)).willThrow(new ProductNotFoundException(ProductControllerTestBase.PRODUCT_ID));
    }

    @Test
    public void shouldReturnHttpStatusCodeNotFound() throws Exception {
        mockMvc.perform(get("/products/product/{productId}", ProductControllerTestBase.PRODUCT_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldRenderNotFoundView() throws Exception {
        mockMvc.perform(get("/products/product/{productId}", ProductControllerTestBase.PRODUCT_ID))
                .andDo(print())
                .andExpect(view().name(ErrorView.NOT_FOUND));
    }
}
