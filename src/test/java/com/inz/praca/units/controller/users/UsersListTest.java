package com.inz.praca.units.controller.users;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.inz.praca.WebTestConstants.View;
import org.junit.Test;

public class UsersListTest extends UsersControllerTestBase {

  @Test
  public void shouldReturnHttpStatusCodeOk() throws Exception {
    mockMvc.perform(get("/users"))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldRenderUsersListView() throws Exception {
    mockMvc.perform(get("/users"))
        .andExpect(view().name(View.USERS));
  }
}
