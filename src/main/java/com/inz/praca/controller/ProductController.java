package com.inz.praca.controller;

import javax.validation.Valid;

import com.inz.praca.dto.ProductDTO;
import com.inz.praca.service.ProductService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class ProductController {

	private static final String NEW_PRODUCT = "newProduct";
	private static final String PRODUCT_FORM = "productCreateForm";
	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/addProduct")
	public String addNewProductPage(Model model) {
		model.addAttribute(PRODUCT_FORM, new ProductDTO());
		return NEW_PRODUCT;
	}

	@PostMapping("/addProduct")
	public String confirmNewProduct(@Valid @ModelAttribute(PRODUCT_FORM) ProductDTO productDTO, BindingResult result, Model model) {
		log.info("Proba dodania nowego produktu {}", productDTO);
		if (result.hasErrors()) {
			log.debug("wystapil blad {} podczas walidacji produktu {}", result.getAllErrors(), productDTO);
			model.addAttribute(PRODUCT_FORM, productDTO);
			return NEW_PRODUCT;
		}
		try {
			productService.createProduct(productDTO);
			return "index";
		}
		catch (IllegalArgumentException e) {
			log.debug(e.getMessage());
			model.addAttribute(PRODUCT_FORM, productDTO);
		}
		return NEW_PRODUCT;
	}

}
