package com.wyl.controller;


import com.wyl.dto.ProductCreateDTO;
import com.wyl.dto.ProductQueryDTO;
import com.wyl.dto.ProductUpdateDTO;
import com.wyl.entity.Product;
import com.wyl.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wyl.vo.ResultVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*
@作者：wyl
*/

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    // 新增产品
    @PostMapping("/add")
    public ResultVo<Product> create(@Valid @RequestBody ProductCreateDTO dto) {
        return ResultVo.success(service.create(dto));
    }

    // 修改产品
    @PutMapping("/update")
    public ResultVo<Product> update(@Valid @RequestBody ProductUpdateDTO dto) {
        return ResultVo.success(service.update(dto));
    }

    // 删除
    @DeleteMapping("/{id}")
    public ResultVo<Void> delete(@PathVariable Long id) {
        service.remove(id);
        return ResultVo.success();
    }

    // 分页查询
    @GetMapping
    public ResultVo<IPage<Product>> page(@Valid ProductQueryDTO dto) {
        return ResultVo.success(service.page(dto));
    }

    // 查询详情
    @GetMapping("/{id}")
    public ResultVo<Product> get(@PathVariable Long id) {
        return ResultVo.success(service.getById(id));
    }
}
