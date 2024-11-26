package org.example.inventorymanagement.contoller;



import org.example.inventorymanagement.entity.Product;
import org.example.inventorymanagement.entity.StockNotification;
import org.example.inventorymanagement.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * Endpoint to get an overview of the inventory.
     */
    @GetMapping("/overview")
    public ResponseEntity<List<Product>> getInventoryOverview() {
        List<Product> products = dashboardService.getInventoryOverview();
        return ResponseEntity.ok(products);
    }

    /**
     * Endpoint to get low-stock notifications.
     */
    @GetMapping("/low-stock-alerts")
    public ResponseEntity<List<StockNotification>> getLowStockAlerts() {
        List<StockNotification> notifications = dashboardService.checkLowStock();
        return ResponseEntity.ok(notifications);
    }
}
