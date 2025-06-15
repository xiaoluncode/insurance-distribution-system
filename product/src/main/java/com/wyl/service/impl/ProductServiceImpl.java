package com.wyl.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyl.dto.ProductCreateDTO;
import com.wyl.dto.ProductQueryDTO;
import com.wyl.dto.ProductUpdateDTO;
import com.wyl.entity.Product;
import com.wyl.exception.BusinessException;
import com.wyl.mapper.ProductMapper;
import com.wyl.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/*
@作者：wyl  
*/
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;

    @Override
    public Product create(ProductCreateDTO dto) {
        // 唯一校验
        if (mapper.selectCount(new QueryWrapper<Product>().eq("name", dto.getName())) > 0) {
            throw new BusinessException(400, "产品名称已存在");
        }
        Product p = new Product();
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());
        p.setStatus("1"); // 默认上架
        p.setProductType(dto.getProductType());
        p.setChannel(dto.getChannel());
        p.setCreatedAt(LocalDateTime.now());
        mapper.insert(p);
        return p;
    }

    @Override
    public Product update(ProductUpdateDTO dto) {
        Product p = mapper.selectById(dto.getId());
        if (p == null) throw new BusinessException(404, "产品不存在");
        // 更新字段
        if (dto.getName() != null) p.setName(dto.getName());
        if (dto.getDescription() != null) p.setDescription(dto.getDescription());
        if (dto.getPrice() != null) p.setPrice(dto.getPrice());
        if (dto.getStatus() != null) p.setStatus(dto.getStatus());
        if (dto.getProductType() != null) p.setProductType(dto.getProductType());
        if (dto.getChannel() != null) p.setChannel(dto.getChannel());
        mapper.updateById(p);
        return p;
    }

    @Override
    public void remove(Long id) {
        if (mapper.selectById(id) == null) {
            throw new BusinessException(404, "产品不存在");
        }
        mapper.deleteById(id);
    }

    @Override
    public IPage<Product> page(ProductQueryDTO dto) {
        Page<Product> page = new Page<>(dto.getPage(), dto.getSize());
        QueryWrapper<Product> qw = new QueryWrapper<>();
        if (dto.getName() != null) qw.like("name", dto.getName());
        if (dto.getStatus() != null) qw.eq("status", dto.getStatus());
        if (dto.getProductType() != null) qw.eq("product_type", dto.getProductType());
        if (dto.getChannel() != null) qw.eq("channel", dto.getChannel());
        return mapper.selectPage(page, qw);
    }

    @Override
    public Product getById(Long id) {
        Product p = mapper.selectById(id);
        if (p == null) throw new BusinessException(404, "产品不存在");
        return p;
    }
}