package com.shoe.shoemanagement.Serviceuser.interfac;


import com.shoe.shoemanagement.dto.LoginRequest;
import com.shoe.shoemanagement.dto.ReqRes;
import com.shoe.shoemanagement.entity.User;

public interface IUserService {
    ReqRes register(User user);

    ReqRes login(LoginRequest loginRequest);

}
