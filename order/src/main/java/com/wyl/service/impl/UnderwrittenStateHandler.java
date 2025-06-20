package com.wyl.service.impl;


import com.wyl.entity.Order;
import com.wyl.entity.OrderStatus;
import com.wyl.service.OrderStateHandler;
import org.springframework.stereotype.Component;

/*
@作者：wyl
核保成功状态处理器
*/
@Component
public class UnderwrittenStateHandler implements OrderStateHandler {
    @Override public OrderStatus getState() { return OrderStatus.UW_SUCCESS; }
    @Override public void handle(Order order) {
        System.out.println("核保成功，准备支付... 订单ID：" + order.getId());
        // 更新核保结果
    }
    @Override public OrderStatus next(Order order) {
        return OrderStatus.WAIT_PAY;
    }
}
