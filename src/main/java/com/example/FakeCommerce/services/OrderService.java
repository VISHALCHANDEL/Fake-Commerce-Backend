package com.example.FakeCommerce.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.FakeCommerce.adapters.OrderAdapter;
import com.example.FakeCommerce.dtos.CreateOrderRequestDTO;
import com.example.FakeCommerce.dtos.GetOrderResponseDto;
import com.example.FakeCommerce.dtos.UpdateOrderRequestDto;
import com.example.FakeCommerce.exceptions.ResourceNotFoundException;
import com.example.FakeCommerce.repositories.OrderRepository;
import com.example.FakeCommerce.repositories.ProductRepository;
import com.example.FakeCommerce.schema.Order;
import com.example.FakeCommerce.schema.OrderProducts;
import com.example.FakeCommerce.schema.OrderStatus;
import com.example.FakeCommerce.schema.Product;

import aQute.bnd.annotation.headers.RequireCapability;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import com.example.FakeCommerce.repositories.OrderproductsRepository;
import com.example.FakeCommerce.repositories.OrderproductsRepository;
import java.util.*;
@Service
@RequiredArgsConstructor
@NoArgsConstructor
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

    public void deleteOrder(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
    }

    @Transactional
    public void createOrder(CreateOrderRequestDTO createOrderRequestDTO){
        Order order = Order.builder()
                     .status(OrderStatus.PENDING)
                     .build();
        orderRepository.save(order);

        if(createOrderRequestDTO.getOrderItems() != null){
        //    List<Long>productIds = new ArrayList<>();
        //   // Method 1
        //    for(var itemDto: createOrderRequestDTO.getOrderItems()){
        //     productIds.add(itemDto.getProductId());
        //    }
        //    // Method 2
            List<Long>productIds = createOrderRequestDTO.getOrderItems().stream()
                                   .map(item -> item.getProductId()).toList();
            
            List<Product>products = productRepository.findAllById(productIds);

            Map<Long,Product>productMap = products.stream().collect(Collectors.toMap(Product::getId, Function.identity()));
        }

        
    }
        // we need to create new order instance
        //2. i the payload (dto) has some order products, add those in the order as well
        // other wise skip it
        
        public GetOrderResponseDto updateOrder(Long id, UpdateOrderRequestDto updateOrderRequestDto){
            Order order = orderRepository.findById((id)).
            orElseThrow(()-> new ResourceNotFoundException("Order not found with id:" + id));

            if(updateOrderRequestDto.getStatus() != null){
                order.setStatus(updateOrderRequestDto.getStatus());
                orderRepository.save(order);
            }
            if(updateOrderRequestDto.getOrderItems()!= null){
                for(var itemDto: updateOrderRequestDto.getOrderItems()){
                    // process each item ------> N+1 problem
                }
            }
            return orderAdapter.mapToGetOrderResponseDto(order);
        }

    }

// User -> cart -> adds an item -> new Order (pending)
// user -> adds more items in the cart -> Same order will be updated
// during checkout -> order pending -> success/failure
// problem with this approach is that we can save some products or some not
// for loop is a classic example of n + 1 query
