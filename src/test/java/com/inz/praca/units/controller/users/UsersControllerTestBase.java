package com.inz.praca.units.controller.users;

import com.inz.praca.integration.WebTestConfig;
import com.inz.praca.registration.UserService;
import com.inz.praca.registration.UsersController;
import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;

public abstract class UsersControllerTestBase {

    static final Long USER_ID = 1L;
    static final Boolean USER_ACTIVITY_TRUE = Boolean.TRUE;
    static final Boolean USER_ACTIVITY_FALSE = Boolean.FALSE;
    static final String USER_POSITIVE_ACTIVATION = "Aktywowano uzytkownika user";
    static final String USER_POSITIVE_DEACTIVATION = "Deaktywowano uzytkownika user";
    UserService userService;
    MockMvc mockMvc;

    @Before
    public void configureSystemUnderTest() {
        userService = mock(UserService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new UsersController(userService))
                .setViewResolvers(WebTestConfig.viewResolver())
                .build();
    }
}
