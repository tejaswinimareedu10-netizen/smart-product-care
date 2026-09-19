package com.example.smartproductcare.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.smartproductcare.entity.Product;
import com.example.smartproductcare.entity.Warranty;
import com.example.smartproductcare.repository.WarrantyRepository;

@Service
public class WarrantyServiceImpl implements WarrantyService {

    @Autowired
    private WarrantyRepository warrantyRepository;

    @Override
    public Warranty saveWarranty(Warranty warranty) {
        return warrantyRepository.save(warranty);
    }

    @Override
    public List<Warranty> getWarrantiesByProduct(Product product) {
        if (product != null && product.getProductId() != null) {
            return warrantyRepository.findByProductId(product.getProductId());
        }
        return warrantyRepository.findByProduct(product);
    }

    @Override
    public Warranty getWarrantyById(Long id) {
        return warrantyRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteWarranty(Long id) {
        warrantyRepository.deleteById(id);
    }
}