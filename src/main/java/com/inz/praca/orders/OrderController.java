package com.inz.praca.orders;

import com.inz.praca.cart.CartSession;
import com.inz.praca.registration.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final CartSession cartSession;

    public OrderController(CartSession cartSession, OrderService orderService) {
        this.cartSession = cartSession;
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public String getShippingDetail(Model model, RedirectAttributes redirectAttributes) {
        if (this.cartSession.getItems().isEmpty()) {
            redirectAttributes.addFlashAttribute("emptyCart", true);
            return "redirect:/cart";
        }
        model.addAttribute(new ShippingDetail());
        OrderController.log.info("getShippingDetail");
        return "collectCustomerInfo";
    }

    @PostMapping("/order")
    public String postShippingDetail(@Valid @ModelAttribute ShippingDetail detail, Model model) {
        OrderController.log.info("postShipping" + detail);
        OrderDTO orderDTO = this.orderService.confirmShippingDetail(detail);
        model.addAttribute(orderDTO);
        return "orderConfirmation";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/orderList")
    public String getOrderList(Model model) {
        CurrentUser user = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("orders", user.getUser().getOrders());
        return "orderList";
    }

}
