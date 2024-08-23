package com.example.demo.service;

import com.example.demo.model.InventoryItem;
import com.example.demo.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllItems() {
        // Arrange
        InventoryItem item1 = new InventoryItem();
        InventoryItem item2 = new InventoryItem();
        List<InventoryItem> items = Arrays.asList(item1, item2);

        when(inventoryRepository.findAll()).thenReturn(items);

        // Act
        List<InventoryItem> result = inventoryService.getAllItems();

        // Assert
        assertEquals(2, result.size());
        verify(inventoryRepository, times(1)).findAll();
    }

    @Test
    public void testGetItemById_Success() {
        // Arrange
        InventoryItem item = new InventoryItem();
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(item));

        // Act
        InventoryItem result = inventoryService.getItemById(1L);

        // Assert
        assertEquals(item, result);
        verify(inventoryRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetItemById_NotFound() {
        // Arrange
        when(inventoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        InventoryItem result = inventoryService.getItemById(1L);

        // Assert
        assertNull(result);
        verify(inventoryRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddItem() {
        // Arrange
        InventoryItem item = new InventoryItem();
        when(inventoryRepository.save(item)).thenReturn(item);

        // Act
        InventoryItem result = inventoryService.addItem(item);

        // Assert
        assertEquals(item, result);
        verify(inventoryRepository, times(1)).save(item);
    }

    @Test
    public void testUpdateItem_Success() {
        // Arrange
        InventoryItem existingItem = new InventoryItem();
        InventoryItem updatedItem = new InventoryItem();
        updatedItem.setName("Updated Name");
        updatedItem.setQuantity(50);

        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(existingItem));
        when(inventoryRepository.save(any(InventoryItem.class))).thenReturn(existingItem);

        // Act
        InventoryItem result = inventoryService.updateItem(1L, updatedItem);

        // Assert
        assertEquals("Updated Name", existingItem.getName());
        assertEquals(50, existingItem.getQuantity());
        verify(inventoryRepository, times(1)).findById(1L);
        verify(inventoryRepository, times(1)).save(existingItem);
    }

    @Test
    public void testUpdateItem_NotFound() {
        // Arrange
        InventoryItem updatedItem = new InventoryItem();
        when(inventoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        InventoryItem result = inventoryService.updateItem(1L, updatedItem);

        // Assert
        assertNull(result);
        verify(inventoryRepository, times(1)).findById(1L);
        verify(inventoryRepository, times(0)).save(any(InventoryItem.class));
    }

    @Test
    public void testDeleteItem() {
        // Act
        inventoryService.deleteItem(1L);

        // Assert
        verify(inventoryRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testIsItemAvailable_Success() {
        // Arrange
        InventoryItem item = new InventoryItem();
        item.setQuantity(10);
        when(inventoryRepository.findByName("Item1")).thenReturn(Optional.of(item));

        // Act
        boolean result = inventoryService.isItemAvailable("Item1", 5);

        // Assert
        assertTrue(result);
        verify(inventoryRepository, times(1)).findByName("Item1");
    }

    @Test
    public void testIsItemAvailable_NotAvailable() {
        // Arrange
        when(inventoryRepository.findByName("Item1")).thenReturn(Optional.empty());

        // Act
        boolean result = inventoryService.isItemAvailable("Item1", 5);

        // Assert
        assertFalse(result);
        verify(inventoryRepository, times(1)).findByName("Item1");
    }

    @Test
    public void testUpdateItemQuantity_Success() {
        // Arrange
        InventoryItem item = new InventoryItem();
        item.setQuantity(10);
        when(inventoryRepository.findByName("Item1")).thenReturn(Optional.of(item));

        // Act
        inventoryService.updateItemQuantity("Item1", 5);

        // Assert
        assertEquals(5, item.getQuantity());
        verify(inventoryRepository, times(1)).findByName("Item1");
        verify(inventoryRepository, times(1)).save(item);
    }

    @Test
    public void testUpdateItemQuantity_InsufficientStock() {
        // Arrange
        InventoryItem item = new InventoryItem();
        item.setQuantity(3);
        when(inventoryRepository.findByName("Item1")).thenReturn(Optional.of(item));

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            inventoryService.updateItemQuantity("Item1", 5);
        });

        assertEquals("Inventory cannot be negative for item: Item1", exception.getMessage());
        verify(inventoryRepository, times(1)).findByName("Item1");
        verify(inventoryRepository, times(0)).save(any(InventoryItem.class));
    }
}
