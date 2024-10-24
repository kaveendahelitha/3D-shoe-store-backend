package com.shoe.shoemanagement.Serviceuser.impl;


import com.shoe.shoemanagement.Serviceuser.EmployeeService;
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
import java.util.Optional;


@Service
public class UserService implements IUserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    private final EmployeeService employeeService;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder,
                       JWTUtils jwtUtils, AuthenticationManager authenticationManager,EmployeeService employeeService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.employeeService = employeeService;
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

            if ("EMPLOYEE".equalsIgnoreCase(user.getRole())) {
                employeeService.createEmployee(savedUser, user.getUserFirstname(), user.getUserLastname(), user.getEmail());
            }

            UserDTO userdto = Utils.mapUserEntityToUserDTO(savedUser);
            response.setStatusCode(200);
            response.setUser(savedUser);
        } catch (ConstraintViolationException e) {
            response.setStatusCode(400);
            response.setMessage("Validation error: " + e.getMessage());

            response.setUser(user);

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
            response.setUser(updatedUser);
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
            userRepo.findById(Math.toIntExact(id)).orElseThrow(() -> new OurException("User not found"));
            userRepo.deleteById(Math.toIntExact(id));
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
            User user = userRepo.findById(Math.toIntExact(id))
                    .orElseThrow(() -> new OurException("User not found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("User found successfully");

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


  // user indo in controller
    public ReqRes getMyInfo(String email){
        ReqRes reqRes = new ReqRes();
        try {
            Optional<User> userOptional = userRepo.findByEmail(email);
            if (userOptional.isPresent()) {
                reqRes.setUser(userOptional.get());
                reqRes.setStatusCode(200);
                reqRes.setMessage("successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }

        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return reqRes;

    }

    //update user info
    public ReqRes updateUser(Integer userId, User updatedUser) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<User> userOptional = userRepo.findById(userId);
            if (userOptional.isPresent()) {
                User existingUser = userOptional.get();
                existingUser.setEmail(updatedUser.getEmail());
                //existingUser.setName(updatedUser.getName());
                existingUser.setRole(updatedUser.getRole());

                // Check if password is present in the request
                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    // Encode the password and update it
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }

                User savedUser = userRepo.save(existingUser);
                reqRes.setUser(savedUser);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while updating user: " + e.getMessage());
        }
        return reqRes;
    }

    public ReqRes getAllUsers() {
        ReqRes reqRes = new ReqRes();

        try {
            List<User> result = userRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setUserList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No users found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }

    public ReqRes getUsersById(Integer id) {
        ReqRes reqRes = new ReqRes();
        try {
            User usersById = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
            reqRes.setUser(usersById);
            reqRes.setStatusCode(200);
            reqRes.setMessage("Users with id '" + id + "' found successfully");
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }
        return reqRes;
    }

    public ReqRes deleteUser(Integer userId) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<User> userOptional = userRepo.findById(userId);
            if (userOptional.isPresent()) {
                userRepo.deleteById(userId);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return reqRes;

    }
}
