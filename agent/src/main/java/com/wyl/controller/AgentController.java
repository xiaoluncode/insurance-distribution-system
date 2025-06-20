package com.wyl.controller;


/*
@作者：wyl
*/
import com.wyl.dto.AgentRegisterDTO;
import com.wyl.entity.Agent;
import com.wyl.service.AgentService;
import com.wyl.vo.ResultVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/agent")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;

    @PostMapping("/register")
    public ResultVo<Agent> register(@Valid @RequestBody AgentRegisterDTO dto) {
        Agent agent = agentService.registerAgent(dto);
        return ResultVo.success(agent);
    }

    @GetMapping("/me")
    public ResultVo<Agent> me(@RequestParam Long userId) {
        Agent agent = agentService.findByUserId(userId);
        return ResultVo.success(agent);
    }

    @GetMapping("/team")
    public ResultVo<List<Agent>> team(@RequestParam Long userId) {
        List<Agent> team = agentService.findTeam(userId);
        return ResultVo.success(team);
    }

    @PutMapping("/upgrade")
    public ResultVo<Agent> upgrade(@RequestParam Long userId,
                                   @RequestParam String level) {
        Agent agent = agentService.upgradeLevel(userId, level);
        return ResultVo.success(agent);
    }

    @GetMapping("/getLevelById/{agentId}")
    public String getLevelById(@PathVariable Long agentId) {
        String level = agentService.findLevelByAgentId(agentId);
        return level;
    }

    @GetMapping("/getAgentById/{userId}")
    public Agent getAgentById(@PathVariable Long userId) {
        Agent agent = agentService.getAgentByUserId(userId);
        return agent;
    }

    @GetMapping("/getAgentCaptain/{agentId}")
    public Agent getAgentCaptain(@PathVariable Long agentId) {
        Agent agent = agentService.getAgentCaptain(agentId);
        return agent;
    }

    @GetMapping("/findAllMembers/{captainId}")
    public List<Long> findAllMembers(@PathVariable Long captainId) {
        List<Long> allMembers = agentService.findAllMembers(captainId);
        return allMembers;
    }

    @GetMapping("/getMonthlyPerformance")
    public ResultVo<BigDecimal> getMonthlyPerformance(@RequestParam List<Long> allMembersIds,
                                            @RequestParam int year,
                                            @RequestParam int month) {
        BigDecimal monthlyPerformance = agentService.getMonthlyPerformance(allMembersIds, year, month);
        return ResultVo.success(monthlyPerformance);
    }

    @GetMapping("/getAllAgentIds")
    public List<Long> getAllAgentIds() {
        List<Long> allAgentIds = agentService.getAllAgentIds();
        return allAgentIds;
    }


}

