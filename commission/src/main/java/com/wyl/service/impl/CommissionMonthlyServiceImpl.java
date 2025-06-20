package com.wyl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyl.entity.CommissionSummaryDaily;
import com.wyl.entity.CommissionSummaryMonthly;
import com.wyl.mapper.CommissionSummaryDailyMapper;
import com.wyl.mapper.CommissionSummaryMonthlyMapper;
import com.wyl.service.CommissionMonthlyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CommissionMonthlyServiceImpl implements CommissionMonthlyService {

    private final CommissionSummaryMonthlyMapper mapper;

    @Autowired
    private CommissionSummaryDailyMapper dailyMapper;

    @Override
    public void save(CommissionSummaryMonthly summary) {
        // 聚合该月所有 daily
        YearMonth ym = YearMonth.of(summary.getYear(), summary.getMonth());
        LocalDate start = ym.atDay(1), end = ym.atEndOfMonth();
        BigDecimal total = dailyMapper.selectList(
                        new QueryWrapper<CommissionSummaryDaily>()
                                .eq("agent_id", summary.getAgentId())
                                .between("date", start, end)
                ).stream()
                .map(CommissionSummaryDaily::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        summary.setTotalAmount(total);

        mapper.delete(new QueryWrapper<CommissionSummaryMonthly>()
                .eq("agent_id", summary.getAgentId())
                .eq("year", summary.getYear())
                .eq("month", summary.getMonth()));
        mapper.insert(summary);
    }

    @Override
    public List<CommissionSummaryMonthly> listByAgentAndYear(Long agentId, int year) {
        List<CommissionSummaryMonthly> raw = mapper.selectList(
                new QueryWrapper<CommissionSummaryMonthly>()
                        .eq("agent_id", agentId)
                        .eq("year", year)
                        .orderByAsc("month")
        );
        Map<Integer, CommissionSummaryMonthly> map = new HashMap<>();
        for (var m : raw) map.put(m.getMonth(), m);
        List<CommissionSummaryMonthly> full = new ArrayList<>();
        for (int m = 1; m <= 12; m++) {
            CommissionSummaryMonthly s = map.getOrDefault(m, null);
            if (s == null) {
                s = new CommissionSummaryMonthly();
                s.setAgentId(agentId);
                s.setYear(year);
                s.setMonth(m);
                s.setTotalAmount(BigDecimal.ZERO);
            }
            full.add(s);
        }
        return full;
    }
}
