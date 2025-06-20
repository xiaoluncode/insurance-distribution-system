package com.wyl.rule.impl;

import com.wyl.entity.Agent;
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
public class ChannelBonusRule implements CommissionRule {
    private final Map<String, BigDecimal> channelRates = new HashMap<>();

    public ChannelBonusRule() {
        channelRates.put("渠道A", new BigDecimal("0.03"));
        channelRates.put("渠道B", new BigDecimal("0.02"));
        channelRates.put("渠道C", new BigDecimal("0.01"));
    }

    @Override
    public BigDecimal calculate(Order order) {
        // 获取渠道奖励比例
        BigDecimal rate = channelRates.getOrDefault(order.getChannel(), BigDecimal.ZERO);
        return order.getAmount().multiply(rate);
    }

    @Override
    public String getRuleName() {
        return "渠道奖励规则";
    }
}
