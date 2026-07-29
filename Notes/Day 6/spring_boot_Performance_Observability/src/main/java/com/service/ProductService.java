package com.service;

import com.bean.Product;
import com.exception.ProductNotFoundException;
import com.metrics.ProductMetricsService;
import com.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMetricsService metricsService;

    public ProductService(ProductRepository repository,
                          ProductMetricsService metricsService) {
        this.repository = repository;
        this.metricsService = metricsService;
    }

    // ==============================
    // Create Product
    // ==============================
    @CacheEvict(value = "products", allEntries = true)
    public Product saveProduct(Product product) {

        System.out.println("Saving Product into Database...");

        Product savedProduct = repository.save(product);

        // Counter
        metricsService.incrementProductCreated();

        // Distribution Summary
        metricsService.recordProductPrice(savedProduct.getPrice());

        // Gauge
        metricsService.updateTotalProducts((int) repository.count());

        return savedProduct;
    }

    // ==============================
    // Get All Products
    // ==============================
    @Cacheable("products")
    public List<Product> getAllProducts() {

        long startTime = System.currentTimeMillis();

        System.out.println("Fetching Products from Database...");

        List<Product> products = repository.findAll();

        long endTime = System.currentTimeMillis();

        // Timer
        metricsService.recordFetchTime(endTime - startTime);

        // Gauge
        metricsService.updateTotalProducts(products.size());

        return products;
    }

    // ==============================
    // Get Product By Id
    // ==============================
    @Cacheable(value = "products", key = "#id")
    public Product getProductById(Long id) {

        long startTime = System.currentTimeMillis();

        System.out.println("Fetching Product from Database...");

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product with ID " + id + " not found"));

        long endTime = System.currentTimeMillis();

        // Timer
        metricsService.recordFetchTime(endTime - startTime);

        return product;
    }

    // ==============================
    // Update Product
    // ==============================
    @CachePut(value = "products", key = "#id")
    public Product updateProduct(Long id, Product product) {

        Product existingProduct = repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product with ID " + id + " not found"));

        existingProduct.setName(product.getName());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());

        System.out.println("Updating Product...");

        Product updatedProduct = repository.save(existingProduct);

        // Distribution Summary
        metricsService.recordProductPrice(updatedProduct.getPrice());

        // Gauge
        metricsService.updateTotalProducts((int) repository.count());

        return updatedProduct;
    }

    // ==============================
    // Delete Product
    // ==============================
    @CacheEvict(value = "products", allEntries = true)
    public void deleteProduct(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product with ID " + id + " not found"));

        System.out.println("Deleting Product...");

        repository.delete(product);

        // Gauge
        metricsService.updateTotalProducts((int) repository.count());
    }

}