package com.example.demo.controllers;

import com.example.demo.model.InventoryItem;
import com.example.demo.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

/**
 * REST controller for managing inventory items.
 */
@Controller
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    /**
     * Retrieves all inventory items.
     * @return List of all inventory items.
     */
    @GetMapping("/inventory-management")
    public String showInventoryManagement(Model model) {
        List<InventoryItem> inventoryItems = inventoryService.getAllItems();
        model.addAttribute("inventoryItems", inventoryItems);
        return "inventory-management";
    }


    @GetMapping
    @ResponseBody // Keeps this method as a REST endpoint
    public List<InventoryItem> getAllItems() {
        return inventoryService.getAllItems();
    }

    /**
     * Retrieves a specific inventory item by its ID.
     * @param id ID of the inventory item.
     * @return ResponseEntity containing the inventory item or a 404 status if not found.
     */
    @GetMapping("/{id}")
    @ResponseBody // Keeps this method as a REST endpoint
    public ResponseEntity<InventoryItem> getItemById(@PathVariable Long id) {
        InventoryItem item = inventoryService.getItemById(id);
        return item != null ? new ResponseEntity<>(item, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Adds a new inventory item.
     * @param item The inventory item to add.
     * @return ResponseEntity containing the created item and a 201 status.
     */
    @PostMapping
    @ResponseBody // Keeps this method as a REST endpoint
    public ResponseEntity<InventoryItem> addItem(@RequestBody InventoryItem item) {
        InventoryItem createdItem = inventoryService.addItem(item);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    /**
     * Updates an existing inventory item by its ID.
     * @param id ID of the inventory item to update.
     * @param updatedItem The updated inventory item data.
     * @return ResponseEntity containing the updated item or a 404 status if not found.
     */
    @PutMapping("/{id}")
    @ResponseBody // Keeps this method as a REST endpoint
    public ResponseEntity<InventoryItem> updateItem(@PathVariable Long id, @RequestBody InventoryItem updatedItem) {
        InventoryItem item = inventoryService.updateItem(id, updatedItem);
        return item != null ? new ResponseEntity<>(item, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes an inventory item by its ID.
     * @param id ID of the inventory item to delete.
     * @return ResponseEntity with a 204 status indicating the item was deleted.
     */
    @DeleteMapping("/{id}")
    @ResponseBody // Keeps this method as a REST endpoint
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        inventoryService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
