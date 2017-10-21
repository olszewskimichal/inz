package com.inz.praca.units.controller;

import com.inz.praca.UnitTest;
import com.inz.praca.WebTestConstants;
import com.inz.praca.integration.WebTestConfig;
import com.inz.praca.products.*;
import com.inz.praca.units.TestStringUtil;
import de.bechte.junit.runners.context.HierarchicalContextRunner;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.inz.praca.WebTestConstants.ModelAttributeName.*;
import static com.inz.praca.integration.WebTestConfig.exceptionResolver;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Category(UnitTest.class)
@RunWith(HierarchicalContextRunner.class)
public class ProductControllerNewTest {

    private static final int MAX_LENGTH_NAME = 15;
    private static final int MIN_LENGTH_NAME = 4;
    private static final Boolean FEEDBACK_MESSAGE_PRODUCT_CREATED = true;
    private static final Long PRODUCT_ID = 1L;
    private static final BigDecimal PRICE = BigDecimal.TEN;
    static final String PRODUCT_NAME = "productname";
    static final String CATEGORY = "kat";
    static final String IMAGE_URL = "URL";
    static final String PRODUCT_DESC = "DESC";
    private ProductService productService;
    private MockMvc mockMvc;

    @Before
    public void configureSystemUnderTest() {
        productService = mock(ProductService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productService))
                .setViewResolvers(WebTestConfig.viewResolver())
                .setHandlerExceptionResolvers(exceptionResolver())
                .build();
    }

    public class ShowAddProductForm {

        @Test
        public void shouldReturnHttpStatusCodeOk() throws Exception {
            mockMvc.perform(get("/addProduct"))
                    .andExpect(status().isOk());
        }

        @Test
        public void shouldRenderAddProductView() throws Exception {
            mockMvc.perform(get("/addProduct"))
                    .andExpect(view().name(WebTestConstants.View.ADD_PRODUCT));
        }

        @Test
        public void shouldCreateAnEmptyFormObject() throws Exception {
            mockMvc.perform(get("/addProduct"))
                    .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM, allOf(
                            hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, nullValue()),
                            hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, nullValue()),
                            hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, nullValue()),
                            hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.IMAGE_URL, nullValue()),
                            hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.ID, nullValue()),
                            hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.DESCRIPTION, nullValue())
                    )));
        }
    }

    public class ProcessAddProductForm {

        public class WhenValidationFail {

            public class WhenEmptyFormIsSubmitted {

                @Test
                public void shouldReturnHttpStatusCodeOk() throws Exception {
                    mockMvc.perform(post("/addProduct")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                            .andExpect(status().isOk());
                }

                @Test
                public void shouldRenderCreateProductView() throws Exception {
                    mockMvc.perform(post("/addProduct")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                            .andExpect(view().name(WebTestConstants.View.ADD_PRODUCT));
                }

                @Test
                public void shouldShowValidationErrorForEmptyName() throws Exception {
                    mockMvc.perform(post("/addProduct")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                            .andExpect(model().attributeHasFieldErrorCode(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                                    WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, is(WebTestConstants.ValidationErrorCode.SIZE)
                            ));
                }

                @Test
                public void shouldShowValidationErrorForEmptyCategory() throws Exception {
                    mockMvc.perform(post("/addProduct")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                            .andExpect(model().attributeHasFieldErrorCode(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                                    WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, is(WebTestConstants.ValidationErrorCode.EMPTY_FIELD)
                            ));
                }

                @Test
                public void shouldNotModifyIdField() throws Exception {
                    mockMvc.perform(post("/addProduct")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                            .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                                    hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.ID, Matchers.nullValue())
                            ));
                }

                @Test
                public void shouldNotCreateNewProduct() throws Exception {
                    mockMvc.perform(post("/addProduct")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""));

                    verify(productService, never()).createProductFromDTO(isA(ProductDTO.class));
                }

                @Test
                public void shouldShowEmptyForm() throws Exception {
                    mockMvc.perform(post("/addProduct")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                            .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM, allOf(
                                    hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, is("")),
                                    hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, nullValue()),
                                    hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, is("")),
                                    hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.IMAGE_URL, nullValue()),
                                    hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.ID, nullValue()),
                                    hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.DESCRIPTION, nullValue())
                            )));
                }

            }

            public class WhenFormIsSubmittedWithTooLongName {
                String name;
                BigDecimal price;
                String category;

                @Before
                public void createFields() {
                    name = TestStringUtil.createStringWithLength(MAX_LENGTH_NAME + 1);
                    price = BigDecimal.TEN;
                    category = "others";
                }

                @Test
                public void shouldReturnHttpStatusCodeOk() throws Exception {
                    mockMvc.perform(post("/addProduct")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                            .andExpect(status().isOk());
                }

                @Test
                public void shouldRenderCreateProductView() throws Exception {
                    mockMvc.perform(post("/addProduct")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                            .andExpect(view().name(WebTestConstants.View.ADD_PRODUCT));
                }

                @Test
                public void shouldShowValidationErrorForToLongName() throws Exception {
                    mockMvc.perform(post("/addProduct")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                            .andExpect(model().attributeHasFieldErrorCode(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                                    WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, is(WebTestConstants.ValidationErrorCode.SIZE)
                            ));
                }

                @Test
                public void shouldNotModifyIdField() throws Exception {
                    mockMvc.perform(post("/addProduct")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                            .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                                    hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.ID, Matchers.nullValue())
                            ));
                }

                @Test
                public void shouldShowEnteredValues() throws Exception {
                    mockMvc.perform(post("/addProduct")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                            .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM, allOf(
                                    hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, is(name)),
                                    hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, is(price)),
                                    hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, is(category)),
                                    hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.IMAGE_URL, nullValue()),
                                    hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.ID, nullValue()),
                                    hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.DESCRIPTION, nullValue())
                            )));
                }

                @Test
                public void shouldNotCreateNewProduct() throws Exception {
                    mockMvc.perform(post("/addProduct")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category));

                    verify(productService, never()).createProductFromDTO(isA(ProductDTO.class));
                }

            }

            public class WhenFormIsSubmittedWithTooShortName {
                String name;
                BigDecimal price;
                String category;

                @Before
                public void createFields() {
                    name = TestStringUtil.createStringWithLength(MIN_LENGTH_NAME - 1);
                    price = BigDecimal.TEN;
                    category = "others";
                }

                @Test
                public void shouldReturnHttpStatusCodeOk() throws Exception {
                    mockMvc.perform(post("/addProduct")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                            .andExpect(status().isOk());
                }

                @Test
                public void shouldRenderCreateProductView() throws Exception {
                    mockMvc.perform(post("/addProduct")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                            .andExpect(view().name(WebTestConstants.View.ADD_PRODUCT));
                }

                @Test
                public void shouldShowValidationErrorForToLongName() throws Exception {
                    mockMvc.perform(post("/addProduct")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                            .andExpect(model().attributeHasFieldErrorCode(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                                    WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, is(WebTestConstants.ValidationErrorCode.SIZE)
                            ));
                }

                @Test
                public void shouldNotModifyIdField() throws Exception {
                    mockMvc.perform(post("/addProduct")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                            .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                                    hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.ID, Matchers.nullValue())
                            ));
                }

                @Test
                public void shouldShowEnteredValues() throws Exception {
                    mockMvc.perform(post("/addProduct")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                            .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM, allOf(
                                    hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, is(name)),
                                    hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, is(price)),
                                    hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, is(category)),
                                    hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.IMAGE_URL, nullValue()),
                                    hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.ID, nullValue()),
                                    hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.DESCRIPTION, nullValue())
                            )));
                }

                @Test
                public void shouldNotCreateNewProduct() throws Exception {
                    mockMvc.perform(post("/addProduct")
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                            .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category));

                    verify(productService, never()).createProductFromDTO(isA(ProductDTO.class));
                }

            }

            public class WhenFormIsSubmittedWithInvalidPrice {
                String name;
                BigDecimal price;
                String category;

                public class WhenPriceIsNull {

                    @Before
                    public void createFields() {
                        name = TestStringUtil.createStringWithLength(MIN_LENGTH_NAME);
                        category = "others";
                    }

                    @Test
                    public void shouldReturnHttpStatusCodeOk() throws Exception {
                        mockMvc.perform(post("/addProduct")
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                                .andExpect(status().isOk());
                    }

                    @Test
                    public void shouldRenderCreateProductView() throws Exception {
                        mockMvc.perform(post("/addProduct")
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                                .andExpect(view().name(WebTestConstants.View.ADD_PRODUCT));
                    }

                    @Test
                    public void shouldShowValidationErrorForNullPrice() throws Exception {
                        mockMvc.perform(post("/addProduct")
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                                .andExpect(model().attributeHasFieldErrorCode(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                                        WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, is(WebTestConstants.ValidationErrorCode.VALID_PRICE)
                                ));
                    }

                    @Test
                    public void shouldNotModifyIdField() throws Exception {
                        mockMvc.perform(post("/addProduct")
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                                .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.ID, Matchers.nullValue())
                                ));
                    }

                    @Test
                    public void shouldShowEnteredValues() throws Exception {
                        mockMvc.perform(post("/addProduct")
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                                .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM, allOf(
                                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, is(name)),
                                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, is(price)),
                                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, is(category)),
                                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.IMAGE_URL, nullValue()),
                                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.ID, nullValue()),
                                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.DESCRIPTION, nullValue())
                                )));
                    }

                    @Test
                    public void shouldNotCreateNewProduct() throws Exception {
                        mockMvc.perform(post("/addProduct")
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category));

                        verify(productService, never()).createProductFromDTO(isA(ProductDTO.class));
                    }
                }

                public class WhenPriceIsLessThen0 {

                    @Before
                    public void createFields() {
                        name = TestStringUtil.createStringWithLength(MIN_LENGTH_NAME);
                        price = BigDecimal.valueOf(-1L);
                        category = "others";
                    }

                    @Test
                    public void shouldReturnHttpStatusCodeOk() throws Exception {
                        mockMvc.perform(post("/addProduct")
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                                .andExpect(status().isOk());
                    }

                    @Test
                    public void shouldRenderCreateProductView() throws Exception {
                        mockMvc.perform(post("/addProduct")
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                                .andExpect(view().name(WebTestConstants.View.ADD_PRODUCT));
                    }

                    @Test
                    public void shouldShowValidationErrorForNullPrice() throws Exception {
                        mockMvc.perform(post("/addProduct")
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                                .andExpect(model().attributeHasFieldErrorCode(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                                        WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, is(WebTestConstants.ValidationErrorCode.VALID_PRICE)
                                ));
                    }

                    @Test
                    public void shouldNotModifyIdField() throws Exception {
                        mockMvc.perform(post("/addProduct")
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                                .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.ID, Matchers.nullValue())
                                ));
                    }

                    @Test
                    public void shouldShowEnteredValues() throws Exception {
                        mockMvc.perform(post("/addProduct")
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                                .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM, allOf(
                                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, is(name)),
                                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, is(price)),
                                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, is(category)),
                                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.IMAGE_URL, nullValue()),
                                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.ID, nullValue()),
                                        hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.DESCRIPTION, nullValue())
                                )));
                    }

                    @Test
                    public void shouldNotCreateNewProduct() throws Exception {
                        mockMvc.perform(post("/addProduct")
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category));

                        verify(productService, never()).createProductFromDTO(isA(ProductDTO.class));
                    }
                }

            }


        }

        public class WhenValidationSuccessful {
            String name;
            BigDecimal price;
            String category;

            @Before
            public void configureTestCases() {
                name = TestStringUtil.createStringWithLength(MAX_LENGTH_NAME);
                price = BigDecimal.TEN;
                category = "others";
            }

            @Test
            public void shouldReturnHttpStatusCodeIsFound() throws Exception {
                mockMvc.perform(post("/addProduct")
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                        .andExpect(status().isFound());
            }

            @Test
            public void shouldRenderCreateProductView() throws Exception {
                mockMvc.perform(post("/addProduct")
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                        .andExpect(view().name(WebTestConstants.RedirectView.PRODUCTS));
            }

            @Test
            public void shouldAddFeedbackMessageAsAFlashAttribute() throws Exception {
                mockMvc.perform(post("/addProduct")
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                        .andExpect(flash().attribute(WebTestConstants.FlashMessageKey.CREATE_PRODUCT_CONFIRM, FEEDBACK_MESSAGE_PRODUCT_CREATED
                        ));
            }

            @Test
            public void shouldCreateProduct() throws Exception {
                mockMvc.perform(post("/addProduct")
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category));

                verify(productService, times(1)).createProductFromDTO(isA(ProductDTO.class));
            }


        }
    }

    public class ShowProduct {

        public class WhenProductIsNotFound {

            @Before
            public void throwTaskNotFoundException() {
                given(productService.getProductDTOById(PRODUCT_ID)).willThrow(new ProductNotFoundException(PRODUCT_ID));
            }

            @Test
            public void shouldReturnHttpStatusCodeNotFound() throws Exception {
                mockMvc.perform(get("/products/product/{productId}", PRODUCT_ID))
                        .andExpect(status().isNotFound());
            }

            @Test
            public void shouldRenderNotFoundView() throws Exception {
                mockMvc.perform(get("/products/product/{productId}", PRODUCT_ID))
                        .andDo(print())
                        .andExpect(view().name(WebTestConstants.ErrorView.NOT_FOUND));
            }
        }

        public class WhenProductIsFound {

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

    }

    public class DeleteProduct {

        public class WhenProductNotFound {

            @Before
            public void throwProductNotFoundException() {
                willThrow(new ProductNotFoundException(PRODUCT_ID)).given(productService).deleteProductById(PRODUCT_ID);
            }

            @Test
            public void shouldReturnHttpStatusCodeNotFound() throws Exception {
                mockMvc.perform(get("/products/product/delete/{productId}", PRODUCT_ID))
                        .andExpect(status().isNotFound());
            }

            @Test
            public void shouldRenderNotFoundView() throws Exception {
                mockMvc.perform(get("/products/product/delete/{productId}", PRODUCT_ID))
                        .andExpect(view().name(WebTestConstants.ErrorView.NOT_FOUND));
            }
        }

        public class WhenProductIsFound {

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


    }

    public class ProductList {

        @Test
        public void shouldReturnHttpStatusCodeOk() throws Exception {
            mockMvc.perform(get("/products"))
                    .andExpect(status().isOk());
        }

        @Test
        public void shouldRenderProductsListView() throws Exception {
            mockMvc.perform(get("/products"))
                    .andExpect(view().name(WebTestConstants.View.PRODUCTS));
        }

        public class WhenProductsIsNotFound {

            @Before
            public void returnEmptyProductsList() {
                given(productService.getProducts(1, 6, null, Optional.ofNullable(null))).willReturn(new PageImpl<>(new ArrayList<>()));
            }

            @Test
            public void shouldShowEmptyProductsList() throws Exception {
                mockMvc.perform(get("/products"))
                        .andDo(print())
                        .andExpect(model().attribute(PRODUCT_LIST, hasSize(0)))
                        .andExpect(model().attribute(SELECTED_PAGE_SIZE, Matchers.equalTo(6)))
                        .andExpect(model().attribute(PAGER, Matchers.notNullValue()));
            }
        }

        public class WhenTwoProductsAreFound {

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

        public class WhenMoreThenSixProductsAreFound {

            List<Product> firstPageList = new ArrayList<>();
            List<Product> secondPageList = new ArrayList<>();

            @Before
            public void returnMoreThen6Products() {
                IntStream.range(0, 6).forEachOrdered(v -> {
                    firstPageList.add(new ProductBuilder().withName(PRODUCT_NAME + v).withPrice(PRICE).withCategory(CATEGORY).withDescription(PRODUCT_DESC).withUrl(IMAGE_URL).createProduct());
                });
                IntStream.range(0, 3).forEachOrdered(v -> {
                    secondPageList.add(new ProductBuilder().withName(PRODUCT_NAME + v).withPrice(PRICE).withCategory(CATEGORY).withDescription(PRODUCT_DESC).withUrl(IMAGE_URL).createProduct());
                });
                given(productService.getProducts(0, 6, null, Optional.ofNullable(null))).willReturn(new PageImpl<>(firstPageList));
                given(productService.getProducts(1, 6, null, Optional.ofNullable(null))).willReturn(new PageImpl<>(secondPageList));
            }

            @Test
            public void shouldShowProductsListFromFirstPageWhenPageParamIsNotSet() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(model().attribute(PRODUCT_LIST, hasSize(6)));
            }

            @Test
            public void shouldShowProductsListFromFirstPageWhenPageParamIsSet() throws Exception {
                mockMvc.perform(get("/products").
                        param(WebTestConstants.ModelAttributeProperty.PRODUCTS.PAGE, "1"))
                        .andExpect(model().attribute(PRODUCT_LIST, hasSize(6)));
            }

            @Test
            public void shouldShowProductsListFromSecondPageWhenPageParamIsSet() throws Exception {
                mockMvc.perform(get("/products").
                        param(WebTestConstants.ModelAttributeProperty.PRODUCTS.PAGE, "2"))
                        .andExpect(model().attribute(PRODUCT_LIST, hasSize(3)));
            }
        }
    }

    public class ShowUpdateProductForm {

        public class WhenUpdatedProductIsNotFound {

            @Before
            public void throwProductNotFoundException() {
                given(productService.getProductDTOById(PRODUCT_ID)).willThrow(new ProductNotFoundException(PRODUCT_ID));
            }


            @Test
            public void shouldReturnHttpStatusCodeNotFound() throws Exception {
                mockMvc.perform(get("/products/product/edit/{id}", PRODUCT_ID))
                        .andExpect(status().isNotFound());
            }

            @Test
            public void shouldRenderNotFoundView() throws Exception {
                mockMvc.perform(get("/products/product/edit/{id}", PRODUCT_ID))
                        .andExpect(view().name(WebTestConstants.ErrorView.NOT_FOUND));
            }
        }

        public class WhenUpdatedProductIsFound {

            @Before
            public void returnProduct() {
                ProductDTO productDTO = new ProductBuilder().withName(PRODUCT_NAME).withPrice(PRICE).withCategory(CATEGORY).withDescription(PRODUCT_DESC).withUrl(IMAGE_URL).createProductDTO();
                given(productService.getProductDTOById(PRODUCT_ID)).willReturn(productDTO);
            }

            @Test
            public void shouldReturnHttpStatusCodeNotFound() throws Exception {
                mockMvc.perform(get("/products/product/edit/{id}", PRODUCT_ID))
                        .andExpect(status().isOk());
            }

            @Test
            public void shouldRenderUpdateProductView() throws Exception {
                mockMvc.perform(get("/products/product/edit/{id}", PRODUCT_ID))
                        .andExpect(view().name(WebTestConstants.View.EDIT_PRODUCT));
            }

            @Test
            public void shouldShowInformationOfUpdatedProduct() throws Exception {
                mockMvc.perform(get("/products/product/edit/{id}", PRODUCT_ID))
                        .andDo(print())
                        .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM, allOf(
                                hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, is(PRODUCT_NAME)),
                                hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.DESCRIPTION, is(PRODUCT_DESC)),
                                hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, is(PRICE)),
                                hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, is(CATEGORY)),
                                hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.IMAGE_URL, is(IMAGE_URL)),
                                hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.ID, nullValue())
                        )));
            }
        }
    }

    public class ProcessUpdateProduct {

        public class WhenValidationFailed {

            @Test
            public void shouldReturnHttpStatusCodeOk() throws Exception {
                mockMvc.perform(post("/products/product/edit/{id}", PRODUCT_ID)
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                        .andExpect(status().isOk());
            }

            @Test
            public void shouldRenderCreateProductView() throws Exception {
                mockMvc.perform(post("/products/product/edit/{id}", PRODUCT_ID)
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                        .andExpect(view().name(WebTestConstants.View.EDIT_PRODUCT));
            }

            @Test
            public void shouldShowValidationErrorForEmptyName() throws Exception {
                mockMvc.perform(post("/products/product/edit/{id}", PRODUCT_ID)
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                        .andExpect(model().attributeHasFieldErrorCode(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                                WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, is(WebTestConstants.ValidationErrorCode.SIZE)
                        ));
            }

            @Test
            public void shouldShowValidationErrorForEmptyCategory() throws Exception {
                mockMvc.perform(post("/products/product/edit/{id}", PRODUCT_ID)
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                        .andExpect(model().attributeHasFieldErrorCode(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                                WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, is(WebTestConstants.ValidationErrorCode.EMPTY_FIELD)
                        ));
            }

            @Test
            public void shouldNotModifyIdField() throws Exception {
                mockMvc.perform(post("/products/product/edit/{id}", PRODUCT_ID)
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""))
                        .andExpect(model().attribute(WebTestConstants.ModelAttributeName.PRODUCT_CREATE_FORM,
                                hasProperty(WebTestConstants.ModelAttributeProperty.PRODUCT.ID, Matchers.nullValue())
                        ));
            }

            @Test
            public void shouldNotUpdateProduct() throws Exception {
                mockMvc.perform(post("/products/product/edit/{id}", PRODUCT_ID)
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, "")
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, "")
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, ""));

                verify(productService, never()).createProductFromDTO(isA(ProductDTO.class));
            }
        }

        public class WhenValidationIsSuccessful {

            String name;
            BigDecimal price;
            String category;

            @Before
            public void configureTestCases() {
                name = TestStringUtil.createStringWithLength(MAX_LENGTH_NAME);
                price = BigDecimal.ONE;
                category = "others";
            }

            @Test
            public void shouldReturnHttpStatusCodeIsFound() throws Exception {
                mockMvc.perform(post("/products/product/edit/{id}", PRODUCT_ID)
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                        .andExpect(status().isFound());
            }

            @Test
            public void shouldRenderCreateProductView() throws Exception {
                mockMvc.perform(post("/products/product/edit/{id}", PRODUCT_ID)
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                        .andExpect(view().name(WebTestConstants.RedirectView.PRODUCTS + "/product/1"));
            }

            @Test
            public void shouldUpdateProduct() throws Exception {
                mockMvc.perform(post("/products/product/edit/{id}", PRODUCT_ID)
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                        .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category));

                verify(productService, times(1)).updateProduct(eq(PRODUCT_ID), isA(ProductDTO.class));
            }
        }

    }
}
