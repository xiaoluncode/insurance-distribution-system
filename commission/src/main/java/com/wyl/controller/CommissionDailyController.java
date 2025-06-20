package com.wyl.controller;

import com.wyl.entity.CommissionSummaryDaily;
import com.wyl.service.CommissionDailyService;
import com.wyl.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/summary/daily")
@RequiredArgsConstructor
public class CommissionDailyController {

    private final CommissionDailyService service;

    /** 自动写入或更新某日汇总 */
    @PostMapping("/add")
    public ResultVo<Void> save(@RequestBody CommissionSummaryDaily summary) {
        service.save(summary);
        return ResultVo.success();
    }

    /** 查询某代理人某月所有日度汇总，缺失补零 */
    @GetMapping("/selectByMonth")
    public ResultVo<List<CommissionSummaryDaily>> list(
            @RequestParam Long agentId,
            @RequestParam int year,
            @RequestParam int month
    ) {
        return ResultVo.success(service.listByAgentAndMonth(agentId, year, month));
    }
}
