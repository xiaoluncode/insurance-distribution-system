package com.wyl.service;

import com.wyl.entity.CommissionSummaryYearly;

import java.util.List;

public interface CommissionYearlyService {
    void save(CommissionSummaryYearly summary);
    /**
     * 获取 agent 从 startYear 到 endYear 每年汇总，缺失补 0
     */
    List<CommissionSummaryYearly> listByAgentAndRange(Long agentId, int startYear, int endYear);
}
