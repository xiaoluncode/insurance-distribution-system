package com.wyl.dto;


/*
@作者：wyl  
*/
import lombok.Data;

@Data
public class ProductQueryDTO {
    private String name;
    private String status;
    private String productType;
    private String channel;
    private Integer page = 1;
    private Integer size = 10;
}