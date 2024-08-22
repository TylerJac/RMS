package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class MenuItem implements Serializable {

    private String itemName;
    private String itemDescription;
    private int preparationTime; // in minutes
    private double price;
    private List<String> ingredients;

    // Constructors, Getters, and Setters

    public MenuItem() {
    }

    public MenuItem(String itemName, String itemDescription, int preparationTime, double price, List<String> ingredients) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.preparationTime = preparationTime;
        this.price = price;
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "itemName='" + itemName + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", preparationTime=" + preparationTime +
                ", price=" + price +
                ", ingredients=" + ingredients +
                '}';
    }
}
