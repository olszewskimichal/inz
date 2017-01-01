package com.inz.praca.units.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.dto.CartDTO;
import org.junit.Test;

public class CartDTOTest {

	@Test
	public void shouldAddProductTOCart(){
		CartDTO cartDTO = new CartDTO();
		cartDTO.addProductDTO(new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProductDTO());
		assertThat(cartDTO.getTotalPrice()).isEqualTo(BigDecimal.TEN);
		assertThat(cartDTO.getItems().size()).isEqualTo(1);
	}

	@Test
	public void shouldAdd2ProductTOCart(){
		CartDTO cartDTO = new CartDTO();
		cartDTO.addProductDTO(new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProductDTO());
		cartDTO.addProductDTO(new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProductDTO());
		assertThat(cartDTO.getTotalPrice()).isEqualTo(BigDecimal.valueOf(20));
		assertThat(cartDTO.getItems().size()).isEqualTo(1);
	}

	@Test
	public void shouldAdd2DifferentProductTOCart(){
		CartDTO cartDTO = new CartDTO();
		cartDTO.addProductDTO(new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProductDTO());
		cartDTO.addProductDTO(new ProductBuilder().withName("aaab").withPrice(BigDecimal.ONE).createProductDTO());
		assertThat(cartDTO.getTotalPrice()).isEqualTo(BigDecimal.valueOf(11));
		assertThat(cartDTO.getItems().size()).isEqualTo(2);
	}

	@Test
	public void shouldRemoveProductFromCart(){
		CartDTO cartDTO = new CartDTO();
		cartDTO.addProductDTO(new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProductDTO());
		cartDTO.addProductDTO(new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProductDTO());
		assertThat(cartDTO.getTotalPrice()).isEqualTo(BigDecimal.valueOf(20));
		assertThat(cartDTO.getItems().size()).isEqualTo(1);
		cartDTO.removeProductDTO(0);
		assertThat(cartDTO.getItems().size()).isEqualTo(0);
		assertThat(cartDTO.getTotalPrice()).isEqualTo(BigDecimal.ZERO);
	}
}
