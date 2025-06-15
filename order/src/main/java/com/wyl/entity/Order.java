package com.wyl.entity;


/*
@作者：wyl  
*/
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("`order`")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String orderNo;
    private Long agentId;
    private Long customerId;
    private Long productId;
    private String status;        // CREATED, PAID, COMPLETED, CANCELLED...
    private BigDecimal amount;
    private String productType;   // 险种
    private String channel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
