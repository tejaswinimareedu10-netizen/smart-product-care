package com.example.smartproductcare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.smartproductcare.entity.Product;
import com.example.smartproductcare.entity.Warranty;
import com.example.smartproductcare.service.ProductService;
import com.example.smartproductcare.service.WarrantyService;

@Controller
public class WarrantyController {

    @Autowired
    private WarrantyService warrantyService;

    @Autowired
    private ProductService productService;

    @GetMapping("/warranty/{productId}")
    public String warrantyPage(@PathVariable Long productId, Model model) {
        Product product = productService.getProductById(productId);

        model.addAttribute("product", product);
        model.addAttribute("warranties", warrantyService.getWarrantiesByProduct(product));

        return "warranty";
    }

    @GetMapping("/add-warranty/{productId}")
    public String addWarrantyPage(@PathVariable Long productId, Model model) {
        Product product = productService.getProductById(productId);

        Warranty warranty = new Warranty();
        warranty.setProduct(product);

        model.addAttribute("warranty", warranty);
        model.addAttribute("product", product);

        return "addWarranty";
    }

    @GetMapping("/warranty/add/{productId}")
    public String addWarrantyPageAlias(@PathVariable Long productId, Model model) {
        return addWarrantyPage(productId, model);
    }

    @PostMapping("/save-warranty")
    public String saveWarranty(@ModelAttribute Warranty warranty) {
        warrantyService.saveWarranty(warranty);
        return "redirect:/warranty/" + warranty.getProduct().getProductId();
    }

    @GetMapping("/warranty/delete/{id}")
    public String deleteWarranty(@PathVariable Long id) {
        Warranty warranty = warrantyService.getWarrantyById(id);
        Long productId = warranty.getProduct().getProductId();

        warrantyService.deleteWarranty(id);

        return "redirect:/warranty/" + productId;
    }

    @GetMapping("/delete-warranty/{id}")
    public String deleteWarrantyAlias(@PathVariable Long id) {
        return deleteWarranty(id);
    }
}