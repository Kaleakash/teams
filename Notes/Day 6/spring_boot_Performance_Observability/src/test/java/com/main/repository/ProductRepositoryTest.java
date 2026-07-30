package com.main.repository;

import com.bean.Product;
import com.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    private Product createProduct() {

        Product product = new Product();

        product.setName("Laptop");
        product.setCategory("Electronics");
        product.setPrice(75000);
        product.setQuantity(10);

        return product;
    }

    @Test
    @DisplayName("Save Product")
    void testSaveProduct() {

        Product savedProduct = repository.save(createProduct());

        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());
        assertEquals("Laptop", savedProduct.getName());

    }

    @Test
    @DisplayName("Find Product By Id")
    void testFindProductById() {

        Product saved = repository.save(createProduct());

        Optional<Product> product =
                repository.findById(saved.getId());

        assertTrue(product.isPresent());

        assertEquals("Laptop",
                product.get().getName());

    }

    @Test
    @DisplayName("Find All Products")
    void testFindAllProducts() {

        repository.save(createProduct());

        Product mouse = new Product();

        mouse.setName("Mouse");
        mouse.setCategory("Electronics");
        mouse.setPrice(800);
        mouse.setQuantity(25);

        repository.save(mouse);

        List<Product> products =
                repository.findAll();

        assertEquals(2, products.size());

    }

    @Test
    @DisplayName("Update Product")
    void testUpdateProduct() {

        Product saved =
                repository.save(createProduct());

        saved.setPrice(85000);

        Product updated =
                repository.save(saved);

        assertEquals(85000,
                updated.getPrice());

    }

    @Test
    @DisplayName("Delete Product")
    void testDeleteProduct() {

        Product saved =
                repository.save(createProduct());

        repository.delete(saved);

        Optional<Product> product =
                repository.findById(saved.getId());

        assertFalse(product.isPresent());

    }

    @Test
    @DisplayName("Find Products By Category")
    void testFindByCategory() {

        repository.save(createProduct());

        List<Product> products =
                repository.findByCategory("Electronics");

        assertFalse(products.isEmpty());

    }

    @Test
    @DisplayName("Find Products By Price Greater Than")
    void testFindByPriceGreaterThan() {

        repository.save(createProduct());

        List<Product> products =
                repository.findByPriceGreaterThan(50000);

        assertEquals(1, products.size());

    }

}
