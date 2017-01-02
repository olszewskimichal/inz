package com.inz.praca.controller;

import com.inz.praca.dto.CartDTO;
import com.inz.praca.dto.ProductDTO;
import com.inz.praca.service.ProductService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
public class CartController {
	private static final String CART = "cart";
	private static final String REDIRECT_CART = "redirect:/cart";

	private final ProductService productService;
	private CartDTO cartDTO;

	@Autowired
	public CartController(ProductService productService, CartDTO cartDTO) {
		this.productService = productService;
		this.cartDTO = cartDTO;
	}

	@ModelAttribute
	public CartDTO getForm() {
		return cartDTO;
	}

	@GetMapping("/cart")
	public String getSessionCart() {
		log.info(cartDTO.toString());
		return CART;
	}

	@GetMapping(value = "/cart/add/{productId}")
	public String addRow(@PathVariable Long productId) {
		log.info("addProduct id {} cart {}", productId, cartDTO);
		cartDTO.addProductDTO(new ProductDTO(productService.getProductById(productId)));
		return REDIRECT_CART;
	}

	@GetMapping(value = "/cart/remove/{rowId}")
	public String removeRow(@PathVariable Integer rowId) {
		log.info("removeProduct rowId {} z {} ", rowId, cartDTO);
		cartDTO.removeProductDTO(rowId);
		return REDIRECT_CART;
	}

	@GetMapping(value = "/cart/clear")
	public String clearCart() {
		log.info("Czyszczenie koszyka");
		cartDTO.clearCart();
		return REDIRECT_CART;
	}

}
