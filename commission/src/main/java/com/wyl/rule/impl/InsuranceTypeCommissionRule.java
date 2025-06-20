package com.wyl.rule.impl;

import com.wyl.entity.Order;
import com.wyl.rule.CommissionRule;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/*
@作者：wyl  
*/
@Component
public class InsuranceTypeCommissionRule implements CommissionRule {
    private final Map<String, BigDecimal> InsuranceType = new HashMap<>();

    public InsuranceTypeCommissionRule() {
        InsuranceType.put("寿险", new BigDecimal("0.1"));
        InsuranceType.put("重疾险", new BigDecimal("0.15"));
        InsuranceType.put("年金险", new BigDecimal("0.08"));
    }

    @Override
    public BigDecimal calculate(Order order) {
        BigDecimal insuranceTypeOrDefault = InsuranceType.getOrDefault(order.getProductType(), BigDecimal.ZERO);
        return order.getAmount().multiply(insuranceTypeOrDefault);
    }

    @Override
    public String getRuleName() {
        return "险种佣金规则";
    }
}
