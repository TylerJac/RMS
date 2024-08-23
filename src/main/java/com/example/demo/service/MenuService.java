package com.example.demo.service;

import com.example.demo.model.MenuItem;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    private static final String FILE_NAME = "menu-items.dat";

    private List<MenuItem> menuItems = new ArrayList<>();


    public void loadMenuItems() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            menuItems = (List<MenuItem>) ois.readObject();
        } catch (FileNotFoundException e) {
            // No previous data found; starting fresh
            System.out.println("Menu file not found. Starting with an empty menu.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveMenuItems() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(menuItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItems;
    }

    public MenuItem getMenuItem(String itemName) {
        return menuItems.stream()
                .filter(item -> item.getItemName().equalsIgnoreCase(itemName))
                .findFirst()
                .orElse(null);
    }

    public MenuItem addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
        saveMenuItems();
        return menuItem;
    }

    public boolean removeMenuItem(String itemName) {
        MenuItem itemToRemove = menuItems.stream()
                .filter(item -> item.getItemName().equalsIgnoreCase(itemName))
                .findFirst()
                .orElse(null);
        if (itemToRemove != null) {
            menuItems.remove(itemToRemove);
            saveMenuItems();
            return true;
        }
        return false;
    }

    public MenuItem editMenuItem(String itemName, MenuItem updatedItem) {
        for (int i = 0; i < menuItems.size(); i++) {
            if (menuItems.get(i).getItemName().equalsIgnoreCase(itemName)) {
                menuItems.set(i, updatedItem);
                saveMenuItems();
                return updatedItem;
            }
        }
        return null;
    }
}
