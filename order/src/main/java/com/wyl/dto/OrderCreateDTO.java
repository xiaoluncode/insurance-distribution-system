package com.wyl.dto;


/*
@作者：wyl  
*/
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderCreateDTO {
    @NotNull(message = "agentId 不能为空")
    private Long agentId;

    private String orderNo;
    private String status = "待核保";
    private String createdAt;
    private String updatedAt;

    @NotNull(message = "customerId 不能为空")
    private Long customerId;

    @NotNull(message = "productId 不能为空")
    private Long productId;

    @NotNull(message = "金额不能为空")
    private BigDecimal amount;

    private String productType ;
    private String channel ;
}
