package com.shoe.shoemanagement.Serviceuser.interfac;


import com.shoe.shoemanagement.dto.LoginRequest;
import com.shoe.shoemanagement.dto.ReqRes;
import com.shoe.shoemanagement.dto.UserDTO;
import com.shoe.shoemanagement.entity.User;

public interface IUserService {
    ReqRes register(User user);

    ReqRes login(LoginRequest loginRequest);
    ReqRes deleteUser(String userId);

    ReqRes getUserById(String userId);

    ReqRes getMyInfo(String email);

    ReqRes getAllUsers();

    ReqRes updateUserProfile(String email, UserDTO userDto);
}
