package com.inz.praca.units.controller.users;

import com.inz.praca.WebTestConstants;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ChangeUserActivityTest extends UsersControllerTestBase {
    @Test
    public void shouldReturnHttpStatusCodeOk() throws Exception {
        mockMvc.perform(get("/user/active/{activity}/{userId}", USER_ACTIVITY_TRUE, USER_ID))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void shouldRenderUsersListView() throws Exception {
        mockMvc.perform(get("/user/active/{activity}/{userId}", USER_ACTIVITY_TRUE, USER_ID))
                .andExpect(view().name(WebTestConstants.RedirectView.USERS));
    }
}
