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
		return cartSession;
	}

	@GetMapping("/cart")
	public String getSessionCart(Model model) {
		model.addAttribute(cartSession);
		log.info(cartSession.toString());
		return CART;
	}

	@GetMapping(value = "/cart/add/{productId}")
	public String addProductToCart(Model model, @PathVariable Long productId) {
		log.info("addProduct id {} cart {}", productId, cartSession);
		cartSession.addProduct(new ProductDTO(productService.getProductById(productId)));
		model.addAttribute(getForm());
		return REDIRECT_CART;
	}

	@GetMapping(value = "/cart/remove/{rowId}")
	public String removeProductFromCart(@PathVariable Integer rowId) {
		log.info("removeProduct rowId {} z {} ", rowId, cartSession);
		cartSession.removeProductById(rowId);
		return REDIRECT_CART;
	}

	@GetMapping(value = "/cart/clear")
	public String clearCart(Model model) {
		log.info(cartSession.toString());
		log.info("Czyszczenie koszyka");
		cartSession.clearCart();
		model.addAttribute(getForm());
		return REDIRECT_CART;
	}

}
