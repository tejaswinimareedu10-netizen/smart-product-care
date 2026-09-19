package com.example.smartproductcare.service;

import java.util.List;

import com.example.smartproductcare.entity.ServiceHistory;
import com.example.smartproductcare.entity.Product;

public interface ServiceHistoryService {

    ServiceHistory saveService(ServiceHistory serviceHistory);

    List<ServiceHistory> getAllServices();

    List<ServiceHistory> getServicesByProduct(Product product);

    ServiceHistory getServiceById(Long id);

    void deleteService(Long id);
}