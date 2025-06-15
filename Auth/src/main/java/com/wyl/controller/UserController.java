//package com.wyl.controller;
//
//import com.wyl.dto.LoginDTO;
//import com.wyl.dto.RegisterDTO;
//import com.wyl.service.UserService;
//import com.wyl.vo.ResultVo;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///*
//@作者：wyl
//*/
//@RestController
//@RequestMapping("/auth")
//@RequiredArgsConstructor
//public class UserController {
//
//    private final UserService userService;
//
//    /**
//     * 登录接口
//     * @param dto LoginDTO(username, password)
//     * @return JWT Token（封装在 ResultVo 中）
//     */
//    @PostMapping("/login")
//    public ResultVo<String> login(@RequestBody @Valid LoginDTO dto) {
//        String token = userService.login(dto);
//        return ResultVo.success("Bearer " + token);
//    }
//    /*
//    * 注册接口
//    * @param dto RegisterDTO(username, password)
//    */
//    @PostMapping("/register")
//    public ResultVo<String> register(@RequestBody @Valid RegisterDTO dto) {
//        userService.register(dto);
//        return ResultVo.success();
//    }
//    @PostMapping("/logout")
//    public ResultVo<String> logout() {
//        return ResultVo.success();
//    }
//}
//
