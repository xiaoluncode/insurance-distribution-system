package com.wyl.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
@作者：wyl  
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken {
    private String token;
    private String jti;
}
