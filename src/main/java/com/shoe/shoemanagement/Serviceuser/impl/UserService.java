package com.shoe.shoemanagement.Serviceuser.impl;


import com.shoe.shoemanagement.Serviceuser.interfac.IUserService;
import com.shoe.shoemanagement.dto.LoginRequest;
import com.shoe.shoemanagement.dto.ReqRes;
import com.shoe.shoemanagement.dto.UserDTO;
import com.shoe.shoemanagement.entity.User;
import com.shoe.shoemanagement.exceptions.OurException;
import com.shoe.shoemanagement.repository.UserRepo;
import com.shoe.shoemanagement.utils.JWTUtils;
import com.shoe.shoemanagement.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder,
                       JWTUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ReqRes register(User user) {
        ReqRes response = new ReqRes();
        try {
            if (user.getRole() == null || user.getRole().isBlank()) {
                user.setRole("USER");
            }
            if (userRepo.existsByEmail(user.getEmail())) {
                throw new OurException(user.getEmail() + " already exists");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepo.save(user);
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);
            response.setStatusCode(200);
            response.setUser(userDTO);
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during user registration: " + e.getMessage());
        }
        return response;
    }

    @Override
    public ReqRes login(LoginRequest loginRequest) {
        ReqRes reqRes = new ReqRes();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepo.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new OurException("User not found"));

            String token = jwtUtils.generateToken(user);
            reqRes.setStatusCode(200);
            reqRes.setToken(token);
            reqRes.setRole(user.getRole());
            reqRes.setExpirationTime("7 Days");
            reqRes.setMessage("Login successful");
        } catch (OurException e) {
            reqRes.setStatusCode(404);
            reqRes.setMessage(e.getMessage());
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred during user login: " + e.getMessage());
        }
        return reqRes;
    }
}
