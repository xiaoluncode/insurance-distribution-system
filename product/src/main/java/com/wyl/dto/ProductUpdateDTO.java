package com.wyl.dto;


/*
@作者：wyl  
*/
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateDTO {
    @NotNull(message = "产品ID不能为空")
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;
    private String status;
    private String productType;
    private String channel;
}