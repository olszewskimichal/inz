package com.inz.praca.units.service;

import com.inz.praca.login.CurrentUserDetailsService;
import com.inz.praca.registration.CurrentUser;
import com.inz.praca.registration.UserBuilder;
import com.inz.praca.registration.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class CurrentUserDetailServiceTest {
    private CurrentUserDetailsService currentUserDetailsService;

    @Mock
    private UserService userService;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.currentUserDetailsService = new CurrentUserDetailsService(userService);
    }

    @Test
    public void shouldGetCurrentUser() {
        given(userService.getUserByEmail("admin@email.pl")).willReturn(
                new UserBuilder().withEmail("admin@email.pl").withPasswordHash("zaq1@WSX").build());

        CurrentUser admin = currentUserDetailsService.loadUserByUsername("admin@email.pl");
        assertThat(admin).isNotNull();
        assertThat(admin.getUser().getEmail()).isEqualTo("admin@email.pl");
        assertThat(admin.getUsername()).isEqualTo("admin@email.pl");
        assertThat(admin.getAuthorities()).isNotEmpty();

    }
}
