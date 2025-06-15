package com.wyl.entity;


/*
@作者：wyl  
*/
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("commission_summary_daily")
public class CommissionSummaryDaily {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long agentId;
    private LocalDate date;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;

}

