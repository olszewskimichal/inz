package com.inz.praca.units.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.inz.praca.products.ProductBuilder;
import com.inz.praca.category.Category;
import com.inz.praca.products.Product;
import com.inz.praca.products.ProductDTO;
import com.inz.praca.category.CategoryNotFoundException;
import com.inz.praca.products.ProductNotFoundException;
import com.inz.praca.category.CategoryRepository;
import com.inz.praca.products.ProductRepository;
import com.inz.praca.products.ProductService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
		this.productService = new ProductService(productRepository, categoryRepository);
	}

	@Test
	public void shouldThrownIllegalArgumentExceptionWhenNameIsNull() {
		try {
			this.productService.getProductByName(null);
			Assert.fail("Nie mozna podac nullowego argumentu");
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("Podano pusta nazwe produktu");
		}
	}

	@Test
	public void shouldThrownProductNotFoundExceptionWhenProductByNameNotExist() {
		try {
			given(this.productRepository.findByName(anyString())).willReturn(Optional.empty());
			this.productService.getProductByName(NAME);
			Assert.fail("Nie powinno znalezc produktu o podanej nazwie");
		}
		catch (ProductNotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("Nie znaleziono produktu o nazwie name");
		}
	}

	@Test
	public void shouldReturnProductWhenProductByNameExists() {
		given(this.productRepository.findByName(NAME)).willReturn(
				Optional.of(new ProductBuilder().withName(NAME).withDescription("desc").withUrl("url").withPrice(BigDecimal.TEN).createProduct()));
		Product productByName = this.productService.getProductByName(NAME);
		assertThat(productByName).isNotNull();
		assertThat(productByName.getName()).isEqualTo(NAME);
	}

	@Test
	public void shouldThrownIllegalArgumentExceptionWhenIdIsNull() {
		try {
			this.productService.getProductById(null);
			Assert.fail("Nie mozna podac nullowego argumentu");
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("Podano pusty id produktu");
		}
	}

	@Test
	public void shouldThrownProductNotFoundExceptionWhenProductByIdNotExist() {
		try {
			given(this.productRepository.findById(anyLong())).willReturn(Optional.empty());
			this.productService.getProductById(ID);
			Assert.fail("Nie powinno znalezc produktu o podanej nazwie");
		}
		catch (ProductNotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("Nie znaleziono produktu o id 1");
		}
	}

	@Test
	public void shouldThrownCategoryNotFoundExceptionWhenCategoryByNameNotExists() {
		try {
			given(categoryRepository.findByName(anyString())).willReturn(Optional.empty());
			ProductDTO productDTO = new ProductDTO();
			productDTO.setName("nazwa");
			productDTO.setDescription("opis");
			productDTO.setImageUrl("url");
			productDTO.setPrice(BigDecimal.TEN);
			productDTO.setCategory("inne");

			doAnswer(invocation -> {
				Product argument = (Product) invocation.getArguments()[0];
				argument.setId(1L);
				return argument;
			}).when(productRepository).save(any(Product.class));
			productService.createProductFromDTO(productDTO);
			Assert.fail();
		}
		catch (CategoryNotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("Nie znaleziono kategorii o nazwie inne");
		}
	}

	@Test
	public void shouldReturnProductWhenProductByIdExists() {
		given(this.productRepository.findById(ID)).willReturn(
				Optional.of(new ProductBuilder().withName(NAME).withDescription("desc").withUrl("url").withPrice(BigDecimal.TEN).createProduct()));
		Product productById = this.productService.getProductById(ID);
		assertThat(productById).isNotNull();
		assertThat(productById.getName()).isEqualTo(NAME);
	}

	@Test
	public void shouldThrownIllegalArgumentExceptionWhenSizeOrPageNumberIsIncorrect() {
		try {
			this.productService.getProducts(-1, 2, null, null);
			Assert.fail("Nie mozna podac minusowej strony");
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("Podano nieprawidlowy numer strony");
		}

		try {
			this.productService.getProducts(0, 0, null, null);
			Assert.fail("Nie mozna podac rozmiaru mniejszego od 1");
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("Podano rozmiar mniejszy od 1");
		}
	}

	@Test
	public void shouldReturn1ProductsAscByIdOnFirstPage() {
		Page<Product> products = new PageImpl<>(
				Collections.singletonList(new ProductBuilder().withName("name3").withPrice(BigDecimal.TEN).createProduct()));
		given(this.productRepository.findAllByActive(new PageRequest(0, 1, Sort.Direction.ASC, "id"), true)).willReturn(products);
		List<Product> asc = this.productService.getProducts(1, 1, "asc", null).getContent();
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
						new ProductBuilder().withName("name2").withDescription("desc").withUrl("url").withPrice(BigDecimal.TEN).createProduct()));
		given(this.productRepository.findAllByActive(new PageRequest(0, 5, null, "id"), true)).willReturn(products);
		List<Product> asc = this.productService.getProducts(null, null, null, null).getContent();
		assertThat(asc).isNotNull().isNotEmpty();
		assertThat(asc.size()).isEqualTo(2);
		assertThat(asc.get(0).getName()).isEqualTo("name3");
		assertThat(asc.get(1).getName()).isEqualTo("name2");
	}

	@Test
	public void shouldReturn2ProductsOnPageWhenSizeIsEqualThenMax() {
		Page<Product> products = new PageImpl<>(
				Arrays.asList(new ProductBuilder().withName("name3").withPrice(BigDecimal.TEN).createProduct(),
						new ProductBuilder().withName("name2").withDescription("desc").withUrl("url").withPrice(BigDecimal.TEN).createProduct()));
		given(this.productRepository.findAllByActive(new PageRequest(0, 19, null, "id"), true)).willReturn(products);
		List<Product> asc = this.productService.getProducts(1, 19, null, null).getContent();
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

		doAnswer(invocation -> {
			Product argument = (Product) invocation.getArguments()[0];
			argument.setId(1L);
			return argument;
		}).when(productRepository).save(any(Product.class));

		given(categoryRepository.findByName("inne")).willReturn(Optional.of(new Category("inne", "b")));

		Product product = productService.createProductFromDTO(productDTO);
		assertThat(product.getId()).isEqualTo(1L);
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
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("Nie moze być pusta nazwa produktu");
		}
	}

	@Test
	public void shouldReturnProductsOnPageByCategory() {
		Page<Product> products = new PageImpl<>(
				Arrays.asList(new ProductBuilder().withName("name3").withPrice(BigDecimal.TEN).createProduct(),
						new ProductBuilder().withName("name2").withDescription("desc").withUrl("url").withPrice(BigDecimal.TEN).createProduct()));
		Category category = new Category("category", "aaa");
		given(categoryRepository.findByName("category")).willReturn(Optional.of(category));
		given(this.productRepository.findByCategory(new PageRequest(0, 5, null, "id"), category)).willReturn(products);
		List<Product> asc = this.productService.getProducts(null, null, null, "category").getContent();
		assertThat(asc).isNotNull().isNotEmpty();
		assertThat(asc.size()).isEqualTo(2);
		assertThat(asc.get(0).getName()).isEqualTo("name3");
		assertThat(asc.get(1).getName()).isEqualTo("name2");
	}
}
