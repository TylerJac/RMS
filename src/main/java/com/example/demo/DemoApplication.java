package com.example.demo;

import com.example.demo.controllers.InventoryController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.demo.model.InventoryItem;
import com.example.demo.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class DemoApplication {

    @Autowired
    private InventoryService inventoryService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

    }

    /**
     * Add inventory to database everytime this code runs.     *
     */
    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            //Add initial inventory items
//            addInventoryItem("Coffee", 40_000, "ounces");
//            addInventoryItem("Blueberry Muffins", 1500, "each");
//            addInventoryItem("Chocolate Chip Muffins", 1500, "each");
//            addInventoryItem("Black Tea", 40_000, "ounces");

        };
    }

    private void addInventoryItem(String name, int quantity, String unit) {
        InventoryItem item = new InventoryItem();
        item.setName(name);
        item.setQuantity(quantity);
        item.setUnit(unit);
        inventoryService.addItem(item);

    }


}
