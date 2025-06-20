package com.wyl.service.impl;


import com.wyl.entity.Order;
import com.wyl.entity.OrderStatus;
import com.wyl.service.OrderStateHandler;
import org.springframework.stereotype.Component;

/*
@作者：wyl
待出单状态处理器
*/
@Component
public class WaitIssueStateHandler implements OrderStateHandler {
    @Override public OrderStatus getState() { return OrderStatus.WAIT_ISSUE; }
    @Override public void handle(Order order) {
        System.out.println("执行出单逻辑... 订单ID：" + order.getId());
        // 调用保险公司出单接口
    }
    @Override public OrderStatus next(Order order) {
        return OrderStatus.ISSUED;
    }
}
