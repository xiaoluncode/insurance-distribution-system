package com.wyl.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterDTO {
 @NotBlank(message = "用户名不能为空")
 private String username;

 @NotBlank(message = "密码不能为空")
 private String password;

 @NotBlank(message = "角色不能为空")
 private String role;  // ADMIN, AGENT, USER
}
