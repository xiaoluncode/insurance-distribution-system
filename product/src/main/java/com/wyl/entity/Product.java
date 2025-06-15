package com.wyl.entity;


import lombok.Data;

/*
@作者：wyl  
*/
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("product")
public class Product {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;
    private String status;       // "1" 上架, "0" 下架
    private String productType;  // 险种代码/名称
    private String channel;      // 渠道
    private LocalDateTime createdAt;
}

