package com.inz.praca.units.controller.users;

import com.inz.praca.WebTestConstants;
import com.inz.praca.WebTestConstants.View;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class UsersListTest extends UsersControllerTestBase {
    @Test
    public void shouldReturnHttpStatusCodeOk() throws Exception {
        this.mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRenderUsersListView() throws Exception {
        this.mockMvc.perform(get("/users"))
                .andExpect(view().name(View.USERS));
    }
}
