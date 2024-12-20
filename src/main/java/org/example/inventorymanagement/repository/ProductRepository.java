package org.example.inventorymanagement.repository;



import org.example.inventorymanagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByQuantityLessThan(int threshold);

    Optional<Product> findBySku(String sku);
}
