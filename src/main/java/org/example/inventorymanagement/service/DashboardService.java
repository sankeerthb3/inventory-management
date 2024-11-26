package org.example.inventorymanagement.service;



import org.example.inventorymanagement.entity.Product;
import org.example.inventorymanagement.entity.StockNotification;
import org.example.inventorymanagement.repository.ProductRepository;
import org.example.inventorymanagement.repository.StockNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class
DashboardService {

    private final ProductRepository productRepository;
    private final StockNotificationRepository notificationRepository;

    /**
     * Fetch an overview of the current inventory status.
     */
    public List<Product> getInventoryOverview() {
        return productRepository.findAll();
    }

    /**
     * Fetch a list of low-stock products and generate notifications.
     */
    public List<StockNotification> checkLowStock() {
        List<Product> lowStockProducts = productRepository.findByQuantityLessThan(10);
        for (Product product : lowStockProducts) {
            Optional<StockNotification> existingNotification = notificationRepository.findByProductName(product.getName());

            if (existingNotification.isEmpty()) {
                StockNotification notification = new StockNotification(
                        null,
                        product.getName(),
                        product.getQuantity(),
                        LocalDateTime.now()
                );
                notificationRepository.save(notification);
            }
        }
        return notificationRepository.findAll();
    }
}
