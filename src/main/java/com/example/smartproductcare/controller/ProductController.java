package com.example.smartproductcare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.smartproductcare.entity.Product;
import com.example.smartproductcare.service.ProductService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate; // 👉 Import LocalDate for calculation

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    private final String UPLOAD_DIR = "uploads/";

    // 1. Show Product List Page
    @GetMapping({"/products", "/product/list"})
    public String viewProductList(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "productList";
    }

    // 2. Show Add Product Form Page
    @GetMapping({"/products/add", "/add-product"})
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "addProduct";
    }

    // 3. Save Product, Calculate Expiry Date, and Handle File Upload
    @PostMapping({"/products/add", "/save-product"})
    public String saveProduct(@ModelAttribute Product product,
                              @RequestParam(value = "file", required = false) MultipartFile file) {

        // 👉 Automatic calculation of Warranty Expiry based on Purchase Date + Warranty Months
        if (product.getPurchaseDate() != null && !product.getPurchaseDate().isEmpty() 
                && product.getWarrantyMonths() != null) {
            try {
                LocalDate purchaseDate = LocalDate.parse(product.getPurchaseDate());
                LocalDate expiryDate = purchaseDate.plusMonths(product.getWarrantyMonths());
                product.setWarrantyExpiry(expiryDate.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (file != null && !file.isEmpty()) {
            try {
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                product.setReceiptPath(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        productService.saveProduct(product);
        return "redirect:/products";
    }

    // 4. Delete Product by ID
    @GetMapping({"/products/delete/{id}", "/delete-product/{id}"})
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    // 5. Show Edit Product Form
    @GetMapping("/products/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "addProduct";
    }
}