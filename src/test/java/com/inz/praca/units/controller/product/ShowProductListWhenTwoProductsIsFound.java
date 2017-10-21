package com.inz.praca.units.controller.product;

import com.inz.praca.WebTestConstants;
import com.inz.praca.products.Product;
import com.inz.praca.products.ProductBuilder;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.Optional;

import static com.inz.praca.WebTestConstants.ModelAttributeName.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

public class ShowProductListWhenTwoProductsIsFound extends ProductControllerTestBase {

    Product first;
    Product second;

    @Before
    public void returnTwoProducts() {
        first = new ProductBuilder().withName(PRODUCT_NAME).withPrice(PRICE).withCategory(CATEGORY).withDescription(PRODUCT_DESC).withUrl(IMAGE_URL).createProduct();
        second = new ProductBuilder().withName(PRODUCT_NAME).withPrice(PRICE).withCategory(CATEGORY).withDescription(PRODUCT_DESC).withUrl(IMAGE_URL).createProduct();
        given(productService.getProducts(0, 6, null, Optional.ofNullable(null))).willReturn(new PageImpl<>(Arrays.asList(first, second)));
    }

    @Test
    public void shouldShowProductsListThatHasTwoProducts() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(model().attribute(PRODUCT_LIST, hasSize(2)))
                .andExpect(model().attribute(SELECTED_PAGE_SIZE, Matchers.equalTo(6)))
                .andExpect(model().attribute(PAGER, Matchers.notNullValue()));  //equalsTO
    }

    @Test
    public void shouldShowTwoProducts() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_LIST, contains(first, second)));
    }

    @Test
    public void shouldShowCorrectInformationAboutProducts() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_LIST, allOf(
                        hasItem(allOf(
                                hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCTS.NAME, is(PRODUCT_NAME)),
                                hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCTS.PRICE, is(PRICE))
                        )),
                        hasItem(allOf(
                                hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCTS.NAME, is(PRODUCT_NAME)),
                                hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCTS.PRICE, is(PRICE))
                        ))
                )));
    }
}
