package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.entity.SetmealDish;
import com.itheima.reggie.mapper.SetmealMapper;
import com.itheima.reggie.service.SetmealDishService;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title:
 * @Author: xiajialin
 * @Date: 2023/7/30 13:50
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐基本信息，操作setmeal，执行insert
        this.save(setmealDto);

        List<SetmealDish> setMealDishes = setmealDto.getSetmealDishes();
        setMealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        //保存套餐和菜品关联,操作setmeal_dish
        setmealDishService.saveBatch(setMealDishes);
    }

    @Override
    @Transactional
    public void deleteWithDish_single(long id) {
        //删除套餐
        this.removeById(id);
        //删除套餐中的菜品dish
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        setmealDishService.remove(queryWrapper);
    }

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        //查询可用状态，确定是否可用
        LambdaQueryWrapper<Setmeal> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.in(Setmeal::getId,ids);
        queryWrapper1.eq(Setmeal::getStatus,1);

        int count = this.count(queryWrapper1);
        //不能删除 抛出异常
        if(count>0){
            throw new CustomException("套餐正在售卖中，不能删除");
        }
        //可以删除，先删除套餐表中的数据
        //批量删除
        this.removeByIds(ids);

        //删除关系表setmealdish中的数据
        //delete from setmeal_dish where setmeal_id in (1,2,3)
        LambdaQueryWrapper<SetmealDish> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(queryWrapper2);
    }
}
