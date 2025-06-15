package com.wyl.service;


/*
@作者：wyl  
*/
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wyl.dto.ProductCreateDTO;
import com.wyl.dto.ProductQueryDTO;
import com.wyl.dto.ProductUpdateDTO;
import com.wyl.entity.Product;

public interface ProductService {
    Product create(ProductCreateDTO dto);
    Product update(ProductUpdateDTO dto);
    void remove(Long id);
    IPage<Product> page(ProductQueryDTO dto);
    Product getById(Long id);
}