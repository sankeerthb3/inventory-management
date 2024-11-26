package org.example.inventorymanagement.contoller;

import lombok.RequiredArgsConstructor;
import org.example.inventorymanagement.dto.AddProductRequest;
import org.example.inventorymanagement.dto.RestockRequest;
import org.example.inventorymanagement.dto.StockUpdateRequest;
import org.example.inventorymanagement.entity.Product;
import org.example.inventorymanagement.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * Endpoint to update stock after a sale.
     */
    @PostMapping("/update-sale")
    public ResponseEntity<Product> updateStockAfterSale(@RequestBody StockUpdateRequest request) {
        Product updatedProduct = inventoryService.updateStockAfterSale(request);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @PostMapping("/restock")
    public ResponseEntity<Product> restockInventory(@RequestBody RestockRequest request) {
        Product updatedProduct = inventoryService.restockInventory(request);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @PostMapping("/add-product")
    public ResponseEntity<Product> addNewProduct(@RequestBody AddProductRequest request) {
        Product newProduct = inventoryService.addNewProduct(request);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }
}