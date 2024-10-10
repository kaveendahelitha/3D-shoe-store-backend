package com.shoe.shoemanagement.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {


    private int id;

    @NotBlank(message = "First name is required")
    private String userFirstname;

    @NotBlank(message = "Last name is required")
    private String userLastname;

    @NotBlank(message = "Phone Number is required")
    @Size(min = 10, max = 15, message = "Phone Number should be between 10 and 15 characters")
    private String phoneNumber;

    private String address;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private String role;


}
