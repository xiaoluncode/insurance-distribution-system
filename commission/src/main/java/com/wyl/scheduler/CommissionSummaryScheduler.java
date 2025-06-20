package com.wyl.scheduler;


import com.wyl.client.AgentClient;
import com.wyl.entity.CommissionSummaryDaily;
import com.wyl.entity.CommissionSummaryMonthly;
import com.wyl.entity.CommissionSummaryYearly;
import com.wyl.service.CommissionDailyService;
import com.wyl.service.CommissionMonthlyService;
import com.wyl.service.CommissionYearlyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommissionSummaryScheduler {
    @Autowired
    private CommissionDailyService dailyService;
    @Autowired
    private CommissionMonthlyService monthlyService;
    @Autowired
    private CommissionYearlyService yearlyService;
    @Autowired
    private AgentClient agentClient;
    // 每天凌晨1点执行
    @Scheduled(cron = "0 0 1 * * ?")
    public void dailySummary() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        // 查询所有代理人ID
        List<Long> agentIds = agentClient.getAllAgentIds();
        for (Long agentId : agentIds) {
            CommissionSummaryDaily summary = new CommissionSummaryDaily();
            summary.setAgentId(agentId);
            summary.setDate(yesterday);
            dailyService.save(summary);
        }
    }

    // 每月1号凌晨2点执行
    @Scheduled(cron = "0 0 2 1 * ?")
    public void monthlySummary() {
        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        List<Long> agentIds = agentClient.getAllAgentIds();
        for (Long agentId : agentIds) {
            CommissionSummaryMonthly summary = new CommissionSummaryMonthly();
            summary.setAgentId(agentId);
            summary.setYear(lastMonth.getYear());
            summary.setMonth(lastMonth.getMonthValue());
            monthlyService.save(summary);
        }
    }

    // 每年1月1号凌晨3点执行
    @Scheduled(cron = "0 0 3 1 1 ?")
    public void yearlySummary() {
        int lastYear = LocalDate.now().getYear() - 1;
        List<Long> agentIds = agentClient.getAllAgentIds();
        for (Long agentId : agentIds) {
            CommissionSummaryYearly summary = new CommissionSummaryYearly();
            summary.setAgentId(agentId);
            summary.setYear(lastYear);
            yearlyService.save(summary);
        }
    }
}
