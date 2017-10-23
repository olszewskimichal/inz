package com.inz.praca.units.dto;

import com.inz.praca.UnitTest;
import com.inz.praca.products.ProductBuilder;
import com.inz.praca.products.ProductDTO;
import org.junit.experimental.categories.Category;

import java.math.BigDecimal;

@Category(UnitTest.class)
public class ProductDTOTest extends LocalEqualsHashCodeTest {


    @Override
    protected Object createInstance() {
        return new ProductDTO(new ProductBuilder().withName("name").withPrice(BigDecimal.TEN).createProduct());
    }

    @Override
    protected Object createNotEqualInstance() {
        return new ProductDTO(new ProductBuilder().withName("name2").withPrice(BigDecimal.TEN).createProduct());
    }

}
