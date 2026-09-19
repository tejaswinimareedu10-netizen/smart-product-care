package com.example.smartproductcare.service;

import java.util.List;

import com.example.smartproductcare.entity.Product;

public interface ProductService {

    Product saveProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product updateProduct(Product product);

    void deleteProduct(Long id);

    List<Product> searchProducts(String productName);
}