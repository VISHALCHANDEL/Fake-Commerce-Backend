package com.example.FakeCommerce.adapters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.FakeCommerce.dtos.GetOrderResponseDto;
import com.example.FakeCommerce.dtos.OrderItemResponseDto;
import com.example.FakeCommerce.repositories.OrderproductsRepository;
import com.example.FakeCommerce.schema.Order;
import com.example.FakeCommerce.schema.OrderProducts;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor

public class OrderAdapter {
    
    private final OrderproductsRepository orderproductsRepository;

    public List<GetOrderResponseDto>mapToGetOrderResponseDtoList(List<Order>orders){
        return orders.stream().map(this::mapToGetOrderResponseDto).collect(Collectors.toList());
    }
    public  GetOrderResponseDto mapToGetOrderResponseDto(Order order){
          
        
        List<OrderProducts> orderProducts  =  orderproductsRepository.findByOrderId(order.getId());
        List<OrderItemResponseDto>items = mapToOrderItemResponseDto(orderProducts);
        return GetOrderResponseDto.builder()
            .id(order.getId())
            .status(order.getStatus())
            .createdAt(order.getCreatedAt())
            .updatedAt(order.getUpdatedAt())
            .items(items)
            .build();
    }

    public List<OrderItemResponseDto> mapToOrderItemResponseDto(List<OrderProducts> orderProducts){
        return orderProducts.stream().map(
            op -> OrderItemResponseDto.builder()
            .productId(op.getProduct().getId())
            .quantity(op.getQuantity())
            .productName(op.getProduct().getTitle())
            .productPrice(op.getProduct().getPrice())
            .productImage(op.getProduct().getImage()) // Removed: not in DTO
            .subTotal(op.getProduct().getPrice().multiply(java.math.BigDecimal.valueOf(op.getQuantity())))
            .build()).collect(Collectors.toList());
            

    }
}
