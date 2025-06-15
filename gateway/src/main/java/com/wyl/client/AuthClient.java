//package com.wyl.client;
//
//import com.wyl.dto.LoginDTO;
//import com.wyl.dto.RegisterDTO;
//import com.wyl.vo.ResultVo;
//import jakarta.validation.Valid;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//@FeignClient(name = "auth-service", path = "/auth")
//public interface AuthClient {
// /**
//  * 登录接口
//  * @param dto LoginDTO(username, password)
//  * @return JWT Token（封装在 ResultVo 中）
//  */
// @PostMapping("/login")
// public ResultVo<String> login(@RequestBody @Valid LoginDTO dto);
// /*
//  * 注册接口
//  * @param dto RegisterDTO(username, password)
//  */
// @PostMapping("/register")
// public ResultVo<String> register(@RequestBody @Valid RegisterDTO dto);
//}
//
