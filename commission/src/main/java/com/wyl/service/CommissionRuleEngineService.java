package com.wyl.service;


import com.wyl.entity.Commission;
import com.wyl.entity.Order;
import com.wyl.rule.CommissionRuleChain;
import org.springframework.stereotype.Service;

/*
@作者：wyl  
*/
@Service
public class CommissionRuleEngineService {
    private final CommissionRuleChain ruleChain;

    public CommissionRuleEngineService(CommissionRuleChain ruleChain) {
        this.ruleChain = ruleChain;
    }

    public Commission calculateCommission(Order order) {
        return ruleChain.calculateCommission(order);
    }
}

