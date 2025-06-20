package com.wyl.rule;


import com.wyl.entity.Commission;
import com.wyl.entity.Order;
import com.wyl.rule.impl.*;
import com.wyl.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
@作者：wyl  
*/
@Component
public class CommissionRuleChain {

    private AgentService agentService;

    private final List<CommissionRule> rules;

    @Autowired
    public CommissionRuleChain(List<CommissionRule> rules) {
        this.rules = rules;
    }

    public Commission calculateCommission(Order order) {
        Commission result = new Commission();
        result.setOrderId(order.getId());
        result.setAgentId(order.getAgentId());
        result.setAmount(order.getAmount());
        result.setCreatedAt(LocalDateTime.now());

        for (CommissionRule rule : rules) {
            BigDecimal commission = rule.calculate(order);
            result.addRuleResult(rule.getRuleName(), commission);
        }

        return result;
    }
}