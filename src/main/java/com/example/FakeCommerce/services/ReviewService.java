package com.example.FakeCommerce.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.FakeCommerce.adapters.ReviewAdapter;
import com.example.FakeCommerce.dtos.CreateReviewRequestDto;
import com.example.FakeCommerce.dtos.GetReviewResponseDto;
import com.example.FakeCommerce.exceptions.ResourceNotFoundException;
import com.example.FakeCommerce.repositories.OrderRepository;
import com.example.FakeCommerce.repositories.ProductRepository;
import com.example.FakeCommerce.repositories.ReviewRepository;
import com.example.FakeCommerce.schema.Order;
import com.example.FakeCommerce.schema.Product;
import com.example.FakeCommerce.schema.Review;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    private final ReviewAdapter reviewAdapter;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    
    public List<GetReviewResponseDto> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviewAdapter.mapToGetReviewResponseDtoList(reviews);
    }
    
    public GetReviewResponseDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        return reviewAdapter.mapToGetReviewResponseDto(review);
    }
    
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        reviewRepository.delete(review);
    }
    
    public GetReviewResponseDto createReview(CreateReviewRequestDto requestDto) {
        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + requestDto.getOrderId()));
        
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + requestDto.getProductId()));
        
        Review review = Review.builder()
                .order(order)
                .product(product)
                .rating(requestDto.getRating())
                .comment(requestDto.getComment())
                .build();
        
        Review savedReview = reviewRepository.save(review);
        return reviewAdapter.mapToGetReviewResponseDto(savedReview);
    }
    
    public List<GetReviewResponseDto> getReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviewAdapter.mapToGetReviewResponseDtoList(reviews);
    }
    
    public List<GetReviewResponseDto> getReviewsByOrderId(Long orderId) {
        List<Review> reviews = reviewRepository.findByOrderId(orderId);
        return reviewAdapter.mapToGetReviewResponseDtoList(reviews);
    }
}
