package com.example.FakeCommerce.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FakeCommerce.schema.Review;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    @GetMapping
    public List<Review>getAllReviews(){
        throw new UnsupportedOperationException("Not implemented");
    }

    @PostMapping
    public Review createReview(){
        throw new UnsupportedOperationException("Not implemented");
    }   
    
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id){
        throw new UnsupportedOperationException("not implemented");
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Long id){
        throw new UnsupportedOperationException("Not implemented");
    }

    @GetMapping("/product/{productId}")
    public List<Review> getReviewByProductid(@PathVariable Long ProductId){
        throw new UnsupportedOperationException("not implementd");
    }

    @GetMapping("/order/{orderId}")
    public List<Review> getReviewsByOrderId(@PathVariable Long orderId){
        throw new UnsupportedOperationException("not implemented");
    }
    
}
