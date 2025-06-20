package com.wyl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyl.entity.Commission;
import com.wyl.entity.CommissionSummaryDaily;
import com.wyl.mapper.CommissionMapper;
import com.wyl.mapper.CommissionSummaryDailyMapper;
import com.wyl.service.CommissionDailyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CommissionDailyServiceImpl implements CommissionDailyService {

    private final CommissionSummaryDailyMapper mapper;
    @Autowired
    private CommissionMapper commissionMapper;

    @Override
    public void save(CommissionSummaryDaily summary) {
        //
        BigDecimal total = commissionMapper.selectList(
                        new QueryWrapper<Commission>()
                                .eq("agent_id", summary.getAgentId())
                                .eq("DATE(created_at)", summary.getDate())
                ).stream()
                .map(Commission::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        summary.setTotalAmount(total);

        // 先删后插，避免重复
        mapper.delete(new QueryWrapper<CommissionSummaryDaily>()
                .eq("agent_id", summary.getAgentId())
                .eq("date", summary.getDate()));
        mapper.insert(summary);
    }

    @Override
    public List<CommissionSummaryDaily> listByAgentAndMonth(Long agentId, int year, int month) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1), end = ym.atEndOfMonth();
        List<CommissionSummaryDaily> raw = mapper.selectList(
                new QueryWrapper<CommissionSummaryDaily>()
                        .eq("agent_id", agentId)
                        .between("date", start, end)
                        .orderByAsc("date")
        );
        // 构建 map 便于快速查找
        Map<LocalDate, CommissionSummaryDaily> map = new HashMap<>();
        for (CommissionSummaryDaily d : raw) map.put(d.getDate(), d);
        // 补全
        List<CommissionSummaryDaily> full = new ArrayList<>();
        for (LocalDate dt = start; !dt.isAfter(end); dt = dt.plusDays(1)) {
            CommissionSummaryDaily d = map.getOrDefault(dt, null);
            if (d == null) {
                d = new CommissionSummaryDaily();
                d.setAgentId(agentId);
                d.setDate(dt);
                d.setTotalAmount(BigDecimal.ZERO);
            }
            full.add(d);
        }
        return full;
    }
}
