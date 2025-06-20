package com.wyl.controller;

import com.wyl.entity.CommissionSummaryMonthly;
import com.wyl.service.CommissionMonthlyService;
import com.wyl.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/summary/monthly")
@RequiredArgsConstructor
public class CommissionMonthlyController {

    private final CommissionMonthlyService service;

    /** 写入或更新月度汇总 */
    @PostMapping("/add")
    public ResultVo<Void> save(@RequestBody CommissionSummaryMonthly summary) {
        service.save(summary);
        return ResultVo.success();
    }

    /** 查询某年所有月度汇总，缺失补零 */
    @GetMapping("/selectByYear")
    public ResultVo<List<CommissionSummaryMonthly>> list(
            @RequestParam Long agentId,
            @RequestParam int year
    ) {
        return ResultVo.success(service.listByAgentAndYear(agentId, year));
    }
}
