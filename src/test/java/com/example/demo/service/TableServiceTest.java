package com.example.demo.service;

import com.example.demo.exception.GlobalExceptionHandler;
import com.example.demo.model.RestaurantTable;
import com.example.demo.repository.TableRepository;
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

public class TableServiceTest {

    @Mock
    private TableRepository tableRepository;

    @InjectMocks
    private TableService tableService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllTables() {
        // Arrange
        RestaurantTable table1 = new RestaurantTable();
        RestaurantTable table2 = new RestaurantTable();
        List<RestaurantTable> tables = Arrays.asList(table1, table2);

        when(tableRepository.findAll()).thenReturn(tables);

        // Act
        List<RestaurantTable> result = tableService.getAllTables();

        // Assert
        assertEquals(2, result.size());
        verify(tableRepository, times(1)).findAll();
    }

    @Test
    public void testGetTableById_Success() {
        // Arrange
        RestaurantTable table = new RestaurantTable();
        when(tableRepository.findById(1L)).thenReturn(Optional.of(table));

        // Act
        RestaurantTable result = tableService.getTableById(1L);

        // Assert
        assertEquals(table, result);
        verify(tableRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetTableById_NotFound() {
        // Arrange
        when(tableRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        GlobalExceptionHandler.TableNotFoundException exception = assertThrows(
                GlobalExceptionHandler.TableNotFoundException.class,
                () -> tableService.getTableById(1L)
        );

        assertEquals("Table not found", exception.getMessage());
        verify(tableRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveTable() {
        // Arrange
        RestaurantTable table = new RestaurantTable();
        when(tableRepository.save(table)).thenReturn(table);

        // Act
        RestaurantTable result = tableService.saveTable(table);

        // Assert
        assertEquals(table, result);
        verify(tableRepository, times(1)).save(table);
    }

    @Test
    public void testDeleteTable() {
        // Act
        tableService.deleteTable(1L);

        // Assert
        verify(tableRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testAssignCustomerToTable_Success() {
        // Arrange
        RestaurantTable table = new RestaurantTable();
        table.setStatus(RestaurantTable.TableStatus.AVAILABLE);
        when(tableRepository.findById(1L)).thenReturn(Optional.of(table));

        // Act
        tableService.assignCustomerToTable(1L);

        // Assert
        assertEquals(RestaurantTable.TableStatus.OCCUPIED, table.getStatus());
        verify(tableRepository, times(1)).save(table);
    }

    @Test
    public void testAssignCustomerToTable_AlreadyOccupied() {
        // Arrange
        RestaurantTable table = new RestaurantTable();
        table.setStatus(RestaurantTable.TableStatus.OCCUPIED);
        when(tableRepository.findById(1L)).thenReturn(Optional.of(table));

        // Act & Assert
        GlobalExceptionHandler.TableAlreadyOccupiedException exception = assertThrows(
                GlobalExceptionHandler.TableAlreadyOccupiedException.class,
                () -> tableService.assignCustomerToTable(1L)
        );

        assertEquals("Table is already reserved or occupied", exception.getMessage());
        verify(tableRepository, times(0)).save(table);
    }

    @Test
    public void testAssignCustomerToTable_NotFound() {
        // Arrange
        when(tableRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        GlobalExceptionHandler.TableNotFoundException exception = assertThrows(
                GlobalExceptionHandler.TableNotFoundException.class,
                () -> tableService.assignCustomerToTable(1L)
        );

        assertEquals("Table not found", exception.getMessage());
        verify(tableRepository, times(0)).save(any(RestaurantTable.class));
    }
}
