package com.example.smartproductcare.service;

import java.util.List;

import com.example.smartproductcare.entity.Product;
import com.example.smartproductcare.entity.Warranty;

public interface WarrantyService {

    Warranty saveWarranty(Warranty warranty);

    List<Warranty> getWarrantiesByProduct(Product product);

    Warranty getWarrantyById(Long id);

    void deleteWarranty(Long id);
}