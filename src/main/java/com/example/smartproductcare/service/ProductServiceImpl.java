package com.example.smartproductcare.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.smartproductcare.entity.Product;
import com.example.smartproductcare.entity.ServiceHistory;
import com.example.smartproductcare.entity.Warranty;
import com.example.smartproductcare.repository.ProductRepository;
import com.example.smartproductcare.repository.ServiceHistoryRepository;
import com.example.smartproductcare.repository.WarrantyRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarrantyRepository warrantyRepository;

    @Autowired
    private ServiceHistoryRepository serviceHistoryRepository;

    @Override
    public Product saveProduct(Product product) {
        calculateWarrantyExpiry(product);
        Product savedProduct = productRepository.save(product);
        saveAutomaticWarranty(savedProduct);
        return savedProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("Product not found for id :: " + id);
        }
    }

    @Override
    public Product updateProduct(Product product) {
        calculateWarrantyExpiry(product);
        Product updatedProduct = productRepository.save(product);
        saveAutomaticWarranty(updatedProduct);
        return updatedProduct;
    }

    @Override
    public void deleteProduct(Long id) {
        // 1. ముందుగా సంబంధిత Warranties ని డిలీట్ చేయాలి
        List<Warranty> warranties = warrantyRepository.findByProductId(id);
        if (warranties != null && !warranties.isEmpty()) {
            warrantyRepository.deleteAll(warranties);
        }
        
        // 2. ఆ తర్వాత సంబంధిత Service History రికార్డ్స్‌ని డిలీట్ చేయాలి (Foreign Key ఎర్రర్ రాకుండా)
        List<ServiceHistory> serviceHistories = serviceHistoryRepository.findByProduct_ProductId(id);
        if (serviceHistories != null && !serviceHistories.isEmpty()) {
            serviceHistoryRepository.deleteAll(serviceHistories);
        }
        
        // 3. చివరగా ప్రొడక్ట్‌ని విజయవంతంగా డిలీట్ చేయాలి
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> searchProducts(String productName) {
        List<Product> allProducts = productRepository.findAll();
        if (productName == null || productName.trim().isEmpty()) {
            return allProducts;
        }
        return allProducts.stream()
                .filter(p -> p.getProductName() != null && 
                           p.getProductName().toLowerCase().contains(productName.toLowerCase()))
                .collect(Collectors.toList());
    }

    private void calculateWarrantyExpiry(Product product) {
        if (product.getPurchaseDate() != null && !product.getPurchaseDate().isEmpty()) {
            try {
                LocalDate purchaseDate = LocalDate.parse(product.getPurchaseDate());
                int months = (product.getWarrantyMonths() != null) ? product.getWarrantyMonths() : 12;
                product.setWarrantyMonths(months);
                
                LocalDate expiryDate = purchaseDate.plusMonths(months);
                product.setWarrantyExpiry(expiryDate.toString());
            } catch (Exception e) {
                product.setWarrantyExpiry(null);
            }
        }
    }

    private void saveAutomaticWarranty(Product product) {
        if (product.getProductId() != null && product.getPurchaseDate() != null && !product.getPurchaseDate().isEmpty() 
                && product.getWarrantyExpiry() != null && !product.getWarrantyExpiry().isEmpty()) {
            try {
                List<Warranty> existingWarranties = warrantyRepository.findByProductId(product.getProductId());
                Warranty warranty;
                
                if (existingWarranties != null && !existingWarranties.isEmpty()) {
                    warranty = existingWarranties.get(0);
                } else {
                    warranty = new Warranty();
                    warranty.setProduct(product);
                }
                
                warranty.setStartDate(LocalDate.parse(product.getPurchaseDate()));
                warranty.setEndDate(LocalDate.parse(product.getWarrantyExpiry()));
                warranty.setStatus(product.getWarrantyStatus() != null ? product.getWarrantyStatus() : "Active");
                
                warrantyRepository.save(warranty);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}