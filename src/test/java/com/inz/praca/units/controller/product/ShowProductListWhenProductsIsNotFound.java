package com.inz.praca.units.controller.product;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.Optional;

import static com.inz.praca.WebTestConstants.ModelAttributeName.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

public class ShowProductListWhenProductsIsNotFound extends ProductControllerTestBase {

    @Before
    public void returnEmptyProductsList() {
        given(this.productService.getProducts(1, 6, null, Optional.ofNullable(null))).willReturn(new PageImpl<>(new ArrayList<>()));
    }

    @Test
    public void shouldShowEmptyProductsList() throws Exception {
        this.mockMvc.perform(get("/products"))
                .andDo(print())
                .andExpect(model().attribute(PRODUCT_LIST, hasSize(0)))
                .andExpect(model().attribute(SELECTED_PAGE_SIZE, Matchers.equalTo(6)))
                .andExpect(model().attribute(PAGER, Matchers.notNullValue()));
    }
}
