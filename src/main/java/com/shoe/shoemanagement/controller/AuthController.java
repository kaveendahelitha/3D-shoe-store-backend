package com.shoe.shoemanagement.controller;

import com.shoe.shoemanagement.Serviceuser.interfac.IUserService;
import com.shoe.shoemanagement.dto.LoginRequest;
import com.shoe.shoemanagement.dto.ReqRes;
import com.shoe.shoemanagement.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IUserService userService;

    @Autowired
    public AuthController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ReqRes> register(@RequestBody User user) {
        ReqRes reqRes = userService.register(user);
        return ResponseEntity.status(reqRes.getStatusCode()).body(reqRes);
    }

    @PostMapping("/login")
    public ResponseEntity<ReqRes> login(@RequestBody LoginRequest loginRequest) {
        ReqRes reqRes = userService.login(loginRequest);
        return ResponseEntity.status(reqRes.getStatusCode()).body(reqRes);
    }
}
