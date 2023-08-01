package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.controller.ShoppingCartController;
import com.itheima.reggie.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Title:
 * @Author: xiajialin
 * @Date: 2023/7/31 23:01
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
