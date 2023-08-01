package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Title:
 * @Author: xiajialin
 * @Date: 2023/8/1 0:08
 */
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
