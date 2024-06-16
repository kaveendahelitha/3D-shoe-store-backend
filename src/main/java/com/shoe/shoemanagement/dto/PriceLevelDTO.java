package com.shoe.shoemanagement.dto;

import lombok.Data;

@Data
public class PriceLevelDTO {
    private String priceRange;
    private long productCount;

    public PriceLevelDTO(String priceRange, long productCount) {
        this.priceRange = priceRange;
        this.productCount = productCount;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public long getProductCount() {
        return productCount;
    }

    public void setProductCount(long productCount) {
        this.productCount = productCount;
    }
}
