package com.inz.praca.units.domain;

import com.inz.praca.domain.User;
import org.junit.Assert;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class UserEntityTest {

    @Test
    public void shouldThrownExceptionWhenEmailIsNull() {
        try {
            new User(null, "imie", "nazwisko", "hashZHaslem");
            Assert.fail("Nie mozna stworzyc uzytkownika z pustym emailem");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Email nie moze być pusty");
        }
    }

    @Test
    public void shouldThrownExceptionWhenEmailIsEmpty() {
        try {
            new User("", "imie", "nazwisko", "hashZHaslem");
            Assert.fail("Nie mozna stworzyc uzytkownika z pustym emailem");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Email nie moze być pusty");
        }
    }

    @Test
    public void shouldThrownExceptionWhenNameIsEmpty() {
        try {
            new User("email@o2.pl", "", "nazwisko", "hashZHaslem");
            Assert.fail("Nie mozna stworzyc uzytkownika z pustym imieniem");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Imie nie może być puste");
        }
    }

    @Test
    public void shouldThrownExceptionWhenLastNameIsEmpty() {
        try {
            new User("email@o2.pl", "imie", "", "hashZHaslem");
            Assert.fail("Nie mozna stworzyc uzytkownika z pustym nazwiskiem");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Nazwisko nie może być puste");
        }
    }

    @Test
    public void shouldThrownExceptionWhenPasswordHashIsEmpty() {
        try {
            new User("email@o2.pl", "imie", "nazwisko", "");
            Assert.fail("Nie mozna stworzyc uzytkownika z pustym hasłem");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Nie może być puste hasło");
        }
    }

    @Test
    public void shouldCreateProductWhenAllValuesAreCorrect() {
        try {
            new User("nazwa@o2.pl", "imie", "nazwisko", "hash");
        } catch (IllegalArgumentException e) {
            Assert.fail("Nie moze wystapić wyjatek przy prawidłowej deklaracji obiektu");
        }
    }
}
