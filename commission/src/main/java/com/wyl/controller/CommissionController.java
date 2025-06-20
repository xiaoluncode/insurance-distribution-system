package com.wyl.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyl.entity.*;
import com.wyl.mapper.CommissionSummaryDailyMapper;
import com.wyl.service.CommissionRuleEngineService;
import com.wyl.service.OrderService;
import com.wyl.mapper.CommissionMapper;
import com.wyl.mapper.CommissionSummaryMonthlyMapper;
import com.wyl.mapper.CommissionSummaryYearlyMapper;
import com.wyl.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commission")
@RequiredArgsConstructor
public class CommissionController {

    private final CommissionMapper commissionMapper;
    private final CommissionSummaryDailyMapper dailyMapper;
    private final CommissionSummaryMonthlyMapper monthlyMapper;
    private final CommissionSummaryYearlyMapper yearlyMapper;
    private final OrderService orderService;
    private final CommissionRuleEngineService commissionRuleEngineService;

    /**
     * 计算并插入佣金明细（通过订单）
     */
    @PostMapping("/calculate")
    public ResultVo<Commission> calculate(@RequestBody Order order) {
        Commission result = commissionRuleEngineService.calculateCommission(order);
        commissionMapper.insert(result);
        return ResultVo.success(result);
    }

    /**
     * 查询某代理人所有佣金明细
     */
    @GetMapping("/byAgent/{agentId}")
    public ResultVo<List<Commission>> getByAgent(@PathVariable Long agentId) {
        List<Commission> list = commissionMapper.selectList(
                new QueryWrapper<Commission>().eq("agent_id", agentId)
        );
        return ResultVo.success(list);
    }
}
