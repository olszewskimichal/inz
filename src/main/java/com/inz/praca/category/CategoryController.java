package com.inz.praca.category;

import com.inz.praca.products.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.PersistenceException;
import javax.validation.Valid;

@Controller
@Slf4j
public class CategoryController {

    private static final String CATEGORY_CREATE_FORM = "categoryCreateForm";
    private static final String NEW_CATEGORY = "newCategory";
    private final ProductService productService;

    public CategoryController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/addCategory")
    public String addNewCategoryPage(Model model) {
        model.addAttribute(CATEGORY_CREATE_FORM, new CategoryDTO());
        return NEW_CATEGORY;
    }

    @PostMapping("/addCategory")
    public String confirmNewCategory(@Valid @ModelAttribute(value = CATEGORY_CREATE_FORM) CategoryDTO categoryDTO, BindingResult result, Model model) {
        log.info("Proba dodania nowej kategorii {}", categoryDTO);
        if (result.hasErrors()) {
            log.debug("wystapil blad {} podczas walidacji kategorii {}", result.getAllErrors(), categoryDTO);
            model.addAttribute(CATEGORY_CREATE_FORM, categoryDTO);
            return NEW_CATEGORY;
        }
        try {
            log.debug("tworzy kategorie");
            productService.createCategoryFromDTO(categoryDTO);
            return "redirect:/";
        } catch (IllegalArgumentException | PersistenceException | DataIntegrityViolationException e) {
            log.debug(e.getMessage());
            model.addAttribute(CATEGORY_CREATE_FORM, categoryDTO);
        }
        return NEW_CATEGORY;
    }
}
