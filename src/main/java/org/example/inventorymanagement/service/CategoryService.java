package org.example.inventorymanagement.service;

import lombok.RequiredArgsConstructor;
import org.example.inventorymanagement.dto.CategoryRequest;
import org.example.inventorymanagement.entity.Category;
import org.example.inventorymanagement.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Creates a new category.
     */
    public Category addCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return categoryRepository.save(category);
    }

    /**
     * Retrieves all categories.
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}