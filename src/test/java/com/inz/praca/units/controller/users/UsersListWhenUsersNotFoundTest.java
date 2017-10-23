package com.inz.praca.units.controller.users;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;

import static com.inz.praca.WebTestConstants.ModelAttributeName.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

public class UsersListWhenUsersNotFoundTest extends UsersControllerTestBase {
    @Before
    public void returnEmptyUsersList() {
        given(this.userService.getAllUsers(0)).willReturn(new PageImpl<>(new ArrayList<>()));
    }

    @Test
    public void shouldShowEmptyUsersList() throws Exception {
        this.mockMvc.perform(get("/users"))
                .andExpect(model().attribute(USERS_LIST, hasSize(0)))
                .andExpect(model().attribute(SELECTED_PAGE_SIZE, Matchers.equalTo(0)))
                .andExpect(model().attribute(PAGER, Matchers.notNullValue()));
    }
}
