package com.inz.praca.units.controller;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

import java.math.BigDecimal;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.controller.ProductController;
import com.inz.praca.dto.ProductDTO;
import com.inz.praca.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public class ProductControllerTest {

	@Mock
	private Model model;

	@Mock
	private ProductService productService;

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
	public void shouldCreateUserAndRedirectToIndex() {
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
		verifyNoMoreInteractions(model);
	}

	@Test
	public void shouldShowAgainFormWhenErrorOnCreate() {
		given(bindingResult.hasErrors()).willReturn(true);

		ProductDTO productDTO = new ProductDTO();
		assertThat(controller.confirmNewProduct(productDTO, bindingResult, model)).isEqualTo("newProduct");

		verify(model).addAttribute("productCreateForm", productDTO);
		verifyNoMoreInteractions(model);
	}

	@Test
	public void shouldShowProductDetails() {
		given(productService.getProductById(1L)).willReturn(new ProductBuilder().withName("nazwa").withPrice(BigDecimal.TEN).createProduct());
		assertThat(controller.showProductDetail(1L, model)).isEqualTo("product");
	}
}
