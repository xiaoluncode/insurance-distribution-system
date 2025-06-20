package com.wyl.service;


import com.wyl.dto.AgentRegisterDTO;
import com.wyl.entity.Agent;
import com.wyl.vo.ResultVo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public interface AgentService {
    Agent registerAgent(AgentRegisterDTO dto);
    Agent findByUserId(Long userId);
    List<Agent> findTeam(Long userId);
    Agent upgradeLevel(Long userId, String newLevel);

    String findLevelByAgentId(Long agentId);

    Agent getAgentById(Long agentId);

    Agent getAgentCaptain(Long agentId);

    List<Long> findAllMembers(Long captainId);

    BigDecimal getMonthlyPerformance(List<Long> allMembersIds, int year, int month);

    Agent getAgentByUserId(Long userId);

    List<Long> getAllAgentIds();
}