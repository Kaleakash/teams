package com.service;

import com.bean.Product;
import com.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public String store(Product product){
        productRepository.save(product);
        return "product stored";
    }
    public List<Product> findAll(){
        return productRepository.findAll();
    }
}
