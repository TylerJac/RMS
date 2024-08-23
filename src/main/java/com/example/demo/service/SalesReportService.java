package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.SalesReport;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class SalesReportService {

    private static final Logger logger = Logger.getLogger(SalesReportService.class.getName());

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
                .flatMap(order -> order.getItem().stream())
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
    public void exportReportToFile(SalesReport report, String fileName) {
        // Specify the directory where the file will be stored
        String filePath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", fileName).toString();

        // Log the file path
        logger.log(Level.INFO, "Attempting to write sales report to: " + filePath);

        // Ensure the directory exists
        File file = new File(filePath);
        try {
            // Create parent directories if they don't exist
            file.getParentFile().mkdirs();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("Sales Report for: " + report.getDate() + "\n");
                writer.write("Total Revenue: $" + report.getTotalRevenue() + "\n");
                writer.write("Most Popular Item: " + report.getMostPopularItem() + "\n");
                writer.write("Table with Most Orders: " + report.getMostActiveTable() + "\n");
                writer.write("Total Orders: " + report.getTotalOrders() + "\n");
                writer.write("------------------------------------------------------------\n");

                writer.write("Order Details:\n");
                for (Order order : report.getOrders()) {
                    writer.write("Order ID: " + order.getId() + ", Items: " + order.getItem() + ", Total Price: $" + order.getTotalPrice() + "\n");
                }
            }

            // Log successful file creation
            logger.log(Level.INFO, "Sales report successfully written to: " + filePath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to write sales report to file: " + filePath, e);
        }
    }
}
