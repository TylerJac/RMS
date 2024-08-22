package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.SalesReport;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SalesReportService {

    @Autowired
    private OrderRepository orderRepository;

    // Generate daily sales report
    public SalesReport generateDailySalesReport() {
        String currentDate = LocalDate.now().toString(); // Get today's date

        // Get all orders for today (assuming you store order date in Order model)
        List<Order> orders = orderRepository.findAll();  // You can filter by date if needed

        double totalRevenue = orders.stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();

        // Calculate most popular items
        Map<String, Long> itemFrequency = orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(Object::toString, Collectors.counting()));

        String mostPopularItem = itemFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No Items");

        // Calculate tables with most orders (assuming a "tableNumber" field in Order)
        Map<String, Long> tableOrderCount = orders.stream()
                .collect(Collectors.groupingBy(Order::getStatus, Collectors.counting()));

        String mostActiveTable = tableOrderCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No Tables");

        // Create the SalesReport object
        SalesReport report = new SalesReport(currentDate, totalRevenue, mostPopularItem, mostActiveTable, orders.size());
        report.setOrders(orders);

        return report;
    }

    // Export sales report to a text file
    public void exportReportToFile(SalesReport report, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Sales Report for: " + report.getDate() + "\n");
            writer.write("Total Revenue: $" + report.getTotalRevenue() + "\n");
            writer.write("Most Popular Item: " + report.getMostPopularItem() + "\n");
            writer.write("Table with Most Orders: " + report.getMostActiveTable() + "\n");
            writer.write("Total Orders: " + report.getTotalOrders() + "\n");
            writer.write("------------------------------------------------------------\n");

            writer.write("Order Details:\n");
            for (Order order : report.getOrders()) {
                writer.write("Order ID: " + order.getId() + ", Items: " + order.getItems() + ", Total Price: $" + order.getTotalPrice() + "\n");
            }
        }
    }
}

