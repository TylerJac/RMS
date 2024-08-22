package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {
    // Constructors, getters, and setters
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    private String itemName;

    @Getter
    @Column(nullable = false)
    private int quantity;

    @Getter
    @Column(nullable = false)
    private double price;


}

