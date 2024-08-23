package com.example.demo.controller;

import com.example.demo.controllers.TableController;
import com.example.demo.model.RestaurantTable;
import com.example.demo.service.TableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TableControllerTest {

    @Mock
    private TableService tableService;

    @InjectMocks
    private TableController tableController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllTables() {
        // Arrange
        RestaurantTable table1 = new RestaurantTable(4, RestaurantTable.TableStatus.AVAILABLE);
        RestaurantTable table2 = new RestaurantTable(6, RestaurantTable.TableStatus.RESERVED);
        List<RestaurantTable> tables = Arrays.asList(table1, table2);

        when(TableService.getAllTables()).thenReturn(tables);

        // Act
        List<RestaurantTable> result = TableService.getAllTables();

        // Assert
        assertEquals(2, result.size());
        assertEquals(RestaurantTable.TableStatus.AVAILABLE, result.getFirst().getStatus());
        verify(tableService, times(1));
        TableService.getAllTables();
    }


    @Test
    public void testGetTableById_Success() {
        // Arrange
        RestaurantTable table = new RestaurantTable();
        when(tableService.getTableById(1L)).thenReturn(table);

        // Act
        ResponseEntity<RestaurantTable> response = tableController.getTableById(1L);

        // Assert
        assertEquals(ResponseEntity.ok(table), response);
        verify(tableService, times(1)).getTableById(1L);
    }

    @Test
    public void testGetTableById_NotFound() {
        // Arrange
        when(tableService.getTableById(1L)).thenReturn(null);

        // Act
        ResponseEntity<RestaurantTable> response = tableController.getTableById(1L);

        // Assert
        assertEquals(ResponseEntity.notFound().build(), response);
        verify(tableService, times(1)).getTableById(1L);
    }

    @Test
    public void testCreateTable() {
        // Arrange
        RestaurantTable table = new RestaurantTable(4, RestaurantTable.TableStatus.AVAILABLE);
        when(tableService.saveTable(any(RestaurantTable.class))).thenReturn(table);

        // Act
        RestaurantTable result = tableController.createTable(table);

        // Assert
        assertEquals(4, result.getSize());
        assertEquals(RestaurantTable.TableStatus.AVAILABLE, result.getStatus());
        verify(tableService, times(1)).saveTable(table);
    }

    @Test
    public void testDeleteTable() {
        // Act
        ResponseEntity<Void> response = tableController.deleteTable(1L);

        // Assert
        assertEquals(ResponseEntity.ok().build(), response);
        verify(tableService, times(1)).deleteTable(1L);
    }
}
