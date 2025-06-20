package com.wyl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyl.entity.CommissionSummaryMonthly;
import com.wyl.entity.CommissionSummaryYearly;
import com.wyl.mapper.CommissionSummaryMonthlyMapper;
import com.wyl.mapper.CommissionSummaryYearlyMapper;
import com.wyl.service.CommissionYearlyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CommissionYearlyServiceImpl implements CommissionYearlyService {

    private final CommissionSummaryYearlyMapper mapper;

    @Autowired
    private CommissionSummaryMonthlyMapper monthlyMapper;

    @Override
    public void save(CommissionSummaryYearly summary) {
        BigDecimal total = monthlyMapper.selectList(
                        new QueryWrapper<CommissionSummaryMonthly>()
                                .eq("agent_id", summary.getAgentId())
                                .eq("year", summary.getYear())
                ).stream()
                .map(CommissionSummaryMonthly::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        summary.setTotalAmount(total);

        mapper.delete(new QueryWrapper<CommissionSummaryYearly>()
                .eq("agent_id", summary.getAgentId())
                .eq("year", summary.getYear()));
        mapper.insert(summary);
    }


    @Override
    public List<CommissionSummaryYearly> listByAgentAndRange(Long agentId, int startYear, int endYear) {
        List<CommissionSummaryYearly> raw = mapper.selectList(
                new QueryWrapper<CommissionSummaryYearly>()
                        .eq("agent_id", agentId)
                        .between("year", startYear, endYear)
                        .orderByAsc("year")
        );
        Map<Integer, CommissionSummaryYearly> map = new HashMap<>();
        for (var y : raw) map.put(y.getYear(), y);
        List<CommissionSummaryYearly> full = new ArrayList<>();
        for (int y = startYear; y <= endYear; y++) {
            CommissionSummaryYearly s = map.getOrDefault(y, null);
            if (s == null) {
                s = new CommissionSummaryYearly();
                s.setAgentId(agentId);
                s.setYear(y);
                s.setTotalAmount(BigDecimal.ZERO);
            }
            full.add(s);
        }
        return full;
    }
}
