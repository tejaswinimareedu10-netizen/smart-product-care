package com.example.smartproductcare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.smartproductcare.entity.Product;
import com.example.smartproductcare.entity.ServiceHistory;
import com.example.smartproductcare.repository.ServiceHistoryRepository;

@Service
public class ServiceHistoryServiceImpl implements ServiceHistoryService {

    @Autowired
    private ServiceHistoryRepository serviceHistoryRepository;

    @Override
    public ServiceHistory saveService(ServiceHistory serviceHistory) {
        return serviceHistoryRepository.save(serviceHistory);
    }

    @Override
    public List<ServiceHistory> getAllServices() {
        return serviceHistoryRepository.findAll();
    }

    @Override
    public List<ServiceHistory> getServicesByProduct(Product product) {
        return serviceHistoryRepository.findByProduct(product);
    }

    @Override
    public ServiceHistory getServiceById(Long id) {
        return serviceHistoryRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteService(Long id) {
        serviceHistoryRepository.deleteById(id);
    }
}