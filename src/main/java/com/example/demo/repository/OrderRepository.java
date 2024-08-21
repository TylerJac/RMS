package com.example.demo.repository;

import com.example.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing CRUD operations on Order entities.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
