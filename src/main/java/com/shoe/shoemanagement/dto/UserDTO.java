package com.shoe.shoemanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private int id;
    private String userFirstname;
    private String userLastname;
    private String address;
    private String email;
    private String password;
    private String role;


}
