package com.shoe.shoemanagement.controller;

import com.shoe.shoemanagement.dto.RequestResponse;
import com.shoe.shoemanagement.entity.SystemUsers;
import com.shoe.shoemanagement.service.UserManegementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserManagementController {

    @Autowired
    private UserManegementService userManegementService;

    @PostMapping("/auth/register")
    public ResponseEntity<RequestResponse> register(@RequestBody RequestResponse reg){
        return ResponseEntity.ok(userManegementService.register(reg));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<RequestResponse> login(@RequestBody RequestResponse req){
        return ResponseEntity.ok(userManegementService.login(req));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<RequestResponse> refreshToken(@RequestBody RequestResponse req){
        return ResponseEntity.ok(userManegementService.refreshToken(req));
    }


    @GetMapping("/admin/get-all-users")
    public ResponseEntity<RequestResponse> getAllUsers(){
        return ResponseEntity.ok(userManegementService.getAllUsers());

    }

    @GetMapping("/admin/get-users/{userId}")
    public ResponseEntity<RequestResponse> getUSerByID(@PathVariable Integer userId){
        return ResponseEntity.ok(userManegementService.getUsersById(userId));

    }

    @PutMapping("/admin/update/{userId}")
    public ResponseEntity<RequestResponse> updateUser(@PathVariable Integer userId, @RequestBody SystemUsers reqres){
        return ResponseEntity.ok(userManegementService.updateUser(userId, reqres));
    }

    @GetMapping("/adminuser/get-profile")
    public ResponseEntity<RequestResponse> getMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        RequestResponse response = userManegementService.getMyInfo(email);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<RequestResponse> deleteUSer(@PathVariable Integer userId){
        return ResponseEntity.ok(userManegementService.deleteUser(userId));
    }

}
