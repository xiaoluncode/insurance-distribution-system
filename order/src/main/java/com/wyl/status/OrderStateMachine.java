package com.wyl.status;


import com.wyl.entity.Order;
import com.wyl.entity.OrderStatus;
import com.wyl.service.OrderStateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
@作者：wyl
状态机调度器
*/
@Component
public class OrderStateMachine {
    private final Map<OrderStatus, OrderStateHandler> handlerMap = new HashMap<>();

    public OrderStateMachine(List<OrderStateHandler> handlers) {
        for (OrderStateHandler handler : handlers) {
            handlerMap.put(handler.getState(), handler);
        }
    }

    public Order process(Order order) {
        OrderStatus statusEnum;
        try {
            statusEnum = OrderStatus.valueOf(order.getStatus());
        } catch (Exception e) {
            throw new IllegalStateException("无效的订单状态: " + order.getStatus());
        }
        OrderStateHandler handler = handlerMap.get(statusEnum);
        if (handler == null) {
            throw new IllegalStateException("未实现状态: " + order.getStatus());
        }
        handler.handle(order);
        OrderStatus next = handler.next(order);
        order.setStatus(next.name());
        System.out.println("订单新状态：" + order.getStatus());
        return order;
    }
}

