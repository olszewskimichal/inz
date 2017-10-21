package com.inz.praca.units.controller.users;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

public class ChangeUserActivityToActiveTest extends UsersControllerTestBase {
    @Before
    public void returnUsersList() {
        given(userService.changeUserActive(USER_ID, USER_ACTIVITY_TRUE)).willReturn(USER_POSITIVE_ACTIVATION);
    }

    @Test
    public void shouldActivateUser() throws Exception {
        mockMvc.perform(get("/user/active/{activity}/{userId}", USER_ACTIVITY_TRUE, USER_ID))
                .andExpect(flash().attribute("activate", true))
                .andExpect(flash().attribute("activateMessage", USER_POSITIVE_ACTIVATION));
    }
}
