package com.controller;

import com.bean.Orders;
import com.service.OrdersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersRestController {

    private final OrdersService orderService;

    // Inject service instead of repository
    public OrdersRestController(OrdersService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Orders> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<Orders> placeOrder(@PathVariable int productId, @RequestBody Orders order) {
        return orderService.createOrder(productId, order)
                .map(savedOrder -> new ResponseEntity<>(savedOrder, HttpStatus.CREATED))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
