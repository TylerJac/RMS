package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; // Maps to index.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Maps to log in.html
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // Maps to dashboard.html
    }

    @GetMapping("/menu-management")
    public String menuManagement() {
        return "menu-management"; // Maps to menu-management.html
    }

    @GetMapping("/orders")
    public String orderProcessing() {
        return "orders-processing"; // Maps to order-processing.html
    }

    @GetMapping("/table-management")
    public String tableManagement() {
        return "table-management"; // Maps to table-management.html
    }

    @GetMapping("/inventory-management")
    public String inventoryManagement() {
        return "inventory-management"; // Maps to inventory-management.html
    }

    @GetMapping("/sales-report")
    public String salesReport() {
        return "sales-report"; // Maps to sales-report.html
    }

    @GetMapping("/error")
    public String error() {
        return "error"; // Maps to error.html
    }
}

