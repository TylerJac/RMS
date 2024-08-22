package com.example.demo.service;

import com.example.demo.model.InventoryItem;
import com.example.demo.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Service class for managing inventory-related business logic.
 */
@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    /**
     * Retrieves all inventory items from the database.
     * @return List of all inventory items.
     */
    public List<InventoryItem> getAllItems() {
        return inventoryRepository.findAll();
    }

    /**
     * Retrieves a specific inventory item by its ID.
     * @param id ID of the inventory item.
     * @return The inventory item if found, otherwise null.
     */
    public InventoryItem getItemById(Long id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    /**
     * Adds a new inventory item to the database.
     * @param item Inventory item to be added.
     * @return The added inventory item.
     */
    public InventoryItem addItem(InventoryItem item) {
        return inventoryRepository.save(item);
    }

    /**
     * Updates an existing inventory item with new data.
     * @param id ID of the inventory item to update.
     * @param updatedItem Updated inventory item data.
     * @return The updated inventory item, or null if not found.
     */
    public InventoryItem updateItem(Long id, InventoryItem updatedItem) {
        Optional<InventoryItem> existingItem = inventoryRepository.findById(id);
        if (existingItem.isPresent()) {
            InventoryItem item = existingItem.get();
            item.setName(updatedItem.getName());
            item.setQuantity(updatedItem.getQuantity());
            item.setUnit(updatedItem.getUnit());
            return inventoryRepository.save(item);
        } else {
            return null;
        }
    }

    /**
     * Deletes an inventory item by its ID.
     * @param id ID of the inventory item to delete.
     */
    public void deleteItem(Long id) {
        inventoryRepository.deleteById(id);
    }

    /**
     * Method used when placing an order to ensure the requests are in stock
     * @param itemName name of requested order
     * @param requiredQuantity amount of requested order
     * @return true if available, false if not in stock or not enough in stock
     */
    public boolean isItemAvailable(String itemName, int requiredQuantity) {
        Optional<InventoryItem> item = inventoryRepository.findByName(itemName);
        return item.isPresent() && item.get().getQuantity() >= requiredQuantity;
    }
    /**
     * Updates the quantity of an inventory item based on usage.
     * @param itemName Name of the inventory item.
     * @param quantityUsed Quantity of the item used.
     */
    public void updateItemQuantity(String itemName, int quantityUsed) {
        Optional<InventoryItem> item = inventoryRepository.findByName(itemName);
        item.ifPresent(i -> {
            int updatedQuantity = i.getQuantity() - quantityUsed;
            if (updatedQuantity >= 0) {
                i.setQuantity(updatedQuantity);
                inventoryRepository.save(i);
            } else {
                // Handle negative inventory (could throw an exception)
                throw new IllegalStateException("Inventory cannot be negative for item: " + itemName);
            }
        });
    }
    public void alertIfLow(String itemName) {
        Optional<InventoryItem> item = inventoryRepository.findByName(itemName);
        item.ifPresent(i -> {
            if (i.getQuantity() < 10) { // Assuming the threshold for "low" is 10 units
                // Implement your alerting logic here. It could be an email, a log entry, etc.
                System.out.println("Alert: " + itemName + " is running low!");
            }
        });
    }

}
