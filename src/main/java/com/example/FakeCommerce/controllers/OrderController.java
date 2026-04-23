package com.example.FakeCommerce.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.FakeCommerce.schema.Order;

public class OrderController {
    
    @GetMapping
    public List<Order>getAllOrders(){
        throw new UnsupportedOperationException("Not implemented");
    }
    
    @PostMapping
    public Order createOrder(){
        throw new UnsupportedOperationException("Not implemented");
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id){
        throw new UnsupportedOperationException("Not implemented");
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id){
        throw new UnsupportedOperationException("Not implemented");
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByProductId(@PathVariable Long productId){
        throw new UnsupportedOperationException("Not implemented");
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id){
        throw new UnsupportedOperationException("Not implemented");
    }

    @GetMapping("/{id}")
    public void getOrderSummary(@PathVariable Long id){
                throw new UnsupportedOperationException("Not implemented");

    }
}
