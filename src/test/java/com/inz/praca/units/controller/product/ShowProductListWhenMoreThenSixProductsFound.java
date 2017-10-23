package com.inz.praca.units.controller.product;

import com.inz.praca.WebTestConstants;
import com.inz.praca.WebTestConstants.ModelAttributeProperty;
import com.inz.praca.WebTestConstants.ModelAttributeProperty.PRODUCTS;
import com.inz.praca.products.Product;
import com.inz.praca.products.ProductBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.inz.praca.WebTestConstants.ModelAttributeName.PRODUCT_LIST;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

public class ShowProductListWhenMoreThenSixProductsFound extends ProductControllerTestBase {
    private final List<Product> firstPageList = new ArrayList<>();
    private final List<Product> secondPageList = new ArrayList<>();

    @Before
    public void returnMoreThen6Products() {
        IntStream.range(0, 6).forEachOrdered(v -> this.firstPageList.add(new ProductBuilder().withName(this.PRODUCT_NAME + v).withPrice(ProductControllerTestBase.PRICE).withCategory(ProductControllerTestBase.CATEGORY).withDescription(ProductControllerTestBase.PRODUCT_DESC).withUrl(ProductControllerTestBase.IMAGE_URL).createProduct()));
        IntStream.range(0, 3).forEachOrdered(v -> this.secondPageList.add(new ProductBuilder().withName(this.PRODUCT_NAME + v).withPrice(ProductControllerTestBase.PRICE).withCategory(ProductControllerTestBase.CATEGORY).withDescription(ProductControllerTestBase.PRODUCT_DESC).withUrl(ProductControllerTestBase.IMAGE_URL).createProduct()));
        given(this.productService.getProducts(0, 6, null, Optional.ofNullable(null))).willReturn(new PageImpl<>(this.firstPageList));
        given(this.productService.getProducts(1, 6, null, Optional.ofNullable(null))).willReturn(new PageImpl<>(this.secondPageList));
    }

    @Test
    public void shouldShowProductsListFromFirstPageWhenPageParamIsNotSet() throws Exception {
        this.mockMvc.perform(get("/products"))
                .andExpect(model().attribute(PRODUCT_LIST, hasSize(6)));
    }

    @Test
    public void shouldShowProductsListFromFirstPageWhenPageParamIsSet() throws Exception {
        this.mockMvc.perform(get("/products").
                param(PRODUCTS.PAGE, "1"))
                .andExpect(model().attribute(PRODUCT_LIST, hasSize(6)));
    }

    @Test
    public void shouldShowProductsListFromSecondPageWhenPageParamIsSet() throws Exception {
        this.mockMvc.perform(get("/products").
                param(PRODUCTS.PAGE, "2"))
                .andExpect(model().attribute(PRODUCT_LIST, hasSize(3)));
    }
}
