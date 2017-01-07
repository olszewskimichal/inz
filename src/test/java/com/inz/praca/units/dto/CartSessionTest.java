package com.inz.praca.units.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.dto.CartSession;
import org.junit.Test;

public class CartSessionTest {

	@Test
	public void shouldAddProductTOCart(){
		CartSession cartSession = new CartSession();
		cartSession.addProductDTO(new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProductDTO());
		assertThat(cartSession.getTotalPrice()).isEqualTo(BigDecimal.TEN);
		assertThat(cartSession.getItems().size()).isEqualTo(1);
	}

	@Test
	public void shouldAdd2ProductTOCart(){
		CartSession cartSession = new CartSession();
		cartSession.addProductDTO(new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProductDTO());
		cartSession.addProductDTO(new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProductDTO());
		assertThat(cartSession.getTotalPrice()).isEqualTo(BigDecimal.valueOf(20));
		assertThat(cartSession.getItems().size()).isEqualTo(1);
	}

	@Test
	public void shouldAdd2DifferentProductTOCart(){
		CartSession cartSession = new CartSession();
		cartSession.addProductDTO(new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProductDTO());
		cartSession.addProductDTO(new ProductBuilder().withName("aaab").withPrice(BigDecimal.ONE).createProductDTO());
		assertThat(cartSession.getTotalPrice()).isEqualTo(BigDecimal.valueOf(11));
		assertThat(cartSession.getItems().size()).isEqualTo(2);
	}

	@Test
	public void shouldRemoveProductFromCart(){
		CartSession cartSession = new CartSession();
		cartSession.addProductDTO(new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProductDTO());
		cartSession.addProductDTO(new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProductDTO());
		assertThat(cartSession.getTotalPrice()).isEqualTo(BigDecimal.valueOf(20));
		assertThat(cartSession.getItems().size()).isEqualTo(1);
		cartSession.removeProductDTO(0);
		assertThat(cartSession.getItems().size()).isEqualTo(0);
		assertThat(cartSession.getTotalPrice()).isEqualTo(BigDecimal.ZERO);
	}

	@Test
	public void shouldClearCart(){
		CartSession cartSession = new CartSession();
		cartSession.addProductDTO(new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProductDTO());
		cartSession.addProductDTO(new ProductBuilder().withName("aaab").withPrice(BigDecimal.TEN).createProductDTO());
		assertThat(cartSession.getTotalPrice()).isEqualTo(BigDecimal.valueOf(20));
		assertThat(cartSession.getItems().size()).isEqualTo(2);
		cartSession.clearCart();
		assertThat(cartSession.getItems().size()).isEqualTo(0);
		assertThat(cartSession.getTotalPrice()).isEqualTo(BigDecimal.ZERO);
	}
}
