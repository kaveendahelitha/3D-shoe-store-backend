package com.shoe.shoemanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRes {
    private int statusCode;
    private String role;
    private String message;
    private String token;
    private String expirationTime;
    private String orderConfirmationCode;

    private UserDTO user;
    private ProductDTO product;


    private List<UserDTO> userList;


    private List<ProductDTO> productList;

}
