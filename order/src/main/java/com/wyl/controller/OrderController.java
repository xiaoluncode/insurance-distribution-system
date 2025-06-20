package com.wyl.controller;


/*
@作者：wyl  
*/

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wyl.dto.OrderCreateDTO;
import com.wyl.dto.OrderQueryDTO;
import com.wyl.entity.Order;
import com.wyl.service.OrderService;
import com.wyl.vo.ResultVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping("/add")
    public ResultVo<Order> create(@Valid @RequestBody OrderCreateDTO dto) {
        return ResultVo.success(service.create(dto));
    }

    @GetMapping
    public ResultVo<IPage<Order>> page(@Valid OrderQueryDTO dto) {
        return ResultVo.success(service.page(dto));
    }

    @GetMapping("/{id}")
    public ResultVo<Order> get(@PathVariable Long id) {
        return ResultVo.success(service.getById(id));
    }

    @PutMapping("/updatestatus/{orderNo}")
    public ResultVo<Order> updateStatus(@PathVariable String orderNo ) {
        return ResultVo.success(service.updateStatus(orderNo));
    }
}
