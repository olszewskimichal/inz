package com.inz.praca.units.domain;

import com.inz.praca.domain.Product;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ProductEntityTest {
    @Test
    public void shouldThrownExceptionWhenNameIsEmpty() {
        try {
            new Product("", "", "", BigDecimal.ONE);
            Assert.fail("Nie mozna stworzyc produktu bez nazwy");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Nie moze być pusta nazwa produktu");
        }
    }

    @Test
    public void shouldThrownExceptionWhenNameIsNull() {
        try {
            new Product(null, "", "", BigDecimal.ONE);
            Assert.fail("Nie mozna stworzyc produktu bez nazwy");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Nie moze być pusta nazwa produktu");
        }
    }

    @Test
    public void shouldThrownExceptionWhenPriceIsNull() {
        try {
            new Product("Nazwa", "", "", null);
            Assert.fail("Nie mozna stworzyc produktu bez ceny");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Cena nie moze byc nullem");
        }
    }

    @Test
    public void shouldThrownExceptionWhenPriceIsNotPositiveNumber() {
        try {
            new Product("Nazwa", "", "", BigDecimal.valueOf(-1L));
            Assert.fail("Nie mozna stworzyc produktu z ujemna cena");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Cena nie moze być mniejsza od 0");
        }
    }

    @Test
    public void shouldCreateProductWhenAllValuesAreCorrect() {
        try {
            new Product("nazwa", "opis", "url", BigDecimal.TEN);
        } catch (IllegalArgumentException e) {
            Assert.fail("Nie moze wystapić wyjatek przy prawidłowej deklaracji obiektu");
        }
    }
}
