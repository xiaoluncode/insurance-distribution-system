package com.wyl.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyl.entity.Agent;
import com.wyl.vo.ResultVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AgentMapper extends BaseMapper<Agent> {
    // 根据 userId 查询
    @Select("SELECT * FROM agent WHERE user_id = #{userId}")
    Agent selectByUserId(Long userId);

    // 查询下级代理列表
    @Select("SELECT * FROM agent WHERE parent_id = #{parentId}")
    List<Agent> selectByParentId(Long parentId);

    Agent getAgentCaptainByAgentId(Long agentId);

    List<Long> findAllMembers(Long captainId);

    BigDecimal getMonthlyPerformance(@Param("allMembersIds") List<Long> allMembersIds,
    @Param("year") int year,
    @Param("month") int month);

    //获取所有代理人id，要求去重
    @Select("SELECT DISTINCT agent_id FROM agent")
    List<Long> getAllAgentIds();
}
