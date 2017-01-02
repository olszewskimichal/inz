package com.inz.praca.units.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

import java.math.BigDecimal;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.controller.CartController;
import com.inz.praca.dto.CartDTO;
import com.inz.praca.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class CartControllerTest {
	private CartController controller;

	@Mock
	private ProductService productService;

	@Before
	public void setUp() {
		initMocks(this);
		controller = new CartController(productService, new CartDTO());
	}

	@Test
	public void shouldReturnCartPage() {
		assertThat(controller.getSessionCart()).isEqualTo("cart");
	}

	@Test
	public void shouldAddProductToCart() {
		given(productService.getProductById(1L)).willReturn(new ProductBuilder().withName("nazwa").withPrice(BigDecimal.TEN).createProduct());
		controller.addRow(1L);
		assertThat(controller.getForm().getItems().size()).isEqualTo(1);
		assertThat(controller.getForm().getTotalPrice().stripTrailingZeros()).isEqualTo(BigDecimal.TEN.stripTrailingZeros());
	}

	@Test
	public void shouldAdd2ProductAndRemove1ProductFromCart() {
		given(productService.getProductById(1L)).willReturn(new ProductBuilder().withName("nazwa").withPrice(BigDecimal.TEN).createProduct());
		given(productService.getProductById(2L)).willReturn(new ProductBuilder().withName("nazwa2").withPrice(BigDecimal.ONE).createProduct());
		controller.addRow(1L);
		controller.addRow(2L);
		assertThat(controller.getForm().getItems().size()).isEqualTo(2);
		assertThat(controller.getForm().getTotalPrice().stripTrailingZeros()).isEqualTo(BigDecimal.valueOf(11).stripTrailingZeros());

		controller.removeRow(1);
		assertThat(controller.getForm().getItems().size()).isEqualTo(1);
		assertThat(controller.getForm().getTotalPrice().stripTrailingZeros()).isEqualTo(BigDecimal.valueOf(10).stripTrailingZeros());
	}

	@Test
	public void shouldAdd2ProductAndClearCart() {
		given(productService.getProductById(1L)).willReturn(new ProductBuilder().withName("nazwa").withPrice(BigDecimal.TEN).createProduct());
		given(productService.getProductById(2L)).willReturn(new ProductBuilder().withName("nazwa2").withPrice(BigDecimal.ONE).createProduct());
		controller.addRow(1L);
		controller.addRow(2L);
		assertThat(controller.getForm().getItems().size()).isEqualTo(2);
		assertThat(controller.getForm().getTotalPrice().stripTrailingZeros()).isEqualTo(BigDecimal.valueOf(11).stripTrailingZeros());

		controller.clearCart();
		assertThat(controller.getForm().getItems().size()).isEqualTo(0);
		assertThat(controller.getForm().getTotalPrice()).isEqualTo(BigDecimal.ZERO);
	}
}
