package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;

/**
 * @Title:
 * @Author: xiajialin
 * @Date: 2023/7/30 13:47
 */
public interface DishService extends IService<Dish> {

    /**
     * 新增菜品，同时加入菜品对应的口味数据，需要操作dish dish_flavor两张表
     */
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(long id);

    public void updateWithFlavor(DishDto dishDto);
}
