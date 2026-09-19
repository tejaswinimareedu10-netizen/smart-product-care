package com.example.smartproductcare.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.smartproductcare.entity.Product;
import com.example.smartproductcare.entity.Warranty;

public interface WarrantyRepository extends JpaRepository<Warranty, Long> {
    
    
    List<Warranty> findByProduct(Product product);

    @Query("SELECT w FROM Warranty w WHERE w.product.productId = :productId")
    List<Warranty> findByProductId(@Param("productId") Long productId);
}