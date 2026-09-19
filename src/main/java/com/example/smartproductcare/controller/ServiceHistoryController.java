package com.example.smartproductcare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.smartproductcare.entity.Product;
import com.example.smartproductcare.entity.ServiceHistory;
import com.example.smartproductcare.service.ProductService;
import com.example.smartproductcare.service.ServiceHistoryService;

@Controller
public class ServiceHistoryController {

    @Autowired
    private ServiceHistoryService serviceHistoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/service-history/{productId}")
    public String serviceHistory(@PathVariable Long productId, Model model) {

        Product product = productService.getProductById(productId);

        model.addAttribute("product", product);
        model.addAttribute("services",
                serviceHistoryService.getServicesByProduct(product));

        return "serviceHistory";
    }

    @GetMapping("/add-service/{productId}")
    public String addServicePage(@PathVariable Long productId, Model model) {

        Product product = productService.getProductById(productId);

        ServiceHistory serviceHistory = new ServiceHistory();
        serviceHistory.setProduct(product);

        model.addAttribute("serviceHistory", serviceHistory);
        model.addAttribute("product", product);

        return "addService";
    }

    @PostMapping("/save-service")
    public String saveService(
            @ModelAttribute ServiceHistory serviceHistory) {

        serviceHistoryService.saveService(serviceHistory);

        return "redirect:/service-history/"
                + serviceHistory.getProduct().getProductId();
    }

    @GetMapping("/delete-service/{id}")
    public String deleteService(@PathVariable Long id) {

        ServiceHistory service = serviceHistoryService.getServiceById(id);

        Long productId = service.getProduct().getProductId();

        serviceHistoryService.deleteService(id);

        return "redirect:/service-history/" + productId;
    }
}