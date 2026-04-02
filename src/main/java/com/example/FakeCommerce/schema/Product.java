package com.example.FakeCommerce.schema;

import java.math.BigDecimal;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")

public class Product {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)

    private Long id; //Primary key
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT") // to store large text
    private String description;

    @Column(nullable = false, name = "price") // custom name to coloumn
    private BigDecimal price;

    private String image;

    private String category;

    private String rating;

}
