package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Tables")
@Getter
@Setter
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int size;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TableStatus status;

    public RestaurantTable() {
        // Default constructor needed by JPA
    }

    public RestaurantTable(int size, TableStatus status) {
        this.size = size;
        this.status = status;
    }

    public String getName() {
    return null;
    }

    // Nested enum for table status
    public enum TableStatus {
        AVAILABLE,
        RESERVED,
        OCCUPIED
    }
}
