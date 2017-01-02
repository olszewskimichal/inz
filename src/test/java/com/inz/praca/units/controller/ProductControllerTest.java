package com.inz.praca.units.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.controller.ProductController;
import com.inz.praca.domain.Category;
import com.inz.praca.dto.ProductDTO;
import com.inz.praca.repository.CategoryRepository;
import com.inz.praca.service.ProductService;
import com.inz.praca.utils.Pager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public class ProductControllerTest {

	@Mock
	private Model model;

	@Mock
	private ProductService productService;

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private BindingResult bindingResult;

	private ProductController controller;

	@Before
	public void setUp() {
		initMocks(this);
		controller = new ProductController(productService);
	}

	@Test
	public void shouldReturnRegisterPage() {
		assertThat(controller.addNewProductPage(model)).isEqualTo("newProduct");
	}

	@Test
	public void shouldCreateProductAndRedirectToIndex() {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("name");
		productDTO.setDescription("desc");
		productDTO.setPrice(BigDecimal.TEN);
		productDTO.setImageUrl("url");

		assertThat(controller.confirmNewProduct(productDTO, bindingResult, model)).isEqualTo("index");
	}

	@Test
	public void shouldFailedCreateProduct() {
		given(productService.createProduct(any(ProductDTO.class))).willThrow(new IllegalArgumentException());

		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("name");
		productDTO.setDescription("desc");
		productDTO.setPrice(BigDecimal.TEN);
		productDTO.setImageUrl("url");
		assertThat(controller.confirmNewProduct(productDTO, bindingResult, model)).isEqualTo("newProduct");

		verify(model).addAttribute("productCreateForm", productDTO);
		verify(model).addAttribute("categoryList", productService.findAllCategory());
		verifyNoMoreInteractions(model);
	}

	@Test
	public void shouldShowAgainFormWhenErrorOnCreate() {
		given(bindingResult.hasErrors()).willReturn(true);

		ProductDTO productDTO = new ProductDTO();
		assertThat(controller.confirmNewProduct(productDTO, bindingResult, model)).isEqualTo("newProduct");

		verify(model).addAttribute("productCreateForm", productDTO);
		verify(model).addAttribute("categoryList", productService.findAllCategory());
		verifyNoMoreInteractions(model);
	}

	@Test
	public void shouldShowProductDetails() {
		given(productService.getProductById(1L)).willReturn(new ProductBuilder().withName("nazwa").withPrice(BigDecimal.TEN).createProduct());
		assertThat(controller.showProductDetail(1L, model)).isEqualTo("product");
	}

	@Test
	public void shouldShowAllProducts() {
		given(productService.getProducts(1, 6, null, null)).willReturn(new PageImpl<>(new ArrayList<>()));
		assertThat(controller.showProducts(model, null, null, null)).isEqualTo("products");
	}

	@Test
	public void shouldShowAllProducts2() {
		given(productService.getProducts(2, 6, null, null)).willReturn(new PageImpl<>(new ArrayList<>()));
		assertThat(controller.showProducts(model, 2, 6, null)).isEqualTo("products");
	}

	@Test
	public void shouldShowAllProductsByCategory() {
		given(categoryRepository.findByName("kategoria")).willReturn(Optional.of(new Category("a","a")));
		given(productService.getProducts(2, 6, null, "a")).willReturn(new PageImpl<>(new ArrayList<>()));
		assertThat(controller.showProducts(model, 2, 6, "a")).isEqualTo("products");
		verify(model).addAttribute("pager", new Pager(1,1,5));
	}

	@Test
	public void shouldShowAllProductsWithEmptyCategory() {
		given(productService.getProducts(1, 6, null, null)).willReturn(new PageImpl<>(new ArrayList<>()));
		assertThat(controller.showProducts(model, null, null, "")).isEqualTo("products");
	}

	@Test
	public void shouldShowAllProducts3() {
		given(productService.getProducts(2, 2, null, null)).willReturn(new PageImpl<>(new ArrayList<>()));
		assertThat(controller.showProducts(model, 2, 2, null)).isEqualTo("products");
	}
}
