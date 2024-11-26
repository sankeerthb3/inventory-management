package org.example.inventorymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductRequest {
    private String sku;
    private String name;
    private String description;
    private int initialQuantity;
    private int lowStockThreshold;
    private Long categoryId;
}
