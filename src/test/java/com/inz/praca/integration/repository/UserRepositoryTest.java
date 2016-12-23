package com.inz.praca.integration.repository;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.Optional;

import com.inz.praca.domain.User;
import com.inz.praca.domain.UserBuilder;
import com.inz.praca.integration.JpaTestBase;
import com.inz.praca.repository.UserRepository;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryTest extends JpaTestBase {
	private static final String EMAIL = "email@o2.pl";

	@Autowired
	private UserRepository repository;

	@Test
	public void shouldFindUserByEmail() {
		repository.deleteAll();
		this.entityManager.persist(new UserBuilder().withEmail(EMAIL).withPasswordHash("hash").build());
		Optional<User> byEmail = this.repository.findByEmail(EMAIL);
		assertThat(byEmail).isNotNull();
		assertThat(byEmail.isPresent()).isTrue();
		assertThat(byEmail.get().getEmail()).isEqualTo(EMAIL);
	}

	@Test
	public void shouldNotFindUserByEmail() {
		repository.deleteAll();
		this.entityManager.persist(new UserBuilder().withEmail(EMAIL).withPasswordHash("hash").build());
		Optional<User> byEmail = this.repository.findByEmail("innyEmail");
		assertThat(byEmail).isNotNull();
		assertThat(byEmail.isPresent()).isFalse();
	}

}
