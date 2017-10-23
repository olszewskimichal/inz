package com.inz.praca.units.controller.product;

import com.inz.praca.WebTestConstants;
import com.inz.praca.WebTestConstants.FlashMessageKey;
import com.inz.praca.WebTestConstants.ModelAttributeProperty;
import com.inz.praca.WebTestConstants.ModelAttributeProperty.PRODUCT;
import com.inz.praca.WebTestConstants.RedirectView;
import com.inz.praca.products.ProductDTO;
import com.inz.praca.units.TestStringUtil;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProcessAddProductWhenValidationSuccessful extends ProductControllerTestBase {
    @Before
    public void configureTestCases() {
        this.name = TestStringUtil.createStringWithLength(ProductControllerTestBase.MAX_LENGTH_NAME);
        this.price = BigDecimal.TEN;
        this.category = "others";
    }

    @Test
    public void shouldReturnHttpStatusCodeIsFound() throws Exception {
        this.mockMvc.perform(post("/addProduct")
                .param(PRODUCT.NAME, this.name)
                .param(PRODUCT.PRICE, this.price.toString())
                .param(PRODUCT.CATEGORY, this.category))
                .andExpect(status().isFound());
    }

    @Test
    public void shouldRenderCreateProductView() throws Exception {
        this.mockMvc.perform(post("/addProduct")
                .param(PRODUCT.NAME, this.name)
                .param(PRODUCT.PRICE, this.price.toString())
                .param(PRODUCT.CATEGORY, this.category))
                .andExpect(view().name(RedirectView.PRODUCTS));
    }

    @Test
    public void shouldAddFeedbackMessageAsAFlashAttribute() throws Exception {
        this.mockMvc.perform(post("/addProduct")
                .param(PRODUCT.NAME, this.name)
                .param(PRODUCT.PRICE, this.price.toString())
                .param(PRODUCT.CATEGORY, this.category))
                .andExpect(flash().attribute(FlashMessageKey.CREATE_PRODUCT_CONFIRM, ProductControllerTestBase.FEEDBACK_MESSAGE_PRODUCT_CREATED
                ));
    }

    @Test
    public void shouldCreateProduct() throws Exception {
        this.mockMvc.perform(post("/addProduct")
                .param(PRODUCT.NAME, this.name)
                .param(PRODUCT.PRICE, this.price.toString())
                .param(PRODUCT.CATEGORY, this.category));

        verify(this.productService, times(1)).createProductFromDTO(isA(ProductDTO.class));
    }
}
