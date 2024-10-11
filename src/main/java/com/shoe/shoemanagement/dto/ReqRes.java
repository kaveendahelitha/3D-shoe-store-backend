package com.shoe.shoemanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.shoe.shoemanagement.entity.User;
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
    private String error;
    //private UserDTO user;
    private ProductDTO product;
    private String refreshToken;
    private String email;
    private String password;
    private String name;
    private User User;
    private List<User> UserList;
    private List<ProductDTO> productList;





}
