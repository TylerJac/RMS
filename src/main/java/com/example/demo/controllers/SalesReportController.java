package com.example.demo.controllers;

import com.example.demo.model.SalesReport;
import com.example.demo.service.SalesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.io.IOException;

@Controller
@RequestMapping("/api/sales-report")
public class SalesReportController {

    @Autowired
    private SalesReportService salesReportService;

    // View the daily sales report
    @GetMapping("/view")
    public String viewDailySalesReport(Model model) {
        SalesReport report = salesReportService.generateDailySalesReport();

        // Check if report is not null before adding it to the model
        if (report != null) {
            model.addAttribute("salesReport", report);
        } else {
            // Handle case where report generation failed or no data is available
            model.addAttribute("error", "Sales report data is unavailable.");
        }

        return "sales-report"; // This should point to sales-report.html
    }

    // Export the daily sales report to a text file
    @GetMapping("/export")
    public String exportDailySalesReport(Model model) {
        SalesReport report = salesReportService.generateDailySalesReport();
        salesReportService.exportReportToFile(report, "daily_sales_report.txt");
        model.addAttribute("message", "Sales report exported successfully.");
        return "redirect:/api/sales-report/view?success=true"; // Redirect to view page with success message
    }
}
