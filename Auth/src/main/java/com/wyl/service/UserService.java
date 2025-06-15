package com.wyl.service;


/*
@作者：wyl  
*/
import com.wyl.dto.LoginDTO;
import com.wyl.dto.RegisterDTO;

public interface UserService {
    void register(RegisterDTO dto);
    String login(LoginDTO dto);
}
