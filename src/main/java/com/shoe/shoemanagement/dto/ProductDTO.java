package com.shoe.shoemanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    private Long id;
    private String productName;
    private String category;
    private Double productPrice;
    private String priceRange;
    private String productPhotoUrl;
    private String productColor;

    private String productDescription;


}
