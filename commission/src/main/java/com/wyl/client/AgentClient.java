package com.wyl.client;

import com.wyl.entity.Agent;
import com.wyl.vo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Component
@FeignClient(name = "agent-service", path = "/agent")
public interface AgentClient {

    @GetMapping("/getLevelById/{agentId}")
    public String getLevelById(@PathVariable Long agentId);

    @GetMapping("/getAgentById/{userId}")
    public Agent getAgentById(@PathVariable Long userId);

    @GetMapping("/getAgentCaptain/{agentId}")
    public Agent getAgentCaptain(@PathVariable Long agentId);

    @GetMapping("/findAllMembers/{captainId}")
    public List<Long> findAllMembers(@PathVariable Long captainId);

    @GetMapping("/getMonthlyPerformance")
    public ResultVo<BigDecimal> getMonthlyPerformance(@RequestParam List<Long> allMembersIds,
                                            @RequestParam int year,
                                            @RequestParam int month);
    @GetMapping("/getAllAgentIds")
    public List<Long> getAllAgentIds();
}

