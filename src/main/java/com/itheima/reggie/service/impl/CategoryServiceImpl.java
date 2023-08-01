package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title:
 * @Author: xiajialin
 * @Date: 2023/7/30 9:30
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除分类，删除前需要判断
     */
    @Override
    public void remove(Long id) {
        //查询当前分类是否关联了菜品，如果关联抛出异常
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(queryWrapper);
        if(count>0){
            //已经关联，抛出异常
            throw new CustomException("当前分类关联了菜品，不能删除");
        }
        //查询当前分类是否关联了套餐，如果关联抛出异常
        LambdaQueryWrapper<Setmeal> query = new LambdaQueryWrapper<>();
        query.eq(Setmeal::getCategoryId,id);
        int res = setmealService.count(query);
        if(res>0){
            //已经关联，抛出异常
            throw new CustomException("当前分类关联了菜品，不能删除");
        }

        //正常删除
        super.removeById(id);


    }







}
