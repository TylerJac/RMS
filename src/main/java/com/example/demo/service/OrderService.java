package com.example.demo.service;

import com.example.demo.model.OrderItem;
import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * Retrieves all orders from the database.
     *
     * @return List of all orders.
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Retrieves a specific order by its ID.
     *
     * @param id ID of the order.
     * @return The order if found, otherwise null.
     */
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    /**
     * Places a new order and updates the inventory accordingly.
     *
     * @param order The order to place.
     * @return The placed order.
     */
    @Valid
    @NotNull
    @Transactional
    public Order placeOrder(Order order) {
        order.setStatus("Waiting");

        // Check if all items in the order are available in sufficient quantity
        for (OrderItem item : order.getOrderItems()) {
            if (!inventoryService.isItemAvailable(item.getItemName(), item.getQuantity())) {
                // Handle insufficient inventory (could throw an exception or return a specific response)
                throw new IllegalStateException("Insufficient inventory for item: " + item.getItemName());
            }
        }

        double totalPrice = 0.0;
        for (OrderItem item : order.getOrderItems()) {
            double itemPrice = calculateItemPrice(item);
            item.setPrice(itemPrice);
            totalPrice += itemPrice * item.getQuantity();

            //Update inventory quantities based on the items ordered
            inventoryService.updateItemQuantity(item.getItemName(), item.getQuantity());
        }
        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }

    /**
     * Updates the status of an existing order.
     *
     * @param id     ID of the order to update.
     * @param status New status of the order.
     * @return The updated order, or null if not found.
     */
    public Order updateOrderStatus(Long id, String status) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            Order order = existingOrder.get();
            order.setStatus(status);
            return orderRepository.save(order);
        } else {
            return null;
        }
    }

    /**
     * Deletes an order by its ID.
     *
     * @param id ID of the order to delete.
     */
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    /**
     * Calculate item price by fetching data from menu... reconfigure when available
     *
     */
    private double calculateItemPrice(OrderItem item) {
        // Replace with actual price lookup logic
        return 10.0;
    }
    /**
     * Calculates the total price of the order based on the items ordered.
     *
     * @param items List of items ordered.
     * @return Total price of the order.
     */
    private double calculateTotalPrice(List<String> items) {
        // Example logic to calculate total price; assumes each item has a fixed price.
        // You can modify this method to fetch prices from the menu items.
        double pricePerItem = 10.0; // Replace with actual logic
        return items.size() * pricePerItem;
    }
}