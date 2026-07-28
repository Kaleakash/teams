package com.service;


import com.bean.Orders;
import com.repository.OrdersRepository;
import com.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {

    private final OrdersRepository orderRepository;
    private final ProductRepository productRepository;

    public OrdersService(OrdersRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Orders> createOrder(int productId, Orders order) {
        return productRepository.findById(productId).map(product -> {
            order.setProduct(product);
            return orderRepository.save(order);
        });
    }

    public Optional<Orders> getOrderById(int id) {
        return orderRepository.findById(id);
    }
}

