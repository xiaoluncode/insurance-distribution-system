package com.wyl.rule.impl;

import com.wyl.client.AgentClient;
import com.wyl.entity.Agent;
import com.wyl.entity.Order;
import com.wyl.rule.CommissionRule;
import com.wyl.service.AgentService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/*
@作者：wyl  
*/
@Component
public class NewAgentBonusRule implements CommissionRule {

//    @Resource
//    private AgentService agentService;
    @Autowired
    private AgentClient agentClient;


    @Override
    public BigDecimal calculate(Order order) {
        // 远程调用获取代理
        Agent agent = agentClient.getAgentById(order.getAgentId());
        if (agent == null) {
            return BigDecimal.ZERO;
        }
        long daysSinceJoin = ChronoUnit.DAYS.between(agent.getCreatedAt(), LocalDateTime.now());
        if (daysSinceJoin <= 90) {
            return order.getAmount().multiply(BigDecimal.valueOf(0.05));
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String getRuleName() {
        return "新代理人扶持奖励规则";
    }
}

