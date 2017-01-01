package com.inz.praca.units.dto;

import java.math.BigDecimal;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.dto.ProductDTO;

public class ProductDTOTest extends LocalEqualsHashCodeTest {


	@Override
	protected Object createInstance() throws Exception {
		return new ProductDTO(new ProductBuilder().withName("name").withPrice(BigDecimal.TEN).createProduct());
	}

	@Override
	protected Object createNotEqualInstance() throws Exception {
		return new ProductDTO(new ProductBuilder().withName("name2").withPrice(BigDecimal.TEN).createProduct());
	}

}