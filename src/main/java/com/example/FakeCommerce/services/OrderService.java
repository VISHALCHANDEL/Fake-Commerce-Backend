package com.example.FakeCommerce.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.FakeCommerce.adapters.OrderAdapter;
import com.example.FakeCommerce.dtos.CreateOrderRequestDTO;
import com.example.FakeCommerce.dtos.GetOrderResponseDto;
import com.example.FakeCommerce.exceptions.ResourceNotFoundException;
import com.example.FakeCommerce.repositories.OrderRepository;
import com.example.FakeCommerce.repositories.ProductRepository;
import com.example.FakeCommerce.schema.Order;
import com.example.FakeCommerce.schema.OrderProducts;
import com.example.FakeCommerce.schema.OrderStatus;
import com.example.FakeCommerce.schema.Product;

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

    public void deleteOrder(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
    }

    public void createOrder(CreateOrderRequestDTO createOrderRequestDTO){
        Order order = Order.builder()
                     .status(OrderStatus.PENDING)
                     .build();
        orderRepository.save(order);

        if(createOrderRequestDTO.getOrderItems() != null){
            for(var itemDto : createOrderRequestDTO.getOrderItems()){
                Product product = productRepository.findById(itemDto.getProductId())
                .orElseThrow(()-> new ResourceNotFoundException("Product not found with id:" + itemDto.getProductId()));

                OrderProducts orderProduct = OrderProducts.builder()
                                             .order(order)
                                             .product(product)
                                             .quantity(itemDto.getQuantity() != null ? itemDto.getQuantity():1)
                                             .build();

                orderproductsRepository.save(orderProduct);

            }
        }

        
    }
        // we need to create new order instance
        //2. i the payload (dto) has some order products, add those in the order as well
        // other wise skip it
        

    }

// User -> cart -> adds an item -> new Order (pending)
// user -> adds more items in the cart -> Same order will be updated
// during checkout -> order pending -> success/failure
// problem with this approach is that we can save some products or some not
// for loop is a classic example of n + 1 query
