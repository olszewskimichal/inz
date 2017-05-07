package com.inz.praca.integration.repository;

import com.inz.praca.integration.JpaTestBase;
import com.inz.praca.registration.User;
import com.inz.praca.registration.UserBuilder;
import com.inz.praca.registration.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest extends JpaTestBase {
    private static final String EMAIL = "email_a@o2.pl";

    @Autowired
    private UserRepository repository;

    @Test
    public void shouldFindUserByEmail() {
        repository.deleteAll();
        this.entityManager.persistFlushFind(new UserBuilder().withEmail(EMAIL).withPasswordHash("zaq1@WSX").build());
        Optional<User> byEmail = this.repository.findByEmail(EMAIL);
        assertThat(byEmail).isNotNull();
        assertThat(byEmail.isPresent()).isTrue();
        assertThat(byEmail.get().getEmail()).isEqualTo(EMAIL);
    }

}
