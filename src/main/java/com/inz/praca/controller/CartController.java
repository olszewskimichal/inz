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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class CartController {
	private static final String CART = "cart";

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
		return "redirect:/cart";
	}

	@RequestMapping(value = "/cart/remove/{rowId}")
	public String removeRow(@PathVariable Integer rowId) {
		log.info("removeProduct rowId {} z {} ", rowId, cartDTO);
		cartDTO.removeProductDTO(rowId);
		return "redirect:/cart";
	}
}
