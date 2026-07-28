package com.main;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.bean.Product;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootApplication(scanBasePackages = "com")
@EntityScan(basePackages = "com.bean")
@EnableJpaRepositories(basePackages = "com.repository")
public class SpringJpaDataApplication implements CommandLineRunner{

    @Autowired
    ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        Product p1 = new Product();
        p1.setPname("TV");
        p1.setPrice(45000);
        Product p2 = new Product();
        p2.setPname("Computer");
        p2.setPrice(55000);
        String result;
        result = productService.store(p1);
        System.out.println(result);
        result = productService.store(p2);
        System.out.println(result);
        List<Product> listOfProduct =  productService.findAll();
        for (Product p : listOfProduct) {
            System.out.println(p);
        }
    }

    public static void main(String[] args) {
		SpringApplication.run(SpringJpaDataApplication.class, args);

	}

}

