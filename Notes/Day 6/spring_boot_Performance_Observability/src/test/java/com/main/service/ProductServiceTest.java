package com.main.service;

import com.bean.Product;
import com.exception.ProductNotFoundException;
import com.metrics.ProductMetricsService;
import com.repository.ProductRepository;
import com.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMetricsService metricsService;

    @InjectMocks
    private ProductService service;

    private Product product;

    @BeforeEach
    void setUp() {

        product = new Product();

        product.setId(1L);
        product.setName("Laptop");
        product.setCategory("Electronics");
        product.setPrice(75000);
        product.setQuantity(10);
    }

    @Test
    @DisplayName("Save Product")
    void testSaveProduct() {
        // provide mock
        when(repository.save(product)).thenReturn(product);

        Product savedProduct = service.saveProduct(product);

        assertNotNull(savedProduct);
        assertEquals("Laptop", savedProduct.getName());

        verify(repository, times(1)).save(product);
    }

    @Test
    @DisplayName("Get Product By Id")
    void testGetProductById() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(product));

        Product result = service.getProductById(1L);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());

        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("Get Product By Invalid Id")
    void testGetProductByInvalidId() {

        when(repository.findById(100L))
                .thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> service.getProductById(100L));
    }

    @Test
    @DisplayName("Get All Products")
    void testGetAllProducts() {

        Product mouse = new Product();

        mouse.setId(2L);
        mouse.setName("Mouse");
        mouse.setCategory("Electronics");
        mouse.setPrice(500);
        mouse.setQuantity(50);

        when(repository.findAll())
                .thenReturn(Arrays.asList(product, mouse));

        List<Product> products = service.getAllProducts();

        assertEquals(2, products.size());

        verify(repository).findAll();
    }

    @Test
    @DisplayName("Update Product")
    void testUpdateProduct() {

        Product updatedProduct = new Product();

        updatedProduct.setName("Gaming Laptop");
        updatedProduct.setCategory("Electronics");
        updatedProduct.setPrice(90000);
        updatedProduct.setQuantity(5);

        when(repository.findById(1L))
                .thenReturn(Optional.of(product));

        when(repository.save(any(Product.class)))
                .thenReturn(updatedProduct);

        Product result = service.updateProduct(1L, updatedProduct);

        assertEquals("Gaming Laptop", result.getName());
        assertEquals(90000, result.getPrice());

        verify(repository).save(any(Product.class));
    }

    @Test
    @DisplayName("Delete Product")
    void testDeleteProduct() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(product));

        doNothing().when(repository).delete(product);

        service.deleteProduct(1L);

        verify(repository, times(1)).delete(product);
    }

}
