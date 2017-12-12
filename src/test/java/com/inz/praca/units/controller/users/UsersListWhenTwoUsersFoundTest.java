package com.inz.praca.units.controller.users;

import static com.inz.praca.WebTestConstants.ModelAttributeName.PAGER;
import static com.inz.praca.WebTestConstants.ModelAttributeName.SELECTED_PAGE_SIZE;
import static com.inz.praca.WebTestConstants.ModelAttributeName.USERS_LIST;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import com.inz.praca.WebTestConstants.ModelAttributeProperty.USERS;
import com.inz.praca.registration.User;
import com.inz.praca.registration.UserBuilder;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.PageImpl;

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
