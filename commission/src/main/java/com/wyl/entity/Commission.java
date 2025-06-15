package com.wyl.entity;


/*
@作者：wyl  
*/
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@TableName("commission")
public class Commission {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long orderId;
    private Long agentId;
    private LocalDateTime createdAt;
    private BigDecimal amount;
    private BigDecimal orderRuleCommission;

    // 这两个字段是业务计算中间产物，
    // 不对应数据库列，需要加 @TableField(exist = false)
    @TableField(exist = false)
    private Map<String, BigDecimal> ruleResults = new LinkedHashMap<>();

    @TableField(exist = false)
    private BigDecimal totalCommission = BigDecimal.ZERO;

    public void addRuleResult(String ruleName, BigDecimal commission) {
        /* 存储规则名称与对应佣金金额的映射关系 */
        ruleResults.put(ruleName, commission);

        /* 使用BigDecimal的add方法累加总佣金金额
         * 注意：由于BigDecimal对象不可变，每次运算会生成新的对象实例 */
        totalCommission = totalCommission.add(commission);
    }
}