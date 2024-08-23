package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;


@Entity
@Data
@Getter
@Setter
@ToString
@Table(name="inventory_item")
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Positive
    @Column(nullable = false)
    private int quantity;

    @NotBlank
    @Column(nullable = false)
    private String unit; // e.g., kg, liters, pieces
}
