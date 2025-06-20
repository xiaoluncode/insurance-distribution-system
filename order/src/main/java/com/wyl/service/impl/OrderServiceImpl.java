package com.wyl.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyl.dto.OrderCreateDTO;
import com.wyl.dto.OrderQueryDTO;
import com.wyl.entity.Order;
import com.wyl.exception.BusinessException;
import com.wyl.mapper.OrderMapper;
import com.wyl.service.OrderService;
import com.wyl.status.OrderStateMachine;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/*
@作者：wyl  
*/
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper mapper;

    @Autowired
    private OrderStateMachine stateMachine;

    @Override
    @GlobalTransactional // 如果涉及分布式事务
    public Order create(OrderCreateDTO dto) {
        // 生成唯一订单号（固定字符串“order”拼接uuid拼接时间戳）
        String orderNo = "order-" + UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
        Order o = new Order();
        o.setOrderNo(orderNo);
        o.setAgentId(dto.getAgentId());
        o.setCustomerId(dto.getCustomerId());
        o.setProductId(dto.getProductId());
        o.setAmount(dto.getAmount());
        o.setStatus("CREATED");
        o.setProductType(dto.getProductType());
        o.setChannel(dto.getChannel());
        o.setCreatedAt(LocalDateTime.now());
        o.setUpdatedAt(LocalDateTime.now());
        mapper.insert(o);
        // TODO: 如果需要，触发佣金异步计算
        return o;
    }

    @Override
    public IPage<Order> page(OrderQueryDTO dto) {
        // 构建分页对象
        Page<Order> page = new Page<>(dto.getPage(), dto.getSize());
        // 使用 lambda 表达式构建类型安全的查询条件
        QueryWrapper<Order> qw = new QueryWrapper<>();
        // 根据传入参数动态添加查询条件：
        // - 如果 agentId 不为 null，则按代理商 ID 查询
        // - 如果 customerId 不为 null，则按客户 ID 查询
        // - 如果 status 不为 null，则按订单状态查询
        if (dto.getAgentId() != null) {
            qw.eq("agent_id", dto.getAgentId());
        }
        if (dto.getCustomerId() != null) {
            qw.eq("customer_id", dto.getCustomerId()); // 修复字段名 user_id -> customer_id
        }
        if (dto.getStatus() != null) {
            qw.eq("status", dto.getStatus());
        }
        // 执行分页查询并返回结果
        // 此方法根据给定的查询条件和分页信息从数据库中检索订单数据
        return mapper.selectPage(page, qw);
    }

    @Override
    public Order getById(Long id) {
        Order o = mapper.selectById(id);
        if (o == null) throw new BusinessException(404, "订单不存在");
        return o;
    }

    @Override
    public Order updateStatus(String orderNo) {
        Order o = mapper.selectByOrderNo(orderNo);
        if (o == null) {
            throw new BusinessException(404, "订单不存在");
        }
        // 处理订单状态
        Order order = stateMachine.process(o);
        order.setUpdatedAt(LocalDateTime.now());
        mapper.updateById(order);
        return order;
    }
}
