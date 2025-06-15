package com.wyl.service;

import com.wyl.entity.CommissionSummaryDaily;

import java.time.YearMonth;
import java.util.List;

public interface CommissionDailyService {
    void save(CommissionSummaryDaily summary);
    /**
     * 获取某年某月每天的汇总，若无数据自动补 0
     */
    List<CommissionSummaryDaily> listByAgentAndMonth(Long agentId, int year, int month);
}
