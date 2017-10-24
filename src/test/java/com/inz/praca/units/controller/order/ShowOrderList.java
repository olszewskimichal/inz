package com.inz.praca.units.controller.order;

import com.inz.praca.WebTestConstants.View;
import org.junit.Test;
import org.mockito.Mockito;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ShowOrderList extends OrderControllerTestBase {

    @Test
    public void shouldReturnHttpStatusCodeOk() throws Exception {
        mockMvc.perform(get("/orderList"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRenderOrderListView() throws Exception {
        mockMvc.perform(get("/orderList"))
                .andExpect(view().name(View.ORDER_LIST));
    }
}
