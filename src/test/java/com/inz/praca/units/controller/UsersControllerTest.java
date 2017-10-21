package com.inz.praca.units.controller;

import com.inz.praca.UnitTest;
import com.inz.praca.WebTestConstants;
import com.inz.praca.integration.WebTestConfig;
import com.inz.praca.registration.*;
import de.bechte.junit.runners.context.HierarchicalContextRunner;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static com.inz.praca.WebTestConstants.ModelAttributeName.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Category(UnitTest.class)
@RunWith(HierarchicalContextRunner.class)
public class UsersControllerTest {

    private UserService userService;
    private MockMvc mockMvc;

    private static final Long USER_ID = 1L;
    private static final Boolean USER_ACTIVITY_TRUE = Boolean.TRUE;
    private static final Boolean USER_ACTIVITY_FALSE = Boolean.FALSE;
    private static final String USER_POSITIVE_ACTIVATION = "Aktywowano uzytkownika user";
    private static final String USER_POSITIVE_DEACTIVATION = "Deaktywowano uzytkownika user";


    @Before
    public void configureSystemUnderTest() {
        userService = mock(UserService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new UsersController(userService))
                .setViewResolvers(WebTestConfig.viewResolver())
                .build();
    }

    public class UsersList {

        @Test
        public void shouldReturnHttpStatusCodeOk() throws Exception {
            mockMvc.perform(get("/users"))
                    .andExpect(status().isOk());
        }

        @Test
        public void shouldRenderUsersListView() throws Exception {
            mockMvc.perform(get("/users"))
                    .andExpect(view().name(WebTestConstants.View.USERS));
        }

        public class WhenUsersIsNotFound {

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

        public class WhenTwoUsersAreFound {

            User first;
            User second;

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
                        .andExpect(model().attribute(SELECTED_PAGE_SIZE, Matchers.equalTo(0)))
                        .andExpect(model().attribute(PAGER, Matchers.notNullValue()));  //equalsTO
            }

            @Test
            public void shouldShowTwoUsersInCorrectOrder() throws Exception {
                mockMvc.perform(post("/users"))
                        .andExpect(model().attribute(WebTestConstants.ModelAttributeName.USERS_LIST, contains(first, second)));
            }

            @Test
            public void shouldShowCorrectInformationAboutUsers() throws Exception {
                mockMvc.perform(post("/users"))
                        .andExpect(model().attribute(WebTestConstants.ModelAttributeName.USERS_LIST, allOf(
                                hasItem(allOf(
                                        hasProperty(WebTestConstants.ModelAttributeProperty.USERS.EMAIL, is("email@o2.pl")),
                                        hasProperty(WebTestConstants.ModelAttributeProperty.USERS.NAME, is("imie")),
                                        hasProperty(WebTestConstants.ModelAttributeProperty.USERS.LAST_NAME, is("nazwisko"))
                                )),
                                hasItem(allOf(
                                        hasProperty(WebTestConstants.ModelAttributeProperty.USERS.EMAIL, is("email@o2.pl")),
                                        hasProperty(WebTestConstants.ModelAttributeProperty.USERS.NAME, is("imie")),
                                        hasProperty(WebTestConstants.ModelAttributeProperty.USERS.LAST_NAME, is("nazwisko"))
                                ))
                        )));
            }
        }

        public class WhenMoreThenTwentyUsersAreFound {

            List<User> firstPageList = new ArrayList<>();
            List<User> secondPageList = new ArrayList<>();

            @Before
            public void returnMoreThen20Users() {
                IntStream.range(0, 20).forEachOrdered(v -> {
                    firstPageList.add(new UserBuilder().withEmail(String.format("email%s@o2.pl", v)).withPasswordHash("zaq1@WSX").build());
                });
                IntStream.range(0, 3).forEachOrdered(v -> {
                    secondPageList.add(new UserBuilder().withEmail(String.format("email%s@o2.pl", v)).withPasswordHash("zaq1@WSX").build());
                });
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
                        param(WebTestConstants.ModelAttributeProperty.USERS.PAGE, "1"))
                        .andExpect(model().attribute(USERS_LIST, hasSize(20)))
                        .andExpect(model().attribute(SELECTED_PAGE_SIZE, Matchers.equalTo(0)))
                        .andExpect(model().attribute(PAGER, Matchers.notNullValue()));  //equalsTO
            }

            @Test
            public void shouldShowUsersListFromSecondPageWhenPageParamIsSet() throws Exception {
                mockMvc.perform(get("/users").
                        param(WebTestConstants.ModelAttributeProperty.USERS.PAGE, "2"))
                        .andExpect(model().attribute(USERS_LIST, hasSize(3)))
                        .andExpect(model().attribute(SELECTED_PAGE_SIZE, Matchers.equalTo(1)))
                        .andExpect(model().attribute(PAGER, Matchers.notNullValue()));  //equalsTO
            }

        }

    }

    public class ChangeUserActivity {

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

        public class UserNotFound {
            @Before
            public void returnEmptyUsersList() {
                given(userService.changeUserActive(USER_ID, USER_ACTIVITY_TRUE)).willThrow(new UserNotFoundException(1L));
            }

            @Test
            public void shouldThrowException() throws Exception {
                mockMvc.perform(get("/user/active/{activity}/{userId}", USER_ACTIVITY_TRUE, USER_ID))
                        .andExpect(status().isNotFound());
            }
        }

        public class ActiveUser {

            @Before
            public void returnEmptyUsersList() {
                given(userService.changeUserActive(USER_ID, USER_ACTIVITY_TRUE)).willReturn(USER_POSITIVE_ACTIVATION);
            }

            @Test
            public void shouldActivateUser() throws Exception {
                mockMvc.perform(get("/user/active/{activity}/{userId}", USER_ACTIVITY_TRUE, USER_ID))
                        .andExpect(flash().attribute("activate", true))
                        .andExpect(flash().attribute("activateMessage", USER_POSITIVE_ACTIVATION));
            }

        }

        public class DeactivateUser {

            @Before
            public void returnEmptyUsersList() {
                given(userService.changeUserActive(USER_ID, USER_ACTIVITY_FALSE)).willReturn(USER_POSITIVE_DEACTIVATION);
            }

            @Test
            public void shouldDeactivateUser() throws Exception {
                mockMvc.perform(get("/user/active/{activity}/{userId}", USER_ACTIVITY_FALSE, USER_ID))
                        .andExpect(flash().attribute("activate", true))
                        .andExpect(flash().attribute("activateMessage", USER_POSITIVE_DEACTIVATION));
            }

        }
    }
}
