package com.wyl.service.impl;


import com.wyl.entity.Order;
import com.wyl.entity.OrderStatus;
import com.wyl.service.OrderStateHandler;
import org.springframework.stereotype.Component;

/*
@作者：wyl
*/
@Component
public class WaitUnderwriteStateHandler implements OrderStateHandler {
    @Override public OrderStatus getState() { return OrderStatus.WAIT_UW; }
    @Override public void handle(Order order) {
        System.out.println("执行核保逻辑... 订单ID：" + order.getId());
        // 调用核保接口或风控校验
    }
    @Override public OrderStatus next(Order order) {
        return OrderStatus.UW_SUCCESS;
    }
}
