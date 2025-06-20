package com.wyl.mapper;

/*
@作者：wyl  
*/
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyl.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    Order selectByOrderNo(String orderNo);
}

