package com.main.controller;

import com.bean.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService service;

    @Test
    void testGetAllProducts() throws Exception {

        List<Product> products = Arrays.asList(
                new Product(1L, "Laptop", "Electronics", 50000, 10),
                new Product(2L, "Mouse", "Electronics", 500, 20)
        );

        when(service.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[1].name").value("Mouse"));
    }

    @Test
    void testGetProductById() throws Exception {

        Product product =
                new Product(1L, "Laptop", "Electronics", 50000, 10);

        when(service.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.category").value("Electronics"));
    }

    @Test
    void testSaveProduct() throws Exception {

        Product product =
                new Product(1L, "Laptop", "Electronics", 50000, 10);

        when(service.saveProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(50000));
    }

    @Test
    void testUpdateProduct() throws Exception {

        Product product =
                new Product(1L, "Updated Laptop", "Electronics", 60000, 5);

        when(service.updateProduct(any(Long.class), any(Product.class)))
                .thenReturn(product);

        mockMvc.perform(put("/products/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Laptop"))
                .andExpect(jsonPath("$.price").value(60000));
    }

    @Test
    void testDeleteProduct() throws Exception {

        doNothing().when(service).deleteProduct(1L);

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product Deleted Successfully"));
    }
}