package com.example.smartproductcare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.smartproductcare.service.ProductService;

@Controller
public class DashboardController {

    private final ProductService productService;

    public DashboardController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("products",
                productService.getAllProducts());

        model.addAttribute("totalProducts",
                productService.getAllProducts().size());

        return "dashboard";
    }
}