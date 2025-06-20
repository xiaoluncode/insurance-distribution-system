package com.wyl.controller;

import com.wyl.entity.CommissionSummaryYearly;
import com.wyl.service.CommissionYearlyService;
import com.wyl.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/summary/yearly")
@RequiredArgsConstructor
public class CommissionYearlyController {

    private final CommissionYearlyService service;

    /** 写入或更新年度汇总 */
    @PostMapping("/add")
    public ResultVo<Void> save(@RequestBody CommissionSummaryYearly summary) {
        service.save(summary);
        return ResultVo.success();
    }

    /** 查询某范围内每年汇总，缺失补零 */
    @GetMapping("/select")
    public ResultVo<List<CommissionSummaryYearly>> list(
            @RequestParam Long agentId,
            @RequestParam int startYear,
            @RequestParam int endYear
    ) {
        return ResultVo.success(service.listByAgentAndRange(agentId, startYear, endYear));
    }
}
