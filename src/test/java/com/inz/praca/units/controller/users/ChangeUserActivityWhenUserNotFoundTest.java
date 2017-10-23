package com.inz.praca.units.controller.users;

import com.inz.praca.registration.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChangeUserActivityWhenUserNotFoundTest extends UsersControllerTestBase {
    @Before
    public void returnEmptyUsersList() {
        given(userService.changeUserActive(UsersControllerTestBase.USER_ID, UsersControllerTestBase.USER_ACTIVITY_TRUE)).willThrow(new UserNotFoundException(1L));
    }

    @Test
    public void shouldThrowException() throws Exception {
        mockMvc.perform(get("/user/active/{activity}/{userId}", UsersControllerTestBase.USER_ACTIVITY_TRUE, UsersControllerTestBase.USER_ID))
                .andExpect(status().isNotFound());
    }
}
