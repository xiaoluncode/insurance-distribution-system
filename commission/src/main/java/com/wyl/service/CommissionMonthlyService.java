package com.wyl.service;

import com.wyl.entity.CommissionSummaryMonthly;

import java.util.List;

public interface CommissionMonthlyService {
    void save(CommissionSummaryMonthly summary);
    /**
     * 获取某年每月汇总（1~12），缺失补 0
     */
    List<CommissionSummaryMonthly> listByAgentAndYear(Long agentId, int year);
}
