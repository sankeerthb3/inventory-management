package org.example.inventorymanagement.repository;



import org.example.inventorymanagement.entity.StockNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockNotificationRepository extends JpaRepository<StockNotification, Long> {
    void deleteByProductName(String productName);

    Optional<StockNotification> findByProductName(String name);
}
