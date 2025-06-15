package com.wyl.dto;


/*
@作者：wyl  
*/

import lombok.Data;

@Data
public class OrderQueryDTO {
    private Long agentId;
    private Long customerId;
    private String status;
    private Integer page = 1;
    private Integer size = 10;
}
