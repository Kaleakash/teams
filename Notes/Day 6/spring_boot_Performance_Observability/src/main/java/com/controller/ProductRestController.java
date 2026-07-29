package com.controller;

import com.bean.Product;
import com.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Product API", description = "CRUD Operations for Product Management")
public class ProductRestController {

    private final ProductService service;

    public ProductRestController(ProductService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new product")
    @PostMapping
    public Product saveProduct(@Valid @RequestBody Product product) {
        return service.saveProduct(product);
    }

    @Operation(summary = "Retrieve all products")
    @GetMapping
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @Operation(summary = "Retrieve product by ID")
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @Operation(summary = "Update an existing product")
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id,
                                 @Valid @RequestBody Product product) {
        return service.updateProduct(id, product);
    }

    @Operation(summary = "Delete a product")
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return "Product Deleted Successfully";
    }

}