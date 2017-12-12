package com.inz.praca.units.controller.users;

import static com.inz.praca.WebTestConstants.ModelAttributeName.PAGER;
import static com.inz.praca.WebTestConstants.ModelAttributeName.SELECTED_PAGE_SIZE;
import static com.inz.praca.WebTestConstants.ModelAttributeName.USERS_LIST;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.ArrayList;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.PageImpl;

public class UsersListWhenUsersNotFoundTest extends UsersControllerTestBase {

  @Before
  public void returnEmptyUsersList() {
    given(userService.getAllUsers(0)).willReturn(new PageImpl<>(new ArrayList<>()));
  }

  @Test
  public void shouldShowEmptyUsersList() throws Exception {
    mockMvc.perform(get("/users"))
        .andExpect(model().attribute(USERS_LIST, hasSize(0)))
        .andExpect(model().attribute(SELECTED_PAGE_SIZE, Matchers.equalTo(0)))
        .andExpect(model().attribute(PAGER, Matchers.notNullValue()));
  }
}
