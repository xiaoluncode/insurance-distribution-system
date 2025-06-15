package com.wyl.controller;

import com.wyl.dto.LoginDTO;
import com.wyl.dto.RegisterDTO;
import com.wyl.service.UserService;
import com.wyl.vo.ResultVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResultVo<Void> register(@Valid @RequestBody RegisterDTO dto) {
        userService.register(dto);
        return ResultVo.success();
    }

    @PostMapping("/login")
    public ResultVo<String> login(@Valid @RequestBody LoginDTO dto) {
        String token = userService.login(dto);
        return ResultVo.success(token);
    }

    @PostMapping("/logout")
    public ResultVo<Void> logout() {
        // TODO: 后续黑名单，在此处理
        return ResultVo.success();
    }
}