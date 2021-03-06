package com.inz.praca.units.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.inz.praca.UnitTest;
import com.inz.praca.registration.UserBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;


@Category(UnitTest.class)
public class UserEntityTest {

  @Test
  public void shouldThrownExceptionWhenEmailIsNull() {
    try {
      new UserBuilder().withEmail(null).withPasswordHash("hashZHaslem").build();
      Assert.fail("Nie mozna stworzyc uzytkownika z pustym emailem");
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("Email nie może być pusty");
    }
  }

  @Test
  public void shouldThrownExceptionWhenEmailIsNotCorrect() {
    try {
      new UserBuilder().withEmail("email").withPasswordHash("hashZHaslem").build();
      Assert.fail("Nie mozna stworzyc uzytkownika z pustym emailem");
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("To nie jest prawidłowy adres Email");
    }
  }

  @Test
  public void shouldThrownExceptionWhenEmailIsEmpty() {
    try {
      new UserBuilder().withEmail("").withPasswordHash("hashZHaslem").build();
      Assert.fail("Nie mozna stworzyc uzytkownika z pustym emailem");
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("To nie jest prawidłowy adres Email");
    }
  }

  @Test
  public void shouldThrownExceptionWhenNameIsEmpty() {
    try {
      new UserBuilder().withEmail("email@o2.pl").withName("").withPasswordHash("hashZHaslem").build();
      Assert.fail("Nie mozna stworzyc uzytkownika z pustym imieniem");
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("Imie nie może być puste");
    }
  }

  @Test
  public void shouldThrownExceptionWhenLastNameIsEmpty() {
    try {
      new UserBuilder().withEmail("email@o2.pl").withLastName("").withPasswordHash("hashZHaslem").build();
      Assert.fail("Nie mozna stworzyc uzytkownika z pustym nazwiskiem");
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("Nazwisko nie może być puste");
    }
  }

  @Test
  public void shouldThrownExceptionWhenPasswordHashIsEmpty() {
    try {
      new UserBuilder().withEmail("email@o2.pl").withPasswordHash("").build();
      Assert.fail("Nie mozna stworzyc uzytkownika z pustym hasłem");
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("Nie może być puste hasło");
    }
  }

  @Test
  public void shouldThrownExceptionWhenPasswordIsNotCorrect() {
    try {
      new UserBuilder().withEmail("email@o2.pl").withPasswordHash("a").build();
      Assert.fail("Nie mozna stworzyc uzytkownika z tak prostym haslem");
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("Podane hasło jest zbyt proste");
    }
  }

  @Test
  public void shouldCreateUserWhenAllValuesAreCorrect() {
    try {
      new UserBuilder().withEmail("nazwa@o2.pl").withPasswordHash("zaq1@WSX").build();
    } catch (IllegalArgumentException e) {
      Assert.fail("Nie moze wystapić wyjatek przy prawidłowej deklaracji obiektu");
    }
  }

}
