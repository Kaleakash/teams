package com.controller;

import com.bean.Product;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller

public class ProductController {

    @Autowired
    ProductService productService;

    // 1. Display Form and List Products
    // http://localhost:8080/
    @GetMapping("/")
    public String showProductPage(Model model) {
        model.addAttribute("product", new Product()); // Empty object for form data binding
        model.addAttribute("allProducts", productService.findAll()); // List of existing products
        return "index"; // Looks for product-view.html in templates folder
    }

    // 2. Handle Form Submission to Store Product
    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute("product") Product product,
                              Model model) {
        productService.store(product);
        model.addAttribute("allProducts", productService.findAll()); // List of existing products
        return "index"; // Reloads the page to show the updated list
    }
}
