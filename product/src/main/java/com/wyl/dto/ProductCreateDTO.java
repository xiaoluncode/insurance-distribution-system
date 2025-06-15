package com.wyl.dto;


/*
@作者：wyl  
*/
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductCreateDTO {
    @NotBlank(message = "产品名称不能为空")
    private String name;

    private String description;

    @NotNull(message = "价格不能为空")
    private BigDecimal price;

    private String productType = "DEFAULT";
    private String channel     = "ONLINE";
}
