package com.wyl.service.impl;
/*
@作者：wyl
*/
import com.wyl.dto.AgentRegisterDTO;
import com.wyl.entity.Agent;
import com.wyl.entity.AgentLevel;
import com.wyl.exception.BusinessException;
import com.wyl.mapper.AgentMapper;
import com.wyl.service.AgentService;
import com.wyl.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgentMapper agentMapper;

    @Override
    public Agent registerAgent(AgentRegisterDTO dto) {
        // 防止重复注册
        if (agentMapper.selectByUserId(dto.getUserId()) != null) {
            throw new BusinessException(400, "用户已是代理");
        }
        Agent agent = new Agent();
        agent.setUserId(dto.getUserId());
        agent.setName(dto.getName());
        agent.setParentId(dto.getParentId());
        agent.setLevel(AgentLevel.NORMAL.name());
        agent.setStatus("1");
        agent.setCreatedAt(LocalDateTime.now());
        agentMapper.insert(agent);
        return agent;
    }

    @Override
    public Agent findByUserId(Long userId) {
        return agentMapper.selectByUserId(userId);
    }

    @Override
    public List<Agent> findTeam(Long userId) {
        Agent root = findByUserId(userId);
        if (root == null) throw new BusinessException(404, "代理不存在");
        List<Agent> team = new ArrayList<>();
        collectChildren(root.getId(), team);
        return team;
    }

    private void collectChildren(Long parentId, List<Agent> team) {
        List<Agent> children = agentMapper.selectByParentId(parentId);
        for (Agent child : children) {
            team.add(child);
            collectChildren(child.getId(), team);
        }
    }

    @Override
    public Agent upgradeLevel(Long userId, String newLevel) {
        Agent agent = findByUserId(userId);
        if (agent == null) throw new BusinessException(404, "代理不存在");
        agent.setLevel(newLevel);
        agentMapper.updateById(agent);
        return agent;
    }

    @Override
    public String findLevelByAgentId(Long agentId) {
        return agentMapper.selectById(agentId).getLevel();
    }

    @Override
    public Agent getAgentById(Long agentId) {
        return agentMapper.selectById(agentId);
    }

    @Override
    public Agent getAgentCaptain(Long agentId) {
        return agentMapper.getAgentCaptainByAgentId(agentId);
    }

    @Override
    public List<Long> findAllMembers(Long captainId) {
        return agentMapper.findAllMembers(captainId);
    }

    @Override
    public BigDecimal getMonthlyPerformance(List<Long> allMembersIds, int year, int month) {
        return agentMapper.getMonthlyPerformance(allMembersIds, year, month);
    }

    @Override
    public Agent getAgentByUserId(Long userId) {
        return agentMapper.selectByUserId(userId);
    }

    @Override
    public List<Long> getAllAgentIds() {
        return agentMapper.getAllAgentIds();
    }


}

