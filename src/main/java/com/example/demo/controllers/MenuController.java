package com.example.demo.controllers;

import com.example.demo.dto.MenuItemDTO;
import com.example.demo.model.MenuItem;
import com.example.demo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    // Get all menu items
    @GetMapping
    public List<MenuItem> getAllMenuItems() {
        return menuService.getAllMenuItems();
    }

    // Add a new menu item using MenuItemDTO
    @PostMapping
    public MenuItem addMenuItem(@RequestBody MenuItemDTO menuItemDTO) {
        MenuItem menuItem = new MenuItem(menuItemDTO.getItemName(),
                menuItemDTO.getItemDescription(),
                menuItemDTO.getPreparationTime(),
                menuItemDTO.getPrice(),
                menuItemDTO.getIngredients());
        return menuService.addMenuItem(menuItem);
    }

    // Edit a menu item by name using MenuItemDTO
    @PutMapping("/{itemName}")
    public MenuItem editMenuItem(@PathVariable String itemName, @RequestBody MenuItemDTO updatedItemDTO) {
        MenuItem updatedItem = new MenuItem(updatedItemDTO.getItemName(),
                updatedItemDTO.getItemDescription(),
                updatedItemDTO.getPreparationTime(),
                updatedItemDTO.getPrice(),
                updatedItemDTO.getIngredients());
        return menuService.editMenuItem(itemName, updatedItem);
    }

    // Delete a menu item by name
    @DeleteMapping("/{itemName}")
    public String deleteMenuItem(@PathVariable String itemName) {
        boolean isRemoved = menuService.removeMenuItem(itemName);
        return isRemoved ? "Menu item removed successfully." : "Menu item not found.";
    }
}
