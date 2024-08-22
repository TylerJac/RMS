package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuItemDTO {

    private String itemName;
    private String itemDescription;
    private int preparationTime; // in minutes
    private double price;
    private List<String> ingredients;

    public MenuItemDTO() {
    }

    public MenuItemDTO(String itemName, String itemDescription, int preparationTime, double price, List<String> ingredients) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.preparationTime = preparationTime;
        this.price = price;
        this.ingredients = ingredients;
    }
}

