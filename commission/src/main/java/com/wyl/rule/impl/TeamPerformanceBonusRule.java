package com.wyl.rule.impl;

import com.wyl.client.AgentClient;
import com.wyl.entity.Agent;
import com.wyl.entity.Order;
import com.wyl.rule.CommissionRule;
import com.wyl.service.AgentService;
import com.wyl.vo.ResultVo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/*
@作者：wyl  
*/
@Component
public class TeamPerformanceBonusRule implements CommissionRule {

    @Autowired
    private AgentClient agentClient;

    @Override
    public BigDecimal calculate(Order order) {
        BigDecimal bonusRate = BigDecimal.ZERO;
        LocalDateTime createdAt = order.getCreatedAt();
        int year = createdAt.getYear();
        int month = createdAt.getMonthValue();
        // 获取团队队长
        Agent agentCaptain = agentClient.getAgentCaptain(order.getAgentId());
        // 获取团队所有成员id
        List<Long> allMemberIds = agentClient.findAllMembers(agentCaptain.getId());
        // 获取团队业绩
        ResultVo<BigDecimal> resultVo = agentClient.getMonthlyPerformance(allMemberIds, year, month);
        BigDecimal performance = resultVo != null ? resultVo.getData() : null;
        if (performance == null) {
            performance = BigDecimal.ZERO;
        }

        if (performance.compareTo(new BigDecimal("1000000")) >= 0) {
            bonusRate = new BigDecimal("0.02");
        } else if (performance.compareTo(new BigDecimal("500000")) >= 0) {
            bonusRate = new BigDecimal("0.01");
        }

        return order.getAmount().multiply(bonusRate);
    }

    @Override
    public String getRuleName() {
        return "团队业绩奖励规则";
    }
}
