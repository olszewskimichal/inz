package com.inz.praca.units.controller.users;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.inz.praca.WebTestConstants.RedirectView;
import org.junit.Test;

public class ChangeUserActivityTest extends UsersControllerTestBase {

  @Test
  public void shouldReturnHttpStatusCodeOk() throws Exception {
    mockMvc.perform(get("/user/active/{activity}/{userId}", UsersControllerTestBase.USER_ACTIVITY_TRUE, UsersControllerTestBase.USER_ID))
        .andExpect(status().is3xxRedirection());
  }

  @Test
  public void shouldRenderUsersListView() throws Exception {
    mockMvc.perform(get("/user/active/{activity}/{userId}", UsersControllerTestBase.USER_ACTIVITY_TRUE, UsersControllerTestBase.USER_ID))
        .andExpect(view().name(RedirectView.USERS));
  }
}
