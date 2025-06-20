package com.wyl.service;


import com.wyl.entity.Order;
import com.wyl.entity.OrderStatus;

/*
@作者：wyl
*/
public interface OrderStateHandler {
    OrderStatus getState();               // 处理器对应状态
    void handle(Order order);             // 该状态下的业务逻辑
    OrderStatus next(Order order);        // 返回下一个状态
}
