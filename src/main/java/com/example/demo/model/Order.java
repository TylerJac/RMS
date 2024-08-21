package com.example.demo.model;

import jakarta.persistence.*;
        import lombok.Data;

import java.util.List;

/**
 * Represents a customer order in the restaurant.
 */
@Entity
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // List of item names ordered by the customer
    @ElementCollection
    private List<String> items;

    // Total price of the order
    private double totalPrice;

    // Current status of the order (e.g., "Waiting", "Preparing", "Ready")
    private String status;
}
