package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.mapper.OrderMapper;

/**
 * @Title:
 * @Author: xiajialin
 * @Date: 2023/8/1 0:10
 */
public interface OrderService extends IService<Orders> {

    public void submit(Orders orders);
}
