package org.example.inventorymanagement.contoller;

import lombok.RequiredArgsConstructor;
import org.example.inventorymanagement.dto.CategoryRequest;
import org.example.inventorymanagement.entity.Category;
import org.example.inventorymanagement.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Endpoint to create a new category.
     */
    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody CategoryRequest request) {
        Category newCategory = categoryService.addCategory(request);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    /**
     * Endpoint to get all categories.
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}