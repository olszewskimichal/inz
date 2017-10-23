package com.inz.praca.units.controller.product;

import com.inz.praca.UnitTest;
import com.inz.praca.integration.WebTestConfig;
import com.inz.praca.products.ProductController;
import com.inz.praca.products.ProductService;
import org.junit.Before;
import org.junit.experimental.categories.Category;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static com.inz.praca.integration.WebTestConfig.exceptionResolver;
import static org.mockito.Mockito.mock;

@Category(UnitTest.class)
abstract class ProductControllerTestBase {

    static final int MAX_LENGTH_NAME = 15;
    static final int MIN_LENGTH_NAME = 4;
    static final Boolean FEEDBACK_MESSAGE_PRODUCT_CREATED = true;
    static final Long PRODUCT_ID = 1L;
    static final BigDecimal PRICE = BigDecimal.TEN;
    static final String CATEGORY = "kat";
    static final String IMAGE_URL = "URL";
    static final String PRODUCT_DESC = "DESC";
    final String PRODUCT_NAME = "productname";
    ProductService productService;
    MockMvc mockMvc;
    String name;
    BigDecimal price;
    String category;

    @Before
    public void configureSystemUnderTest() {
        this.productService = mock(ProductService.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(this.productService))
                .setViewResolvers(WebTestConfig.viewResolver())
                .setHandlerExceptionResolvers(exceptionResolver())
                .build();
    }
}
