package com.wyl.service;


/*
@作者：wyl  
*/

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wyl.dto.OrderCreateDTO;
import com.wyl.dto.OrderQueryDTO;
import com.wyl.entity.Order;

public interface OrderService {
    Order create(OrderCreateDTO dto);
    IPage<Order> page(OrderQueryDTO dto);
    Order getById(Long id);
    Order updateStatus(String orderNo);
}
