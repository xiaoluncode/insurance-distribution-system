package com.wyl.mapper;


/*
@作者：wyl  
*/
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyl.entity.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {

}

