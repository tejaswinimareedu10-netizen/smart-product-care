package com.example.smartproductcare.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;
    private String brand;
    private String category;
    private String purchaseDate;
    private Integer warrantyMonths;
    private String warrantyExpiry;
    private String receiptPath;
    public Product() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Integer getWarrantyMonths() {
        return warrantyMonths;
    }

    public void setWarrantyMonths(Integer warrantyMonths) {
        this.warrantyMonths = warrantyMonths;
    }

    public String getWarrantyExpiry() {
        return warrantyExpiry;
    }

    public void setWarrantyExpiry(String warrantyExpiry) {
        this.warrantyExpiry = warrantyExpiry;
    }
    public String getReceiptPath() {
        return receiptPath;
    }

    public void setReceiptPath(String receiptPath) {
        this.receiptPath = receiptPath;
    }

    @Transient
    public String getWarrantyStatus() {

        if (warrantyExpiry == null || warrantyExpiry.isEmpty()) {
            return "Not Available";
        }

        LocalDate expiryDate = LocalDate.parse(warrantyExpiry);

        if (expiryDate.isBefore(LocalDate.now())) {
            return "Expired";
        }

        return "Active";
    }

    @Transient
    public long getRemainingDays() {

        if (warrantyExpiry == null || warrantyExpiry.isEmpty()) {
            return 0;
        }

        LocalDate expiryDate = LocalDate.parse(warrantyExpiry);

        if (expiryDate.isBefore(LocalDate.now())) {
            return 0;
        }

        return ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
    }
}