package com.example.smartproductcare.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.smartproductcare.entity.ServiceHistory;
import com.example.smartproductcare.entity.Product;

public interface ServiceHistoryRepository extends JpaRepository<ServiceHistory, Long> {

    List<ServiceHistory> findByProduct(Product product);

    // 👉 ప్రొడక్ట్ ఐడీ ద్వారా సర్వీస్ హిస్టరీని వెతకడానికి ఈ మెథడ్ యాడ్ చేయండి
    List<ServiceHistory> findByProduct_ProductId(Long productId);
}