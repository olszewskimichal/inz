package com.inz.praca.controller;

import javax.validation.Valid;
import java.util.List;

import com.inz.praca.domain.Product;
import com.inz.praca.dto.ProductDTO;
import com.inz.praca.service.ProductService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class ProductController {

	private static final String NEW_PRODUCT = "newProduct";
	private static final String PRODUCT_FORM = "productCreateForm";
	private static final String PRODUCT = "product";
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

	@GetMapping(value = "/products/product/{id}")
	public String showProductDetail(@PathVariable Long id, Model model) {
		log.debug("Pobranie produktu o id {}", id);
		ProductDTO productDTO = new ProductDTO(productService.getProductById(id));
		model.addAttribute(productDTO);
		return PRODUCT;
	}

	@GetMapping(value = "/products")
	public String showProducts(Model model, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "pageSize", required = false) Integer pageSize) {
		List<Product> products = productService.getProducts(page, pageSize, null);
		log.debug("Pobrano produktów {}", products.size());
		model.addAttribute("products", products);
		return "products";
	}

}
