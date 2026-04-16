package com.example.FakeCommerce.schema;

import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE categories SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Order extends BaseEntity{

    private OrderStatus status;

    // @ManyToMany
    // @JoinTable( // Problem with this is that it will create the table but i cannot add other attributes into that table
    //     name = "order_products",
    //     joinColumns = @JoinColumn(name = "order_id"),// the Fk belonging to the same schema --Order
    //     inverseJoinColumns = @JoinColumn(name = "product_id")// the Fk belonging to the other scheme --Product

    // )
   // private List<Product>products;
}
