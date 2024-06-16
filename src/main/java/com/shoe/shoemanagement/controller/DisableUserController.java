package com.shoe.shoemanagement.controller;


import com.shoe.shoemanagement.entity.DisableUser;
import com.shoe.shoemanagement.repository.DisableUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:5173")
public class DisableUserController {
    @Autowired
    private DisableUserRepository disableUserRepository;

    @PostMapping("/user")
    DisableUser newUser(@RequestBody DisableUser newUser) {
        return disableUserRepository.save(newUser);
    }



}
