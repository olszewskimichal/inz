package com.inz.praca.units.service;

import com.inz.praca.UnitTest;
import com.inz.praca.category.Category;
import com.inz.praca.category.CategoryNotFoundException;
import com.inz.praca.category.CategoryRepository;
import com.inz.praca.products.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doAnswer;

@org.junit.experimental.categories.Category(UnitTest.class)
public class ProductServiceTest {

    private static final String NAME = "name";

    private static final Long ID = 1L;

    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        productService = new ProductService(this.productRepository, this.categoryRepository);
    }

    @Test
    public void shouldThrownIllegalArgumentExceptionWhenNameIsNull() {
        try {
            productService.getProductByName(null);
            Assert.fail("Nie mozna podac nullowego argumentu");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Podano pusta nazwe produktu");
        }
    }

    @Test
    public void shouldThrownProductNotFoundExceptionWhenProductByNameNotExist() {
        try {
            given(productRepository.findByName(anyString())).willReturn(Optional.empty());
            productService.getProductByName(ProductServiceTest.NAME);
            Assert.fail("Nie powinno znalezc produktu o podanej nazwie");
        } catch (ProductNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Nie znaleziono produktu o nazwie name");
        }
    }

    @Test
    public void shouldReturnProductWhenProductByNameExists() {
        given(productRepository.findByName(ProductServiceTest.NAME)).willReturn(
                Optional.of(new ProductBuilder().withName(ProductServiceTest.NAME)
                        .withDescription("desc")
                        .withUrl("url")
                        .withPrice(BigDecimal.TEN)
                        .createProduct()));
        Product productByName = productService.getProductByName(ProductServiceTest.NAME);
        assertThat(productByName).isNotNull();
        assertThat(productByName.getName()).isEqualTo(ProductServiceTest.NAME);
    }

    @Test
    public void shouldThrownIllegalArgumentExceptionWhenIdIsNull() {
        try {
            productService.getProductById(null);
            Assert.fail("Nie mozna podac nullowego argumentu");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Podano pusty id produktu");
        }
    }

    @Test
    public void shouldThrownProductNotFoundExceptionWhenProductByIdNotExist() {
        try {
            given(productRepository.findById(anyLong())).willReturn(Optional.empty());
            productService.getProductById(ProductServiceTest.ID);
            Assert.fail("Nie powinno znalezc produktu o podanej nazwie");
        } catch (ProductNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Nie znaleziono produktu o id 1");
        }
    }

    @Test
    public void shouldThrownCategoryNotFoundExceptionWhenCategoryByNameNotExists() {
        try {
            given(this.categoryRepository.findByName(anyString())).willReturn(Optional.empty());
            ProductDTO productDTO = new ProductDTO();
            productDTO.setName("nazwa");
            productDTO.setDescription("opis");
            productDTO.setImageUrl("url");
            productDTO.setPrice(BigDecimal.TEN);
            productDTO.setCategory("inne");

            doAnswer(invocation -> invocation.getArguments()[0]).when(this.productRepository).save(any(Product.class));
            this.productService.createProductFromDTO(productDTO);
            Assert.fail();
        } catch (CategoryNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Nie znaleziono kategorii o nazwie inne");
        }
    }

    @Test
    public void shouldReturnProductWhenProductByIdExists() {
        given(productRepository.findById(ProductServiceTest.ID)).willReturn(
                Optional.of(new ProductBuilder().withName(ProductServiceTest.NAME)
                        .withDescription("desc")
                        .withUrl("url")
                        .withPrice(BigDecimal.TEN)
                        .createProduct()));
        Product productById = productService.getProductById(ProductServiceTest.ID);
        assertThat(productById).isNotNull();
        assertThat(productById.getName()).isEqualTo(ProductServiceTest.NAME);
    }

    @Test
    public void shouldThrownIllegalArgumentExceptionWhenSizeOrPageNumberIsIncorrect() {
        try {
            productService.getProducts(-1, 2, null, Optional.empty());
            Assert.fail("Nie mozna podac minusowej strony");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Podano nieprawidlowy numer strony");
        }

        try {
            productService.getProducts(0, 0, null, Optional.empty());
            Assert.fail("Nie mozna podac rozmiaru mniejszego od 1");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Podano rozmiar mniejszy od 1");
        }
    }

    @Test
    public void shouldReturn1ProductsAscByIdOnFirstPage() {
        Page<Product> products = new PageImpl<>(
                Collections.singletonList(
                        new ProductBuilder().withName("name3").withPrice(BigDecimal.TEN).createProduct()));
        given(productRepository.findAllByActive(new PageRequest(0, 1, Direction.ASC, "id"), true)).willReturn(
                products);
        List<Product> asc = productService.getProducts(0, 1, "asc", Optional.empty()).getContent();
        assertThat(asc).isNotNull().isNotEmpty();
        assertThat(asc.size()).isEqualTo(1);
        assertThat(asc.get(0).getName()).isEqualTo("name3");
        assertThat(asc.get(0).getDescription()).isEqualTo("opis");
        assertThat(asc.get(0).getImageUrl()).isEqualTo("url");
        assertThat(asc.get(0).getPrice()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void shouldReturn20ProductsOnPageWhenDefaultSettings() {
        Page<Product> products = new PageImpl<>(
                Arrays.asList(new ProductBuilder().withName("name3").withPrice(BigDecimal.TEN).createProduct(),
                        new ProductBuilder().withName("name2")
                                .withDescription("desc")
                                .withUrl("url")
                                .withPrice(BigDecimal.TEN)
                                .createProduct()));
        given(productRepository.findAllByActive(new PageRequest(0, 5, null, "id"), true)).willReturn(products);
        List<Product> asc = productService.getProducts(null, null, null, Optional.empty()).getContent();
        assertThat(asc).isNotNull().isNotEmpty();
        assertThat(asc.size()).isEqualTo(2);
        assertThat(asc.get(0).getName()).isEqualTo("name3");
        assertThat(asc.get(1).getName()).isEqualTo("name2");
    }

    @Test
    public void shouldReturn2ProductsOnPageWhenSizeIsEqualThenMax() {
        Page<Product> products = new PageImpl<>(
                Arrays.asList(new ProductBuilder().withName("name3").withPrice(BigDecimal.TEN).createProduct(),
                        new ProductBuilder().withName("name2")
                                .withDescription("desc")
                                .withUrl("url")
                                .withPrice(BigDecimal.TEN)
                                .createProduct()));
        given(productRepository.findAllByActive(new PageRequest(0, 19, null, "id"), true)).willReturn(products);
        List<Product> asc = productService.getProducts(0, 19, null, Optional.empty()).getContent();
        assertThat(asc).isNotNull().isNotEmpty();
        assertThat(asc.size()).isEqualTo(2);
        assertThat(asc.get(0).getName()).isEqualTo("name3");
        assertThat(asc.get(1).getName()).isEqualTo("name2");
    }

    @Test
    public void shouldCreateProductFromDTO() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("nazwa");
        productDTO.setDescription("opis");
        productDTO.setImageUrl("url");
        productDTO.setPrice(BigDecimal.TEN);
        productDTO.setCategory("inne");

        doAnswer(invocation -> invocation.getArguments()[0]).when(this.productRepository).save(any(Product.class));

        given(this.categoryRepository.findByName("inne")).willReturn(Optional.of(new Category("inne", "b")));

        Product product = this.productService.createProductFromDTO(productDTO);
        assertThat(product.getName()).isEqualTo(productDTO.getName());
        assertThat(product.getPrice()).isEqualTo(productDTO.getPrice());
        assertThat(product.getImageUrl()).isEqualTo(productDTO.getImageUrl());
        assertThat(product.getDescription()).isEqualTo(productDTO.getDescription());
    }

    @Test
    public void shouldThrowExceptionWhenCreateProductFromDTO() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setDescription("opis");
        productDTO.setImageUrl("url");
        productDTO.setPrice(BigDecimal.TEN);
        try {
            productService.createProductFromDTO(productDTO);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Nie moze być pusta nazwa produktu");
        }
    }

    @Test
    public void shouldReturnProductsOnPageByCategory() {
        Page<Product> products = new PageImpl<>(
                Arrays.asList(new ProductBuilder().withName("name3").withPrice(BigDecimal.TEN).createProduct(),
                        new ProductBuilder().withName("name2")
                                .withDescription("desc")
                                .withUrl("url")
                                .withPrice(BigDecimal.TEN)
                                .createProduct()));
        Category category = new Category("category", "aaa");
        given(this.categoryRepository.findByName("category")).willReturn(Optional.of(category));
        given(productRepository.findByCategory(new PageRequest(0, 5, null, "id"), category)).willReturn(products);
        List<Product> asc = productService.getProducts(null, null, null, Optional.ofNullable("category")).getContent();
        assertThat(asc).isNotNull().isNotEmpty();
        assertThat(asc.size()).isEqualTo(2);
        assertThat(asc.get(0).getName()).isEqualTo("name3");
        assertThat(asc.get(1).getName()).isEqualTo("name2");
    }
}
