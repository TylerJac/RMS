package com.example.demo.controller;

import com.example.demo.controllers.OrderController;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllOrders() {
        // Arrange
        Order order1 = new Order();
        Order order2 = new Order();
        List<Order> orders = Arrays.asList(order1, order2);

        when(orderService.getAllOrders()).thenReturn(orders);

        // Act
        List<Order> result = orderController.getAllOrders();

        // Assert
        assertEquals(2, result.size());
        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    public void testGetOrderById_Success() {
        // Arrange
        Order order = new Order();
        when(orderService.getOrderById(1L)).thenReturn(order);

        // Act
        ResponseEntity<Order> response = orderController.getOrderById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
        verify(orderService, times(1)).getOrderById(1L);
    }

    @Test
    public void testGetOrderById_NotFound() {
        // Arrange
        when(orderService.getOrderById(1L)).thenReturn(null);

        // Act
        ResponseEntity<Order> response = orderController.getOrderById(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(orderService, times(1)).getOrderById(1L);
    }

    @Test
    public void testPlaceOrder() {
        // Arrange
        Order order = new Order();
        when(orderService.placeOrder(any(Order.class))).thenReturn(order);

        // Act
        ResponseEntity<Order> response = orderController.placeOrder(order);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(order, response.getBody());
        verify(orderService, times(1)).placeOrder(order);
    }

    @Test
    public void testUpdateOrderStatus_Success() {
        // Arrange
        Order order = new Order();
        order.setStatus("Preparing");
        when(orderService.updateOrderStatus(1L, "Preparing")).thenReturn(order);

        // Act
        ResponseEntity<Order> response = orderController.updateOrderStatus(1L, "Preparing");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
        verify(orderService, times(1)).updateOrderStatus(1L, "Preparing");
    }

    @Test
    public void testUpdateOrderStatus_NotFound() {
        // Arrange
        when(orderService.updateOrderStatus(1L, "Preparing")).thenReturn(null);

        // Act
        ResponseEntity<Order> response = orderController.updateOrderStatus(1L, "Preparing");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(orderService, times(1)).updateOrderStatus(1L, "Preparing");
    }

    @Test
    public void testDeleteOrder() {
        // Act
        ResponseEntity<Void> response = orderController.deleteOrder(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(orderService, times(1)).deleteOrder(1L);
    }
}
