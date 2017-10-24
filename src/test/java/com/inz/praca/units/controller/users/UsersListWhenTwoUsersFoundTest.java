package com.inz.praca.units.controller.users;

import com.inz.praca.WebTestConstants.ModelAttributeProperty.USERS;
import com.inz.praca.registration.User;
import com.inz.praca.registration.UserBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;

import static com.inz.praca.WebTestConstants.ModelAttributeName.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

public class UsersListWhenTwoUsersFoundTest extends UsersControllerTestBase {
    private User first;
    private User second;

    @Before
    public void returnTwoUsers() {
        first = new UserBuilder().withEmail("email@o2.pl").withPasswordHash("zaq1@WSX").build();
        second = new UserBuilder().withEmail("email2@o2.pl").withPasswordHash("zaq1@WSX").build();
        given(userService.getAllUsers(0)).willReturn(new PageImpl<>(Arrays.asList(first, second)));
    }

    @Test
    public void shouldShowUsersListThatHasTwoUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(model().attribute(USERS_LIST, hasSize(2)))
                .andExpect(model().attribute(SELECTED_PAGE_SIZE, equalTo(0)))
                .andExpect(model().attribute(PAGER, notNullValue()));  //equalsTO
    }

    @Test
    public void shouldShowTwoUsersInCorrectOrder() throws Exception {
        mockMvc.perform(post("/users"))
                .andExpect(model().attribute(USERS_LIST, contains(first, second)));
    }

    @Test
    public void shouldShowCorrectInformationAboutUsers() throws Exception {
        mockMvc.perform(post("/users"))
                .andExpect(model().attribute(USERS_LIST, allOf(
                        hasItem(allOf(
                                hasProperty(USERS.EMAIL, is("email@o2.pl")),
                                hasProperty(USERS.NAME, is("imie")),
                                hasProperty(USERS.LAST_NAME, is("nazwisko"))
                        )),
                        hasItem(allOf(
                                hasProperty(USERS.EMAIL, is("email@o2.pl")),
                                hasProperty(USERS.NAME, is("imie")),
                                hasProperty(USERS.LAST_NAME, is("nazwisko"))
                        ))
                )));
    }
}
