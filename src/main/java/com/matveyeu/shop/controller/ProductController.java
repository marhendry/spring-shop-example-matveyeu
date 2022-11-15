package com.matveyeu.shop.controller;

import com.matveyeu.shop.domain.Product;
import com.matveyeu.shop.service.ProductService;
import com.matveyeu.shop.validator.CategoryService;
import com.matveyeu.shop.validator.ProductValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;
    private final ProductValidator productValidator;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, ProductValidator productValidator, CategoryService categoryService) {
        this.productService = productService;
        this.productValidator = productValidator;
        this.categoryService = categoryService;
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("productForm", new Product());
        model.addAttribute("method", "new");
        model.addAttribute("categories", categoryService.findAll());
        return "product";
    }

    @PostMapping("/new")
    public String newProduct(@ModelAttribute("productForm") Product productForm, BindingResult bindingResult, Model model) {
        productValidator.validate(productForm, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.error(String.valueOf(bindingResult.getFieldError()));
            model.addAttribute("method", "new");
            return "product";
        }
        productService.save(productForm);
        logger.debug(String.format("Product with id: %s successfully created.", productForm.getId()));

        return "redirect:/home";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") long productId, Model model){
        Product product = productService.findById(productId);
        if (product != null){
            model.addAttribute("productForm", product);
            model.addAttribute("method", "edit");
            model.addAttribute("categories", categoryService.findAll());
            return "product";
        }else {
            return "error/404";
        }
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") long productId, @ModelAttribute("productForm") Product productForm, BindingResult bindingResult, Model model){
        productValidator.validate(productForm, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.error(String.valueOf(bindingResult.getFieldError()));
            model.addAttribute("method", "edit");
            return "product";
        }
        productService.edit(productId, productForm);
        logger.debug(String.format("Product with id: %s has been successfully edited.", productId));

        return "redirect:/home";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") long productId){
        Product product = productService.findById(productId);
        if (product != null){
            productService.delete(productId);
            logger.debug(String.format("Product with id: %s successfully deleted.", product.getId()));
            return "redirect:/home";
        }else {
            return "error/404";
        }
    }
}