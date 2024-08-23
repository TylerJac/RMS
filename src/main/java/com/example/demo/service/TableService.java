package com.example.demo.service;

import com.example.demo.model.RestaurantTable;
import com.example.demo.repository.TableRepository;
import com.example.demo.exception.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {

    private static TableRepository tableRepository;

    @Autowired
    public TableService(TableRepository tableRepository) {
        TableService.tableRepository = tableRepository;
    }

    public  List<RestaurantTable> getAllTables() {
        return tableRepository.findAll();
    }

    public RestaurantTable getTableById(Long id) {
        return tableRepository.findById(id).orElseThrow(() -> new GlobalExceptionHandler.TableNotFoundException("Table not found"));
    }

    public RestaurantTable saveTable(RestaurantTable table) {
        return tableRepository.save(table);
    }

    public void deleteTable(Long id) {
        tableRepository.deleteById(id);
    }

    public void assignCustomerToTable(Long tableId) {
        RestaurantTable table = tableRepository.findById(tableId).orElseThrow(() -> new GlobalExceptionHandler.TableNotFoundException("Table not found"));
        if (table.getStatus() == RestaurantTable.TableStatus.AVAILABLE) {
            table.setStatus(RestaurantTable.TableStatus.OCCUPIED);
            tableRepository.save(table);
        } else {
            throw new GlobalExceptionHandler.TableAlreadyOccupiedException("Table is already reserved or occupied");
        }
    }
}
