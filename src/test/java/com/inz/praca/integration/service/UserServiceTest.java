package com.inz.praca.integration.service;

import com.inz.praca.integration.IntegrationTestBase;
import com.inz.praca.registration.User;
import com.inz.praca.registration.UserDTO;
import com.inz.praca.registration.UserRepository;
import com.inz.praca.registration.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;


public class UserServiceTest extends IntegrationTestBase {

    @Autowired
    protected UserRepository repository;
    @Autowired
    UserService userService;

    @Before
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    public void shouldCreateUserWhenDtoIsCorrectButWhenAgainThenFailed() {
        assertThat(repository.findAll().size()).isEqualTo(0);

        //given
        UserDTO userDTO = new UserDTO();
        userDTO.setName("name");   //UserDTO ->Builder
        userDTO.setLastName("lastName");
        userDTO.setEmail("email@o2.pl");
        userDTO.setPassword("zaq1@WSX");

        //when
        User user = userService.createUserFromDTO(userDTO);

        //then
        assertThat(repository.findAll().size()).isEqualTo(1);
        assertThat(user.getEmail()).isEqualTo("email@o2.pl");
        assertThat(new BCryptPasswordEncoder().matches("zaq1@WSX", user.getPasswordHash())).isTrue();

        try {
            userService.createUserFromDTO(userDTO);
            Assert.fail();
        } catch (DataIntegrityViolationException e) {
            assertThat(repository.findAll().size()).isEqualTo(1);
        }
    }

}
