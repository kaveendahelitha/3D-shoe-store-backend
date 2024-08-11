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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;

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
            // Validate the user object
            if (user.getRole() == null || user.getRole().isBlank()) {
                user.setRole("USER");
            }
            if (userRepo.existsByEmail(user.getEmail())) {
                throw new OurException("Email " + user.getEmail() + " already exists");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepo.save(user);
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);
            response.setStatusCode(200);
            response.setUser(userDTO);
        } catch (ConstraintViolationException e) {
            response.setStatusCode(400);
            response.setMessage("Validation error: " + e.getMessage());
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

    @Override
    public ReqRes getAllUsers() {
        ReqRes response = new ReqRes();
        try {
            List<User> userList = userRepo.findAll();
            List<UserDTO> userDTOList = Utils.mapUserListEntityToUserListDTO(userList);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setUserList(userDTOList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all users: " + e.getMessage());
        }
        return response;
    }

    @Override
    public ReqRes updateUserProfile(String email, UserDTO userDto) {
        ReqRes response = new ReqRes();
        try {
            User existingUser = userRepo.findByEmail(email)
                    .orElseThrow(() -> new OurException("User not found"));

            if (userDto.getUserFirstname() != null) existingUser.setUserFirstname(userDto.getUserFirstname());
            if (userDto.getUserLastname() != null) existingUser.setUserLastname(userDto.getUserLastname());
            if (userDto.getPhoneNumber() != null && !userDto.getPhoneNumber().isBlank()) existingUser.setPhoneNumber(userDto.getPhoneNumber());
            if (userDto.getAddress() != null) existingUser.setAddress(userDto.getAddress());
            if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) existingUser.setEmail(userDto.getEmail());

            // Update password if provided
            if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
                existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }

            User updatedUser = userRepo.save(existingUser);
            UserDTO updatedUserDTO = Utils.mapUserEntityToUserDTO(updatedUser);

            response.setStatusCode(200);
            response.setMessage("User profile updated successfully");
            response.setUser(updatedUserDTO);
        } catch (ConstraintViolationException e) {
            response.setStatusCode(400);
            response.setMessage("Validation error: " + e.getMessage());
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Internal server error: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for detailed debugging
        }
        return response;
    }

    @Override
    public ReqRes deleteUser(String userId) {
        ReqRes response = new ReqRes();
        try {
            Long id = Long.parseLong(userId);
            userRepo.findById(id).orElseThrow(() -> new OurException("User not found"));
            userRepo.deleteById(id);
            response.setStatusCode(200);
            response.setMessage("User deleted successfully");
        } catch (NumberFormatException e) {
            response.setStatusCode(400);
            response.setMessage("Invalid user ID format");
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting user: " + e.getMessage());
        }
        return response;
    }

    @Override
    public ReqRes getUserById(String userId) {
        ReqRes response = new ReqRes();
        try {
            Long id = Long.parseLong(userId);
            User user = userRepo.findById(id)
                    .orElseThrow(() -> new OurException("User not found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("User found successfully");
            response.setUser(userDTO);
        } catch (NumberFormatException e) {
            response.setStatusCode(400);
            response.setMessage("Invalid user ID format");
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting user by ID: " + e.getMessage());
        }
        return response;
    }

    @Override
    public ReqRes getMyInfo(String email) {
        ReqRes response = new ReqRes();
        try {
            User user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new OurException("User not found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("User profile retrieved successfully");
            response.setUser(userDTO);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving user profile: " + e.getMessage());
        }
        return response;
    }
}
