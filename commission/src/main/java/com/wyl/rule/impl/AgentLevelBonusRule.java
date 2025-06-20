package com.wyl.rule.impl;

//import com.wyl.client.AgentClient;
import com.wyl.client.AgentClient;
import com.wyl.entity.Order;
import com.wyl.rule.CommissionRule;
import com.wyl.service.AgentService;
import com.wyl.service.impl.AgentServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;

/*
@作者：wyl  
*/
@Component
public class AgentLevelBonusRule implements CommissionRule {
//    @Resource
//    private  AgentService agentService;

//    @Resource./
    @Autowired
    private AgentClient agentClient;

    private final HashMap<String, BigDecimal> levelMap = new HashMap<>();

    public AgentLevelBonusRule(){
        levelMap.put("专家", new BigDecimal("0.05"));
        levelMap.put("高级", new BigDecimal("0.03"));
        levelMap.put("普通", new BigDecimal("0.01"));
    }
    @Override
    public BigDecimal calculate(Order order) {
        Long agentId = order.getAgentId();
        //根据代理人id远程调用agentcontroller方法
        String level = agentClient.getLevelById(agentId);
        // 从levelMap中获取指定level对应的利率值，若未找到则返回零值(BigDecimal.ZERO)
        BigDecimal rate = levelMap.getOrDefault(level, BigDecimal.ZERO);
        return order.getAmount().multiply(rate);
    }

    @Override
    public String getRuleName() {
        return "代理人等级佣金";
    }
}
