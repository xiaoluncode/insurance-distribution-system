package com.wyl.service.impl;


import com.wyl.entity.Order;
import com.wyl.entity.OrderStatus;
import com.wyl.service.OrderStateHandler;
import org.springframework.stereotype.Component;

/*
@作者：wyl
支付成功状态处理器
*/
@Component
public class PaidStateHandler implements OrderStateHandler {
    @Override public OrderStatus getState() { return OrderStatus.PAID; }
    @Override public void handle(Order order) {
        System.out.println("支付成功，准备出单... 订单ID：" + order.getId());
        // 支付回调处理
    }
    @Override public OrderStatus next(Order order) {
        return OrderStatus.WAIT_ISSUE;
    }
}
