package com.example.demo.service;

import com.example.demo.model.MenuItem;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Order;
import com.example.demo.repository.MenuItemRepository;
import com.example.demo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing order-related business logic.
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private MenuItemRepository menuItemRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Transactional
    public Order placeOrder(Order order) {
        order.setStatus("Waiting");

        List<OrderItem> insufficientItems = new ArrayList<>();
        for (OrderItem item : order.getOrderItems()) {
            if (!inventoryService.isItemAvailable(item.getItemName(), item.getQuantity())) {
                insufficientItems.add(item);
            }
        }

        if (!insufficientItems.isEmpty()) {
            throw new IllegalStateException("Insufficient inventory for items: " + insufficientItems);
        }

        double totalPrice = 0.0;
        for (OrderItem item : order.getOrderItems()) {
            double itemPrice = calculateItemPrice(item);
            item.setPrice(itemPrice);
            totalPrice += itemPrice * item.getQuantity();
            inventoryService.updateItemQuantity(item.getItemName(), item.getQuantity());
            inventoryService.alertIfLow(item.getItemName());
        }

        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }

    public Order updateOrderStatus(Long id, String status) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            Order order = existingOrder.get();
            order.updateStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    private double calculateItemPrice(OrderItem item) {
        Long menuItemId = item.getId();
        if (menuItemId != null) {
            Optional<MenuItem> menuItem = menuItemRepository.findById(menuItemId);
            if (menuItem.isPresent()) {
                return menuItem.get().getPrice();
            } else {
                throw new IllegalArgumentException("No menu item found with ID: " + menuItemId);
            }
        }
        throw new IllegalStateException("Order item does not have a linked menu item.");
    }
}

