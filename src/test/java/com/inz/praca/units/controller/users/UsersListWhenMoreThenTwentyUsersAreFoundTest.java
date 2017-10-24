package com.inz.praca.units.controller.users;

import com.inz.praca.WebTestConstants.ModelAttributeProperty.USERS;
import com.inz.praca.registration.User;
import com.inz.praca.registration.UserBuilder;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.inz.praca.WebTestConstants.ModelAttributeName.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

public class UsersListWhenMoreThenTwentyUsersAreFoundTest extends UsersControllerTestBase {
    private final List<User> firstPageList = new ArrayList<>();
    private final List<User> secondPageList = new ArrayList<>();

    @Before
    public void returnMoreThen20Users() {
        IntStream.range(0, 20).forEachOrdered(v -> firstPageList.add(new UserBuilder().withEmail(String.format("email%s@o2.pl", v)).withPasswordHash("zaq1@WSX").build()));
        IntStream.range(0, 3).forEachOrdered(v -> secondPageList.add(new UserBuilder().withEmail(String.format("email%s@o2.pl", v)).withPasswordHash("zaq1@WSX").build()));
        given(userService.getAllUsers(0)).willReturn(new PageImpl<>(firstPageList));
        given(userService.getAllUsers(1)).willReturn(new PageImpl<>(secondPageList));
    }

    @Test
    public void shouldShowUsersListFromFirstPageWhenPageParamIsNotSet() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(model().attribute(USERS_LIST, hasSize(20)))
                .andExpect(model().attribute(SELECTED_PAGE_SIZE, Matchers.equalTo(0)))
                .andExpect(model().attribute(PAGER, Matchers.notNullValue()));  //equalsTO
    }

    @Test
    public void shouldShowUsersListFromFirstPageWhenPageParamIsSet() throws Exception {
        mockMvc.perform(get("/users").
                param(USERS.PAGE, "1"))
                .andExpect(model().attribute(USERS_LIST, hasSize(20)))
                .andExpect(model().attribute(SELECTED_PAGE_SIZE, Matchers.equalTo(0)))
                .andExpect(model().attribute(PAGER, Matchers.notNullValue()));  //equalsTO
    }

    @Test
    public void shouldShowUsersListFromSecondPageWhenPageParamIsSet() throws Exception {
        mockMvc.perform(get("/users").
                param(USERS.PAGE, "2"))
                .andExpect(model().attribute(USERS_LIST, hasSize(3)))
                .andExpect(model().attribute(SELECTED_PAGE_SIZE, Matchers.equalTo(1)))
                .andExpect(model().attribute(PAGER, Matchers.notNullValue()));  //equalsTO
    }
}
