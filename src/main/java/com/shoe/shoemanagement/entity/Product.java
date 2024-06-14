package com.shoe.shoemanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private String category;
    private BigDecimal productPrice;
    private String priceRange; // This will be automatically filled based on productPrice
    private String productPhotoUrl;
    private String productColor;
    private String productDescription;


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", productPrice=" + productPrice +
                ", priceRange='" + priceRange + '\'' +
                ", productPhotoUrl='" + productPhotoUrl + '\'' +
                ", productColor='" + productColor + '\'' +
                ", productDescription='" + productDescription + '\'' +
                '}';
    }

    // Method to automatically set priceRange based on productPrice
    @PrePersist
    @PreUpdate
    public void updatePriceRange() {
        if (productPrice != null) {
            if (productPrice.compareTo(new BigDecimal("10000")) > 0) {
                priceRange = "10000+";
            } else if (productPrice.compareTo(new BigDecimal("7000")) > 0) {
                priceRange = "7000-10000";
            } else if (productPrice.compareTo(new BigDecimal("5000")) > 0) {
                priceRange = "5000-7000";
            } else if (productPrice.compareTo(new BigDecimal("3000")) > 0) {
                priceRange = "3000-5000";
            } else if (productPrice.compareTo(new BigDecimal("2000")) > 0) {
                priceRange = "2000-3000";
            } else if (productPrice.compareTo(new BigDecimal("1000")) > 0) {
                priceRange = "1000-2000";
            } else {
                priceRange = "<1000";
            }
        }
    }
}
