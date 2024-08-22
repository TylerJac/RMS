package com.example.demo.controllers;

import com.example.demo.model.SalesReport;
import com.example.demo.service.SalesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/sales-report")
public class SalesReportController {

    @Autowired
    private SalesReportService salesReportService;

    // View the daily sales report
    @GetMapping("/view")
    public SalesReport viewDailySalesReport() {
        return salesReportService.generateDailySalesReport();
    }

    // Export the daily sales report to a text file
    @GetMapping("/export")
    public String exportDailySalesReport() {
        SalesReport report = salesReportService.generateDailySalesReport();
        try {
            salesReportService.exportReportToFile(report, "daily_sales_report.txt");
            return "Sales report exported successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to export sales report.";
        }
    }
}
