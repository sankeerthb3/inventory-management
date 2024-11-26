package org.example.inventorymanagement.service;

import lombok.RequiredArgsConstructor;
import org.example.inventorymanagement.dto.AddProductRequest;
import org.example.inventorymanagement.dto.RestockRequest;
import org.example.inventorymanagement.dto.StockUpdateRequest;
import org.example.inventorymanagement.entity.Category;
import org.example.inventorymanagement.entity.Product;
import org.example.inventorymanagement.entity.StockNotification;
import org.example.inventorymanagement.repository.CategoryRepository;
import org.example.inventorymanagement.repository.ProductRepository;
import org.example.inventorymanagement.repository.StockNotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ProductRepository productRepository;
    private final StockNotificationRepository notificationRepository;
    private final CategoryRepository categoryRepository;
    /**
     * Updates the stock after a product sale.
     */
    public Product updateStockAfterSale(StockUpdateRequest request) {
        // Fetch the product by SKU
        Optional<Product> productOpt = productRepository.findBySku(request.getSku());

        if (productOpt.isEmpty()) {
            throw new RuntimeException("Product not found with SKU: " + request.getSku());
        }

        Product product = productOpt.get();

        // Validate the quantity sold
        if (request.getQuantitySold() <= 0) {
            throw new IllegalArgumentException("Quantity sold must be greater than zero.");
        }

        if (request.getQuantitySold() > product.getQuantity()) {
            throw new RuntimeException("Insufficient stock. Current stock: " + product.getQuantity());
        }

        // Update the stock
        product.setQuantity(product.getQuantity() - request.getQuantitySold());
        productRepository.save(product);

        // Check if stock falls below the threshold
        if (product.getQuantity() < product.getLowStockThreshold()) {
            StockNotification notification = new StockNotification(
                    null,
                    product.getName(),
                    product.getQuantity(),
                    LocalDateTime.now()
            );
            notificationRepository.save(notification);
        }

        return product;
    }

    public Product restockInventory(RestockRequest request) {
        // Fetch the product by SKU
        Optional<Product> productOpt = productRepository.findBySku(request.getSku());

        if (productOpt.isEmpty()) {
            throw new RuntimeException("Product not found with SKU: " + request.getSku());
        }

        Product product = productOpt.get();

        // Validate the quantity received
        if (request.getQuantityReceived() <= 0) {
            throw new IllegalArgumentException("Quantity received must be greater than zero.");
        }

        // Update the stock level
        product.setQuantity(product.getQuantity() + request.getQuantityReceived());
        productRepository.save(product);

        // Check if the product's stock is now above the low-stock threshold and remove any notifications
        if (product.getQuantity() >= product.getLowStockThreshold()) {
            notificationRepository.deleteByProductName(product.getName());
        }

        return product;
    }

    public Product addNewProduct(AddProductRequest request) {
        Optional<Category> categoryOpt = categoryRepository.findById(request.getCategoryId());
        if (categoryOpt.isEmpty()) {
            throw new RuntimeException("Category not found with ID: " + request.getCategoryId());
        }
        // Check if SKU already exists
        Optional<Product> existingProduct = productRepository.findBySku(request.getSku());
        if (existingProduct.isPresent()) {
            throw new RuntimeException("Product with SKU " + request.getSku() + " already exists.");
        }

        // Validate initial quantity
        if (request.getInitialQuantity() < 0) {
            throw new IllegalArgumentException("Initial quantity must be zero or greater.");
        }

        // Use Builder Pattern to create the Product entity
        Product product = Product.builder()
                .sku(request.getSku())
                .name(request.getName())
                .description(request.getDescription())
                .quantity(request.getInitialQuantity())
                .lowStockThreshold(request.getLowStockThreshold())
                .build();

        // Save and return the new product
        return productRepository.save(product);
    }
}
