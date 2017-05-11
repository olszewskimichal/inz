package com.inz.praca.units.domain;

import com.inz.praca.UnitTest;
import com.inz.praca.products.ProductBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class ProductEntityTest {
    @Test
    public void shouldThrownExceptionWhenNameIsEmpty() {
        try {
            new ProductBuilder().withName("").withDescription("").withUrl("").withPrice(BigDecimal.ONE).createProduct();
            Assert.fail("Nie mozna stworzyc produktu bez nazwy");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Nie moze być pusta nazwa produktu");
        }
    }

    @Test
    public void shouldThrownExceptionWhenNameIsNull() {
        try {
            new ProductBuilder().withName(null)
                    .withDescription("")
                    .withUrl("")
                    .withPrice(BigDecimal.ONE)
                    .createProduct();
            Assert.fail("Nie mozna stworzyc produktu bez nazwy");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Nie moze być pusta nazwa produktu");
        }
    }

    @Test
    public void shouldThrownExceptionWhenPriceIsNull() {
        try {
            new ProductBuilder().withName("Nazwa").withDescription("").withUrl("").withPrice(null).createProduct();
            Assert.fail("Nie mozna stworzyc produktu bez ceny");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Cena nie moze byc nullem");
        }
    }

    @Test
    public void shouldThrownExceptionWhenPriceIsNotPositiveNumber() {
        try {
            new ProductBuilder().withName("Nazwa")
                    .withDescription("")
                    .withUrl("")
                    .withPrice(BigDecimal.valueOf(-1L))
                    .createProduct();
            Assert.fail("Nie mozna stworzyc produktu z ujemna cena");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Cena nie moze być mniejsza od 0");
        }
    }

    @Test
    public void shouldThrownExceptionWhenPriceIsNotCorrectNumber() {
        try {
            new ProductBuilder().withName("Nazwa")
                    .withDescription("14,4a")
                    .withUrl("")
                    .withPrice(BigDecimal.valueOf(-1L))
                    .createProduct();
            Assert.fail("To nie jest liczba");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Cena nie moze być mniejsza od 0");
        }
    }

    @Test
    public void shouldCreateProductWhenAllValuesAreCorrect() {
        try {
            new ProductBuilder().withName("nazwa")
                    .withDescription("opis")
                    .withUrl("url")
                    .withPrice(BigDecimal.TEN)
                    .createProduct();
        } catch (IllegalArgumentException e) {
            Assert.fail("Nie moze wystapić wyjatek przy prawidłowej deklaracji obiektu");
        }
    }
}
