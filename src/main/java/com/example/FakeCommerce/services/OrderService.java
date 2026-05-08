package com.example.FakeCommerce.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.FakeCommerce.adapters.OrderAdapter;
import com.example.FakeCommerce.dtos.GetOrderResponseDto;
import com.example.FakeCommerce.repositories.OrderRepository;
import com.example.FakeCommerce.repositories.ProductRepository;
import com.example.FakeCommerce.schema.Order;

import aQute.bnd.annotation.headers.RequireCapability;
import lombok.RequiredArgsConstructor;

import com.example.FakeCommerce.repositories.OrderproductsRepository;
import com.example.FakeCommerce.repositories.OrderproductsRepository;

@Service
@RequiredArgsConstructor

public class OrderService {
    private final OrderRepository orderRepository;
    
    private final OrderproductsRepository orderproductsRepository;
    
    private final ProductRepository
    productRepository;
    private final OrderAdapter orderAdapter;

    public List<GetOrderResponseDto>getAllOrders(){
        List<Order> orders  = orderRepository.findAll();;
        return orderAdapter.mapToGetOrderResponseDtoList(orders);
        
    }

    public GetOrderResponseDto getOrderById(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return orderAdapter.mapToGetOrderResponseDto(order);
    }

    public void deleteOrderByid(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
    }
}
