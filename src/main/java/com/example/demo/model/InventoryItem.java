package com.example.demo.model;
/**
 * This class is used to add and manipulate items in the inventory database.
 */
//Use jakarta.persistence for object-relational mapping.
//This allows us to interact with the SQLite database.
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

//Each instance of inventory item is an entity in the database
@Entity
@Data // used to generate getters, setters, toString(), equals(), and hashCode()
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //primary key will be generated automatically
                                        // using SQLite's auto increment
    private Long id;

    private String name;
    private int quantity;
    private String unit; // e.g., kg, liters, pieces
}
