package com.inz.praca.units.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

import java.math.BigDecimal;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.controller.CartController;
import com.inz.praca.dto.CartSession;
import com.inz.praca.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import org.springframework.ui.Model;

public class CartControllerTest {
	private CartController controller;

	@Mock
	private ProductService productService;

	@Mock
	Model model;

	@Before
	public void setUp() {
		initMocks(this);
		controller = new CartController(productService, new CartSession());
	}


	@Test
	public void shouldReturnCartPage() {
		assertThat(controller.getSessionCart(model)).isEqualTo("cart");
	}

	@Test
	public void shouldAddProductToCart() {
		given(productService.getProductById(1L)).willReturn(new ProductBuilder().withName("nazwa").withPrice(BigDecimal.TEN).createProduct());
		controller.addProductToCart(model, 1L);
		assertThat(controller.getForm().getItems().size()).isEqualTo(1);
		assertThat(controller.getForm().getTotalPrice().stripTrailingZeros()).isEqualTo(BigDecimal.TEN.stripTrailingZeros());
	}

	@Test
	public void shouldAdd2ProductAndRemove1ProductFromCartAndClearCart() {
		given(productService.getProductById(1L)).willReturn(new ProductBuilder().withName("nazwa").withPrice(BigDecimal.TEN).createProduct());
		given(productService.getProductById(2L)).willReturn(new ProductBuilder().withName("nazwa2").withPrice(BigDecimal.ONE).createProduct());
		controller.addProductToCart(model, 1L);
		controller.addProductToCart(model, 2L);
		assertThat(controller.getForm().getItems().size()).isEqualTo(2);
		assertThat(controller.getForm().getTotalPrice().stripTrailingZeros()).isEqualTo(BigDecimal.valueOf(11).stripTrailingZeros());

		controller.removeProductFromCart(1);
		assertThat(controller.getForm().getItems().size()).isEqualTo(1);
		assertThat(controller.getForm().getTotalPrice().stripTrailingZeros()).isEqualTo(BigDecimal.valueOf(10).stripTrailingZeros());

		controller.clearCart(model);
		assertThat(controller.getForm().getItems().size()).isEqualTo(0);
		assertThat(controller.getForm().getTotalPrice()).isEqualTo(BigDecimal.ZERO);
	}

}
