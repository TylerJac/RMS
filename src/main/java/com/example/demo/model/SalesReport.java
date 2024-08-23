package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SalesReport {

    private String date;
    private double totalRevenue;
    private String mostPopularItem;
    private String mostActiveTable;
    private int totalOrders;
    private List<Order> orders;

    public SalesReport(String date, double totalRevenue, String mostPopularItem, String mostActiveTable, int totalOrders) {
        this.date = date;
        this.totalRevenue = totalRevenue;
        this.mostPopularItem = mostPopularItem;
        this.mostActiveTable = mostActiveTable;
        this.totalOrders = totalOrders;
    }

    // Getters and Setters

}

