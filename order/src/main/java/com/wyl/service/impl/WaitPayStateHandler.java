package com.wyl.service.impl;


import com.wyl.entity.Order;
import com.wyl.entity.OrderStatus;
import com.wyl.service.OrderStateHandler;
import org.springframework.stereotype.Component;

/*
@作者：wyl
*/
@Component
public class WaitPayStateHandler implements OrderStateHandler {
    @Override public OrderStatus getState() { return OrderStatus.WAIT_PAY; }
    @Override public void handle(Order order) {
        System.out.println("执行支付逻辑... 订单ID：" + order.getId());
        // 模拟或调用支付服务
    }
    @Override public OrderStatus next(Order order) {
        return OrderStatus.PAID;
    }
}
