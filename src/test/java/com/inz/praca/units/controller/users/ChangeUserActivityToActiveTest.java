package com.inz.praca.units.controller.users;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

public class ChangeUserActivityToActiveTest extends UsersControllerTestBase {
    @Before
    public void returnUsersList() {
        given(userService.changeUserActive(UsersControllerTestBase.USER_ID, UsersControllerTestBase.USER_ACTIVITY_TRUE)).willReturn(UsersControllerTestBase.USER_POSITIVE_ACTIVATION);
    }

    @Test
    public void shouldActivateUser() throws Exception {
        mockMvc.perform(get("/user/active/{activity}/{userId}", UsersControllerTestBase.USER_ACTIVITY_TRUE, UsersControllerTestBase.USER_ID))
                .andExpect(flash().attribute("activate", true))
                .andExpect(flash().attribute("activateMessage", UsersControllerTestBase.USER_POSITIVE_ACTIVATION));
    }
}
