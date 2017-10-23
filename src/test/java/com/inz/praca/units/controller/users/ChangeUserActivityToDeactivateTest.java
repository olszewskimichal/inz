package com.inz.praca.units.controller.users;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

public class ChangeUserActivityToDeactivateTest extends UsersControllerTestBase {
    @Before
    public void returnUsersList() {
        given(userService.changeUserActive(UsersControllerTestBase.USER_ID, UsersControllerTestBase.USER_ACTIVITY_FALSE)).willReturn(UsersControllerTestBase.USER_POSITIVE_DEACTIVATION);
    }

    @Test
    public void shouldDeactivateUser() throws Exception {
        mockMvc.perform(get("/user/active/{activity}/{userId}", UsersControllerTestBase.USER_ACTIVITY_FALSE, UsersControllerTestBase.USER_ID))
                .andExpect(flash().attribute("activate", true))
                .andExpect(flash().attribute("activateMessage", UsersControllerTestBase.USER_POSITIVE_DEACTIVATION));
    }
}
