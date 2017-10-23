package com.inz.praca.cart;

import com.inz.praca.products.ProductDTO;
import com.inz.praca.products.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
public class CartController {
    private static final String CART = "cart";
    private static final String REDIRECT_CART = "redirect:/cart";

    private final ProductService productService;
    private final CartSession cartSession;

    @Autowired
    public CartController(ProductService productService, CartSession cartSession) {
        this.productService = productService;
        this.cartSession = cartSession;
    }

    public CartSession getForm() {
        return this.cartSession;
    }

    @GetMapping("/cart")
    public String getSessionCart(Model model) {
        model.addAttribute(this.cartSession);
        CartController.log.info(this.cartSession.toString());
        return CartController.CART;
    }

    @GetMapping("/cart/add/{productId}")
    public String addProductToCart(Model model, @PathVariable Long productId) {
        CartController.log.info("addProduct id {} cart {}", productId, this.cartSession);
        this.cartSession.addProduct(new ProductDTO(this.productService.getProductById(productId)));
        model.addAttribute(this.getForm());
        return CartController.REDIRECT_CART;
    }

    @GetMapping("/cart/remove/{rowId}")
    public String removeProductFromCart(@PathVariable Integer rowId) {
        CartController.log.info("removeProduct rowId {} z {} ", rowId, this.cartSession);
        this.cartSession.removeProductById(rowId);
        return CartController.REDIRECT_CART;
    }

    @GetMapping("/cart/clear")
    public String clearCart(Model model) {
        CartController.log.info("Czyszczenie koszyka");
        this.cartSession.clearCart();
        model.addAttribute(this.getForm());
        return CartController.REDIRECT_CART;
    }

}
