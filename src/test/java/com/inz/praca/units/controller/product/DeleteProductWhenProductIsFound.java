package com.inz.praca.units.controller.product;

import com.inz.praca.WebTestConstants;
import org.junit.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class DeleteProductWhenProductIsFound extends ProductControllerTestBase{
    @Test
    public void shouldReturnHttpResponseStatusFound() throws Exception {
        mockMvc.perform(get("/products/product/delete/{productId}", PRODUCT_ID))
                .andExpect(status().isFound());
    }

    @Test
    public void shouldRedirectProductToViewTaskListView() throws Exception {
        mockMvc.perform(get("/products/product/delete/{productId}", PRODUCT_ID))
                .andExpect(view().name(WebTestConstants.RedirectView.PRODUCTS));
    }

    @Test
    public void shouldDeleteTaskWithCorrectId() throws Exception {
        mockMvc.perform(get("/products/product/delete/{productId}", PRODUCT_ID));

        verify(productService, times(1)).deleteProductById(PRODUCT_ID);
    }
}
