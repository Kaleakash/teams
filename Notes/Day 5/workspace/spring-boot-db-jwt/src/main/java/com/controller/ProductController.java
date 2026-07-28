package com.controller;

import com.model.Product;
import com.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository repo;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public Product add(@RequestBody Product product) {
        return repo.save(product);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @GetMapping("/all")
    public List<Product> all() {
        return repo.findAll();
    }
}
