package com.shoe.shoemanagement.entity;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderInput {
    private String fullName;
    private String fullAddress;
    private String contactNumber;
    private String alternateContactNumber;

    private List<OrderProductQuantity> orderProductQuantityList;

}
