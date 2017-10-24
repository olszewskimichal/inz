package com.inz.praca.units.controller.order;

import com.inz.praca.cart.CartSession;
import com.inz.praca.integration.WebTestConfig;
import com.inz.praca.orders.OrderController;
import com.inz.praca.orders.OrderService;
import com.inz.praca.registration.CurrentUser;
import com.inz.praca.registration.UserBuilder;
import org.junit.Before;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.inz.praca.integration.WebTestConfig.exceptionResolver;
import static org.mockito.Mockito.mock;

public class OrderControllerTestBase {

    OrderService orderService;
    CartSession cartSession;
    CurrentUser user;
    MockMvc mockMvc;

    @Before
    public void configureSystemUnderTest() {
        orderService = mock(OrderService.class);
        cartSession = mock(CartSession.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new OrderController(cartSession, orderService))
                .setViewResolvers(WebTestConfig.viewResolver())
                .setHandlerExceptionResolvers(exceptionResolver())
                .build();

        user = new CurrentUser(new UserBuilder().withEmail("email@o2.pl").withPasswordHash("zaq1@WSX").build());
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

        SecurityContextHolder.getContext().setAuthentication(auth);
//
    }
}
