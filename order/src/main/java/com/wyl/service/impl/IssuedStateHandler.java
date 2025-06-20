package com.wyl.service.impl;


import com.wyl.entity.Order;
import com.wyl.entity.OrderStatus;
import com.wyl.service.OrderStateHandler;
import org.springframework.stereotype.Component;

/*
@作者：wyl
*/
@Component
public class IssuedStateHandler implements OrderStateHandler {
    @Override public OrderStatus getState() { return OrderStatus.ISSUED; }
    @Override public void handle(Order order) {
        System.out.println("订单已出单，结束流程。订单ID：" + order.getId());
        // 清理资源或后续通知
    }
    @Override public OrderStatus next(Order order) {
        return OrderStatus.ISSUED; // 终态保持不变
    }
}
