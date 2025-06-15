package com.wyl.mapper;


/*
@作者：wyl  
*/
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyl.entity.Commission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommissionMapper extends BaseMapper<Commission> {
}