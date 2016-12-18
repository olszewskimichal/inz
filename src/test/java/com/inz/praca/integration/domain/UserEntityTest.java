package com.inz.praca.integration.domain;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.inz.praca.domain.User;
import com.inz.praca.integration.JpaTestBase;
import org.junit.Test;

public class UserEntityTest extends JpaTestBase {
	@Test
	public void shouldPersistUserWhenObjectIsCorrect() throws Exception {
		User user = this.entityManager.persistFlushFind(new User("prawidlowyEmail@o2.pl", "imie", "nazwisko", "hash"));
		assertThat(user.getEmail()).isEqualTo("prawidlowyEmail@o2.pl");
		assertThat(user.getName()).isEqualTo("imie");
		assertThat(user.getLastName()).isEqualTo("nazwisko");
		assertThat(user.getPasswordHash()).isEqualTo("hash");
		assertThat(user.getId()).isNotNull();
		assertThat(user.toString()).isNotNull().isNotEmpty().contains("imie");
	}
}
