package com.inz.praca.units.controller.product;

import com.inz.praca.WebTestConstants.ModelAttributeProperty.PRODUCTS;
import com.inz.praca.products.Product;
import com.inz.praca.products.ProductBuilder;
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

    private Product first;
    private Product second;

    @Before
    public void returnTwoProducts() {
        first = new ProductBuilder().withName(PRODUCT_NAME).withPrice(ProductControllerTestBase.PRICE).withCategory(ProductControllerTestBase.CATEGORY).withDescription(ProductControllerTestBase.PRODUCT_DESC).withUrl(ProductControllerTestBase.IMAGE_URL).createProduct();
        second = new ProductBuilder().withName(PRODUCT_NAME).withPrice(ProductControllerTestBase.PRICE).withCategory(ProductControllerTestBase.CATEGORY).withDescription(ProductControllerTestBase.PRODUCT_DESC).withUrl(ProductControllerTestBase.IMAGE_URL).createProduct();
        given(productService.getProducts(0, 6, null, Optional.empty())).willReturn(new PageImpl<>(Arrays.asList(first, second)));
    }

    @Test
    public void shouldShowProductsListThatHasTwoProducts() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(model().attribute(PRODUCT_LIST, hasSize(2)))
                .andExpect(model().attribute(SELECTED_PAGE_SIZE, equalTo(6)))
                .andExpect(model().attribute(PAGER, notNullValue()));  //equalsTO
    }

    @Test
    public void shouldShowTwoProducts() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(model().attribute(PRODUCT_LIST, contains(first, second)));
    }

    @Test
    public void shouldShowCorrectInformationAboutProducts() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(model().attribute(PRODUCT_LIST, allOf(
                        hasItem(allOf(
                                hasProperty(PRODUCTS.NAME, is(PRODUCT_NAME)),
                                hasProperty(PRODUCTS.PRICE, is(ProductControllerTestBase.PRICE))
                        )),
                        hasItem(allOf(
                                hasProperty(PRODUCTS.NAME, is(PRODUCT_NAME)),
                                hasProperty(PRODUCTS.PRICE, is(ProductControllerTestBase.PRICE))
                        ))
                )));
    }
}
